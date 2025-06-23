import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, RouterOutlet, Router } from '@angular/router';
import { LicenciaService } from '../licencia/licencia.service';
import { FormsModule } from '@angular/forms';
import { CargoService } from '../cargo/cargo.service';
import { DivisionService } from '../division/division.service';
import { Division } from '../division/division';
import { Cargo } from '../cargo/cargo';
import generatePDF from './pdf';

// Asegúrate de importar el servicio

@Component({
    selector: 'app-home',
    imports: [CommonModule, RouterModule, FormsModule],
    templateUrl: `home.component.html`,
    styleUrl: `home.component.css`,
})
export class HomeComponent {

  fechaHoy: string;
  licenciasHoy: number = 0; // Nueva propiedad
  horas = ['07:00','08:00', '09:00', '10:00', '11:00', '12:00', '13:00', '14:00', '15:00', '16:00', '17:00', '18:00'];
  divisiones: Division[] = [];
  divisionSeleccionada?: Division;
  cargosDivision: Cargo[] = [];
  
  constructor(
    private licenciaService: LicenciaService,
    private cargoService: CargoService,
    private divisionService: DivisionService,
    private router: Router // <--- agrega esto
  ) {
    const hoy = new Date();
    const opciones: Intl.DateTimeFormatOptions = { day: 'numeric', month: 'long', year: 'numeric' };
    this.fechaHoy = `Hoy es ${hoy.toLocaleDateString('es-ES', opciones)}`;
  }

  ngOnInit() {
    this.getLicenciasHoy();
    this.cargarDivisiones();
  }

  getLicenciasHoy() {
    const hoy = new Date();
    const fecha = hoy.toISOString().slice(0, 10);
    this.licenciaService.byFechaDesde(fecha, 1, 1000).subscribe(dataPackage => {
      const data = dataPackage.data as { totalElements?: number };
      this.licenciasHoy = data.totalElements || 0;
    });
  }
  cargarDivisiones() {
    this.divisionService.all().subscribe(dataPackage => {
      this.divisiones = dataPackage.data as Division[];
      if (this.divisiones.length > 0) {
        this.divisionSeleccionada = this.divisiones[0];
        this.onDivisionChange();
      }
    });
  }
  onDivisionChange() {
    if (this.divisionSeleccionada) {
      this.cargoService.getByAnioYNumero(
        this.divisionSeleccionada.anio,
        this.divisionSeleccionada.numDivision
      ).subscribe(dataPackage => {
        this.cargosDivision = dataPackage.data as Cargo[];
      });
    }
  }
  getCargoEnHorario(dia: string, hora: string): Cargo[] {
    const horaNum = parseInt(hora.split(':')[0], 10);
    return this.cargosDivision.filter(cargo =>
      cargo.horarios.some(h => h.dia === dia && h.hora === horaNum)
    );
  }
  getHorasFiltradas(): string[] {
    if (!this.divisionSeleccionada) return this.horas;
    const turno = this.divisionSeleccionada.turno?.toLowerCase() || '';
    if (turno.includes('mañana')) {
      return this.horas.filter(h => parseInt(h, 10) <= 12);
    }
    if (turno.includes('tarde')) {
      return this.horas.filter(h => {
        const horaNum = parseInt(h, 10);
        return horaNum >= 13 && horaNum <= 18;
      });
    }
    // Si no hay turno, mostrar todas
    return this.horas;
  }
  irADetalleCargo(id: number) {
    this.router.navigate(['/cargos', id]);
  }
  exportarPDF() {
    if (this.divisionSeleccionada) {
      generatePDF(this.cargosDivision, this.getHorasFiltradas(), this.divisionSeleccionada);
    }
  }
}
