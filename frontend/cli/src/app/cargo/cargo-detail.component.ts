import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { NgbCalendar, NgbDatepickerModule, NgbDateStruct, NgbModule, NgbTypeaheadModule } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { catchError, debounceTime, distinctUntilChanged, map, switchMap, tap } from 'rxjs/operators';
import { Division } from '../division/division';
import { CargoService } from './cargo.service';
import { DivisionService } from '../division/division.service';
import { ModalService } from '../modal/modal.service';
import { Cargo } from './cargo';
import { Location } from '@angular/common';
import { DataPackage } from '../data-package';
import { Horario } from './horario';

@Component({
  selector: 'app-cargo-detail',
  imports: [FormsModule, CommonModule, RouterModule, NgbTypeaheadModule, NgbModule, NgbDatepickerModule],
  templateUrl: `cargo-detail.component.html`,
  styleUrl: `cargo-detail.component.css`
})
export class CargoDetailComponent {
  divisiones: Division[] = [];
  cargo!: Cargo;
  searching: boolean = false;
  searchFailed: boolean = false;
  fechaInicioModel!: NgbDateStruct;
  fechaFinModel!: NgbDateStruct;
  diasSemana: string[] = ['Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado'];
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private cargoService: CargoService,
    private divisionService: DivisionService,
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
      this.cargo = <Cargo><unknown>{
        division: <Division>{},
        horarios: <Horario[]>[]
      };
      const hoy = this.calendar.getToday();
      this.fechaInicioModel = hoy;
    } else {
      this.cargoService.get(parseInt(id!)).subscribe((dataPackage) => {
        this.cargo = <Cargo>dataPackage.data;

        // Transformar strings a NgbDateStruct
        const dInicio = new Date(this.cargo.fechaInicio);
        this.fechaInicioModel = {
          year: dInicio.getFullYear(),
          month: dInicio.getMonth() + 1,
          day: dInicio.getDate()
        };

        if (this.cargo.fechaFin) {
          const dFin = new Date(this.cargo.fechaFin);
          this.fechaFinModel = {
            year: dFin.getFullYear(),
            month: dFin.getMonth() + 1,
            day: dFin.getDate()
          };
        }
      });
    }

    this.divisionService.all().subscribe((dataPackage) => {
      this.divisiones = <Division[]>dataPackage.data;
    });
  }

  buscarDivisiones = (text$: Observable<string>): Observable<Division[]> =>
    text$.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      tap(() => (this.searching = true)),
      switchMap((term) =>
        this.divisionService.search(term).pipe(
          map((response) => <Division[]>response.data),
          tap(() => (this.searchFailed = false)),
          catchError(() => {
            this.searchFailed = true;
            return of([]);
          })
        )
      ),
      tap(() => (this.searching = false))
    );

  // Este formatea la lista del dropdown
  resultFormat = (division: Division) =>
    `${division.orientacion} ${division.anio}º ${division.numDivision}º ${division.turno}`;

  // Este formatea el input cuando se selecciona
  inputFormat = (division: Division) =>
    division.id ? `${division.orientacion} ${division.anio}º ${division.numDivision}º ${division.turno}` : '';

  onDivisionSelect(event: any) {
    this.cargo.division = event.item;
  }

  save() {
    this.cargo.fechaInicio = new Date(
      this.fechaInicioModel.year,
      this.fechaInicioModel.month - 1,
      this.fechaInicioModel.day
    );

    if (this.fechaFinModel) {
      this.cargo.fechaFin = new Date(
        this.fechaFinModel.year,
        this.fechaFinModel.month - 1,
        this.fechaFinModel.day
      );
    } else {
      this.cargo.fechaFin = undefined;
    }

    this.cargoService.save(this.cargo).subscribe(dataPackage => { this.cargo = <Cargo>dataPackage.data; this.goBack(); });

  }
  goBack() {
    this.location.back();
  }
  get isNew(): boolean {
    return this.cargo.id === undefined;
  }


  addHorario() {
    this.cargo.horarios.push({ dia: this.diasSemana[0], hora: 0 });
  }

  removeHorario(horario: Horario) {
    this.modalService.confirm("Eliminar performance", "¿Está seguro de eliminar esta performance?", "El cambio no sera efectivo hasta que guarde el bordero").then((_) => {
      let horarios = this.cargo.horarios;
      horarios.splice(horarios.indexOf(horario), 1);
    }
    )
  }
}