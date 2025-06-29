import { CommonModule, Location } from '@angular/common';
import { Component, ViewChild, ElementRef } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { NgbTypeaheadModule, NgbModule, NgbDatepickerModule, NgbDateStruct, NgbCalendar } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { catchError, debounceTime, distinctUntilChanged, map, switchMap, tap } from 'rxjs/operators';
import { trigger, transition, style, animate } from '@angular/animations';
import { Licencia, Log } from './licencia';
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
  styleUrl: 'licencia-detail.component.css',
  animations: [
    trigger('fadeInOut', [
      transition(':enter', [
        style({ opacity: 0, maxHeight: '0', marginBottom: '0', overflow: 'hidden', transform: 'translateY(-20px)' }),
        animate('500ms cubic-bezier(.4,2,.6,1)', style({ opacity: 1, maxHeight: '200px', marginBottom: '2rem', overflow: 'hidden', transform: 'translateY(0)' }))
      ]),
      transition(':leave', [
        animate('300ms cubic-bezier(.4,2,.6,1)', style({ opacity: 0, maxHeight: '0', marginBottom: '0', overflow: 'hidden', transform: 'translateY(-20px)' }))
      ])
    ])
  ]
})
export class LicenciaDetailComponent {
  licencia!: Licencia;
  personas: Persona[] = [];
  articulos: ArticuloLicencia[] = [];
  logs: Log[] = [];
  successMessage: string | null = null;
  fechaInicioModel!: NgbDateStruct;
  fechaFinModel!: NgbDateStruct;
  searching: boolean = false;
  searchFailed: boolean = false;
  errorMessage: string | null = null;
  loading = false;

  @ViewChild('errorAlert') errorAlert!: ElementRef<HTMLDivElement>;

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
      this.licencia = <Licencia>{
        pedidoDesde: '',
        pedidoHasta: '',
        domicilio: '',
        certificadoMedico: false,
        persona: <Persona>{},
        articulo: <ArticuloLicencia>{}
      };
      const hoy = this.calendar.getToday();
      this.fechaInicioModel = hoy;
    } else {
      this.licenciaService.get(+id!).subscribe(dataPackage => {
        this.licencia = dataPackage.data as Licencia;
        if (this.licencia.certificadoMedico === undefined) {
          this.licencia.certificadoMedico = false;
        }

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
  inputFormatPersonas = (p: Persona) => p.dni ? `${p.nombre} ${p.apellido}` : '';

  resultFormatArticulos = (a: ArticuloLicencia) => `${a.articulo} - ${a.descripcion}`;
  inputFormatArticulos = (a: ArticuloLicencia) => a.id ? `${a.articulo}` : '';

  onPersonaSelect(event: any) {
    this.licencia.persona = event.item;
  }

  onArticuloSelect(event: any) {
    this.licencia.articulo = event.item;
  }

  save() {
    // Enviar la fecha como string yyyy-MM-ddT00:00:00 para que Java lo acepte como LocalDateTime
    this.licencia.pedidoDesde = `${this.fechaInicioModel.year}-${String(this.fechaInicioModel.month).padStart(2, '0')}-${String(this.fechaInicioModel.day).padStart(2, '0')}T00:00:00`;
    if (this.fechaFinModel) {
      this.licencia.pedidoHasta = `${this.fechaFinModel.year}-${String(this.fechaFinModel.month).padStart(2, '0')}-${String(this.fechaFinModel.day).padStart(2, '0')}T00:00:00`;
    }
    this.errorMessage = null;
    this.successMessage = null;
    this.loading = true;
    this.licenciaService.save(this.licencia).subscribe({
      next: dataPackage => {
        this.loading = false;
        const licenciaGuardada = dataPackage.data as Licencia;
        const ultimoLogMensaje = dataPackage.message; // El backend envía el mensaje del último log

        // Si el status no es 200, mostrar el mensaje de error
        if (!dataPackage || dataPackage.status !== 200) {
          this.errorMessage = ultimoLogMensaje || 'Error desconocido al guardar la licencia';
          this.licencia = licenciaGuardada; // Refresca los logs y estado
        } else {
          this.licencia = licenciaGuardada;
          // Busca el último log (si existe)
          const logs = this.licencia.logs || [];
          const ultimoLog = logs.length > 0 ? logs[logs.length - 1] : null;

          if (ultimoLog && ultimoLog.estatus !== 200) {
            // Si el último log indica error, muestra su mensaje
            this.errorMessage = ultimoLog.mensaje || ultimoLogMensaje || 'La licencia no es válida. Revisá los logs.';
          } else {
            if (this.isNew) {
              this.successMessage = '¡Licencia creada exitosamente!';
            } else {
              this.successMessage = '¡Licencia actualizada exitosamente!';
              this.get(); // Recarga la licencia desde el backend
            }
          }
        }
      },
      error: err => {
        this.loading = false;
        this.errorMessage = err.error?.data || err.error || 'Error creando la licencia';
        setTimeout(() => {
          if (this.errorAlert) {
            this.errorAlert.nativeElement.scrollIntoView({ behavior: 'smooth', block: 'center' });
          }
        }, 100);
      }
    });
  }

  goBack() {
    this.location.back();
  }

  get isNew(): boolean {
    return !this.licencia || this.licencia.id == undefined;
  }
}
