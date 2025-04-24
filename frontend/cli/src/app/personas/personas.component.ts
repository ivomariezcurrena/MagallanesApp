import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';         // ← IMPORTARLO
import { Persona } from './persona';
import { PersonaService } from './persona.service';
import { ResultsPage } from '../results-page';
import { PaginationComponent } from '../pagination/pagination.component';
import { ModalService } from '../modal/modal.service';


@Component({
  selector: 'app-personas',
  imports: [CommonModule, FormsModule, RouterModule, PaginationComponent],
  templateUrl: `personas.component.html`,
  styleUrl: `personas.component.css`,

})
export class PersonasComponent {
  // Array “maestro” para la page actual
  personas: Persona[] = [];
  // Array para el filtrado
  filteredPersonas: Persona[] = [];
  searchTerm: string = '';
  currentPage = 1;
  pageSize = 10;
  resultsPage: ResultsPage = <ResultsPage>{};

  constructor(private personaService: PersonaService,
    private modalService: ModalService
  ) { }

  ngOnInit() {
    this.getPersonas();
  }

  getPersonas() {
    this.personaService.byPage(this.currentPage, this.pageSize)
      .subscribe((dataPackage) => {
        this.resultsPage = <ResultsPage>dataPackage.data;
        this.personas = this.resultsPage.content;
        this.filteredPersonas = [...this.personas];
      });
  }

  //         
  //

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

  remove(dni: number): void {
    let that = this;
    this.modalService.confirm("Eliminar persona", "¿Esta seguro que desea eliminar la persona?", "La operacion no tiene vuelta atras").then(
      function () {
        that.personaService.remove(dni).subscribe(dataPackage => that.getPersonas());
      }
    )
  }

  onPageChangeRequested(page: number): void {
    this.currentPage = page;
    this.getPersonas();
  }
}
