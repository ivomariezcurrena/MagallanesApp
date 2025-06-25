import { CommonModule, Location } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { NgbCalendar, NgbDatepickerModule, NgbDateStruct, NgbModule, NgbTypeaheadModule } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { catchError, debounceTime, distinctUntilChanged, map, switchMap, tap } from 'rxjs/operators';
import { Persona } from '../personas/persona';
import { Designacion } from './designacion';
import { DesignacionService } from './designacion.service';
import { Cargo } from '../cargo/cargo';
import { CargoService } from '../cargo/cargo.service';
import { PersonaService } from '../personas/persona.service';
import { ModalService } from '../modal/modal.service';

@Component({
  selector: 'app-designacion-detail',
  imports: [FormsModule, CommonModule, RouterModule, NgbTypeaheadModule, NgbModule, NgbDatepickerModule],
  templateUrl: `designacion-detail.component.html`,
  styleUrl: `designacion-detail.component.css`
})
export class DesignacionDetailComponent {
  personas: Persona[] = [];
  cargos: Cargo[] = [];
  designacion!: Designacion;
  searching: boolean = false;
  searchFailed: boolean = false;
  fechaInicioModel!: NgbDateStruct;
  fechaFinModel!: NgbDateStruct;
  errorMessage: string | null = null;
  loading = false;
  successMessage: string | null = null;
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private designacionService: DesignacionService,
    private cargoService: CargoService,
    private personaService: PersonaService,
    private location: Location,
    private calendar: NgbCalendar,
    private modalService: ModalService,
  ) { }

  ngOnInit() {
    this.get();
  }


  get() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id === 'new') {
      this.designacion = <Designacion><unknown>{
        cargo: <Cargo>{},
        persona: <Persona>{}
      }
      const hoy = this.calendar.getToday();
      this.fechaInicioModel = hoy;
    } else {
      this.designacionService.get(parseInt(id!)).subscribe((dataPackage) => {
        this.designacion = <Designacion>dataPackage.data;

        // Transformar strings a NgbDateStruct
        const dInicio = new Date(this.designacion.fechaInicio);
        this.fechaInicioModel = {
          year: dInicio.getFullYear(),
          month: dInicio.getMonth() + 1,
          day: dInicio.getDate()
        };

        if (this.designacion.fechaFin) {
          const dFin = new Date(this.designacion.fechaFin);
          this.fechaFinModel = {
            year: dFin.getFullYear(),
            month: dFin.getMonth() + 1,
            day: dFin.getDate()
          };
        }
      });
    }
    this.cargoService.all().subscribe((dataPackage) => {
      this.cargos = <Cargo[]>dataPackage.data;
    });
    this.personaService.all().subscribe((dataPackage) => {
      this.personas = <Persona[]>dataPackage.data;
    });
  }

  buscarCargos = (text$: Observable<string>): Observable<Cargo[]> =>
    text$.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      tap(() => (this.searching = true)),
      switchMap((term) =>
        this.cargoService.search(term).pipe(
          map((response) => <Cargo[]>response.data),
          tap(() => (this.searchFailed = false)),
          catchError(() => {
            this.searchFailed = true;
            return of([]);
          })
        )
      ),
      tap(() => (this.searching = false))
    );

  buscarPersonas = (text$: Observable<string>): Observable<Persona[]> =>
    text$.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      tap(() => (this.searching = true)),
      switchMap((term) =>
        this.personaService.search(term).pipe(
          map((response) => <Persona[]>response.data),
          tap(() => (this.searchFailed = false)),
          catchError(() => {
            this.searchFailed = true;
            return of([]);
          })
        )
      ),
      tap(() => (this.searching = false))
    );

  resultFormatCargos = (cargos: Cargo) => `${cargos.nombre} ${cargos.division? `(${cargos.division.anio}° ${cargos.division.numDivision})` : ''}`;
  inputFormatCargos = (cargos: Cargo) => cargos.id ? `(${cargos.nombre}  )` : '';


  resultFormatPersonas = (personas: Persona) => `${personas.nombre} ${personas.apellido}, DNI: ${personas.dni}`;
  inputFormatPersonas = (personas: Persona) => personas.dni ? `${personas.nombre} ${personas.apellido}` : '';


  onCargoSelect(event: any) {
    this.designacion.cargo = event.item;
  }
  onPersonaSelect(event: any) {
    this.designacion.persona = event.item;
  }


  save() {
    this.designacion.fechaInicio = `${this.fechaInicioModel.year}-${String(this.fechaInicioModel.month).padStart(2, '0')}-${String(this.fechaInicioModel.day).padStart(2, '0')}T00:00:00`;
    if (this.fechaFinModel) {
      this.designacion.fechaFin = `${this.fechaFinModel.year}-${String(this.fechaFinModel.month).padStart(2, '0')}-${String(this.fechaFinModel.day).padStart(2, '0')}T00:00:00`;
    } else {
      this.designacion.fechaFin = undefined;
    }
    this.errorMessage = null;
    this.successMessage = null;
    this.loading = true;
    this.designacionService.save(this.designacion).subscribe({
      next: dataPackage => {
        this.loading = false;
        // Si el status no es 200, mostrar el mensaje de error
        if (!dataPackage || dataPackage.status !== 200) {
          this.errorMessage = typeof dataPackage?.message === 'string'
            ? dataPackage.message
            : JSON.stringify(dataPackage?.message) || 'Error desconocido al guardar la designación';
        } else {
          this.designacion = <Designacion>dataPackage.data;
          this.successMessage = 'Cargo guardado exitosamente!';
        }
      },
      error: err => {
        this.loading = false;
        this.errorMessage = err.error?.data || err.error || 'Error al guardar la designacion';
      }
    });
  }

  goBack() {
    this.location.back();
  }

  get isNew(): boolean {
    return !this.designacion || this.designacion.id == undefined;
  }


}
