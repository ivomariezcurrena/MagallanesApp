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
    const term = this.searchTerm.trim();
    if (!term) {
      this.getPersonas();
      return;
    }

    this.currentPage = 1;
    this.personaService.searchTerm(term, 0, this.pageSize)
      .subscribe((dataPackage) => {
        this.resultsPage = <ResultsPage>dataPackage.data;
        this.filteredPersonas = this.resultsPage.content;
      });
  }
  limpiar() {
    this.searchTerm = '';
    this.getPersonas();
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
