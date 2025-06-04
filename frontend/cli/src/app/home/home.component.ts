import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, RouterOutlet } from '@angular/router';
import { GraficoComponent } from "./grafico.component";
import { PartediarioComponent } from "../licencia/partediario.component";
import { LicenciaService } from '../licencia/licencia.service';
import { FullCalendarModule } from '@fullcalendar/angular';
import dayGridPlugin from '@fullcalendar/daygrid';
import interactionPlugin from '@fullcalendar/interaction';
import { CalendarComponent } from "./calendar.component";
// Asegúrate de importar el servicio

@Component({
    selector: 'app-home',
    imports: [CommonModule, RouterOutlet, GraficoComponent, RouterModule, PartediarioComponent, CalendarComponent],
    templateUrl: `home.component.html`,
    styleUrl: `home.component.css`,
})
export class HomeComponent {
  fechaHoy: string;
  licenciasHoy: number = 0; // Nueva propiedad

  constructor(private licenciaService: LicenciaService) {
    const hoy = new Date();
    const opciones: Intl.DateTimeFormatOptions = { day: 'numeric', month: 'long', year: 'numeric' };
    this.fechaHoy = `Hoy es ${hoy.toLocaleDateString('es-ES', opciones)}`;
  }

  ngOnInit() {
    this.getLicenciasHoy();
  }

  getLicenciasHoy() {
    const hoy = new Date();
    const fecha = hoy.toISOString().slice(0, 10);
    this.licenciaService.byFechaDesde(fecha, 1, 1000).subscribe(dataPackage => {
      const data = dataPackage.data as { totalElements?: number };
      this.licenciasHoy = data.totalElements || 0;
    });
  }
}
