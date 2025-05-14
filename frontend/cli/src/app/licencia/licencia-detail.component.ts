import { CommonModule, Location } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { NgbTypeaheadModule, NgbModule, NgbDatepickerModule, NgbDateStruct, NgbCalendar } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { catchError, debounceTime, distinctUntilChanged, map, switchMap, tap } from 'rxjs/operators';

import { Licencia } from './licencia';
import { Persona } from '../personas/persona';
import { ArticuloLicencia } from './aticuloLicencia';
import { LicenciaService } from './licencia.service';
import { PersonaService } from '../personas/persona.service';
import { ArticuloLicenciaService } from './articulo-licencia.service'; // Asumido

@Component({
  selector: 'app-licencia-detail',
  standalone: true,
  imports: [FormsModule, CommonModule, RouterModule, NgbTypeaheadModule, NgbModule, NgbDatepickerModule],
  templateUrl: 'licencia-detail.component.html',
  styleUrl: 'licencia-detail.component.css'
})
export class LicenciaDetailComponent {
  licencia!: Licencia;
  personas: Persona[] = [];
  articulos: ArticuloLicencia[] = [];

  fechaInicioModel!: NgbDateStruct;
  fechaFinModel!: NgbDateStruct;

  searching: boolean = false;
  searchFailed: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private licenciaService: LicenciaService,
    private personaService: PersonaService,
    private articuloService: ArticuloLicenciaService,
    private calendar: NgbCalendar,
    private location: Location
  ) {}

  ngOnInit() {
    this.get();
  }

  get() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id === 'new') {
      this.licencia = <Licencia><unknown>{
        persona: <Persona>{},
        articuloLicencia: <ArticuloLicencia>{}
      };
      const hoy = this.calendar.getToday();
      this.fechaInicioModel = hoy;
    } else {
      this.licenciaService.get(+id!).subscribe(dataPackage => {
        this.licencia = dataPackage.data as Licencia;

        const dInicio = new Date(this.licencia.pedidoDesde);
        this.fechaInicioModel = {
          year: dInicio.getFullYear(),
          month: dInicio.getMonth() + 1,
          day: dInicio.getDate()
        };

        if (this.licencia.pedidoHasta) {
          const dFin = new Date(this.licencia.pedidoHasta);
          this.fechaFinModel = {
            year: dFin.getFullYear(),
            month: dFin.getMonth() + 1,
            day: dFin.getDate()
          };
        }
      });
    }

    this.personaService.all().subscribe((dataPackage) => {this.personas = <Persona[]>dataPackage.data});
    this.articuloService.all().subscribe((dataPackage) => {this.articulos = <ArticuloLicencia[]>dataPackage.data});
  }

  buscarPersonas = (text$: Observable<string>): Observable<Persona[]> =>
    text$.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      tap(() => this.searching = true),
      switchMap(term =>
        this.personaService.search(term).pipe(
          map((response) => <Persona[]>response.data),
          tap(() => this.searchFailed = false),
          catchError(() => {
            this.searchFailed = true;
            return of([]);
          })
        )
      ),
      tap(() => this.searching = false)
    );

  buscarArticulos = (text$: Observable<string>): Observable<ArticuloLicencia[]> =>
    text$.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      tap(() => this.searching = true),
      switchMap(term =>
        this.articuloService.search(term).pipe(
          map((response) => <ArticuloLicencia[]>response.data),
          tap(() => this.searchFailed = false),
          catchError(() => {
            this.searchFailed = true;
            return of([]);
          })
        )
      ),
      tap(() => this.searching = false)
    );

  resultFormatPersonas = (p: Persona) => `${p.nombre} ${p.apellido}, DNI: ${p.dni}`;
  inputFormatPersonas = (p: Persona) => p ? `${p.nombre} ${p.apellido}` : '';

  resultFormatArticulos = (a: ArticuloLicencia) => `${a.articulo} - ${a.descripcion}`;
  inputFormatArticulos = (a: ArticuloLicencia) => a ? `${a.articulo}` : '';

  onPersonaSelect(event: any) {
    this.licencia.persona = event.item;
  }

  onArticuloSelect(event: any) {
    this.licencia.articuloLicencia = event.item;
  }

  save() {
    this.licencia.pedidoDesde = new Date(
      this.fechaInicioModel.year,
      this.fechaInicioModel.month - 1,
      this.fechaInicioModel.day
    ).toISOString();

    if (this.fechaFinModel) {
      this.licencia.pedidoHasta = new Date(
        this.fechaFinModel.year,
        this.fechaFinModel.month - 1,
        this.fechaFinModel.day
      ).toISOString();
    }

    this.licenciaService.save(this.licencia).subscribe(dataPackage => {
      this.licencia = <Licencia>dataPackage.data;
      this.goBack();
    });
  }

  goBack() {
    this.location.back();
  }

  get isNew(): boolean {
    return this.licencia.id == undefined;
  }
}
