import { Component } from '@angular/core';

@Component({
    selector: 'app-home',
    imports: [],
    templateUrl: `home.component.html`,
    styleUrl: `home.component.css`,
})
export class HomeComponent {
  fechaHoy: string;

  constructor() {
    const hoy = new Date();
    const opciones: Intl.DateTimeFormatOptions = { day: 'numeric', month: 'long', year: 'numeric' };
    this.fechaHoy = `Hoy es ${hoy.toLocaleDateString('es-ES', opciones)}`;
  }
}
