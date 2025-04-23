import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';         // ← IMPORTARLO
import { Persona } from './persona';
import { PersonaService } from './persona.service';

@Component({
  selector: 'app-personas',
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: `personas.component.html`,
  styleUrl: `personas.component.css`,

})
export class PersonasComponent {
  personas: Persona[] = [];
  filteredPersonas: Persona[] = [];
  searchTerm: string = '';

  constructor(private personaService: PersonaService) { }

  ngOnInit() {
    this.personas = this.personaService.obtenerTodas();
    this.filteredPersonas = [...this.personas];
  }

  buscar() {
    // 1. Preparamos el término: minúsculas + sin tildes
    const raw = this.searchTerm.trim().toLowerCase();
    const term = raw.normalize('NFD').replace(/[\u0300-\u036f]/g, '');

    if (!term) {
      this.filteredPersonas = [...this.personas];
      return;
    }

    this.filteredPersonas = this.personas.filter(p => {
      // 2. Minúsculas + sin tildes para nombre y apellido
      const nombre = p.nombre
        .toLowerCase()
        .normalize('NFD')
        .replace(/[\u0300-\u036f]/g, '');

      const apellido = p.apellido
        .toLowerCase()
        .normalize('NFD')
        .replace(/[\u0300-\u036f]/g, '');

      // 3. DNI como cadena normal (no tiene tildes)
      const dni = p.dni.toString();

      // 4. Chequeamos coincidencias
      return (
        nombre.includes(term) ||
        apellido.includes(term) ||
        dni.includes(term)
      );
    });
  }
  limpiar() {
    this.searchTerm = '';
    this.filteredPersonas = [...this.personas];
  }
}
