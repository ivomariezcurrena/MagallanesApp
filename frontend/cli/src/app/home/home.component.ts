import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, RouterOutlet } from '@angular/router';
import { LicenciaService } from '../licencia/licencia.service';
import { CalendarComponent } from "./calendar.component";
import { FormsModule } from '@angular/forms';

// Asegúrate de importar el servicio

@Component({
    selector: 'app-home',
    imports: [CommonModule, RouterModule, CalendarComponent, FormsModule],
    templateUrl: `home.component.html`,
    styleUrl: `home.component.css`,
})
export class HomeComponent {
  fechaHoy: string;
  licenciasHoy: number = 0; // Nueva propiedad
  horas = ['08:00', '09:00', '10:00', '11:00', '12:00', '13:00', '14:00', '15:00', '16:00', '17:00', '18:00'];
  
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
