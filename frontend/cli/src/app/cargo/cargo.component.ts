import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { PaginationComponent } from '../pagination/pagination.component';
import { ModalService } from '../modal/modal.service';
import { ResultsPage } from '../results-page';
import { CargoService } from './cargo.service';
import { Cargo } from './cargo';

@Component({
  selector: 'app-cargo',
  imports: [CommonModule, FormsModule, RouterModule, PaginationComponent],
  templateUrl: `cargo.component.html`,
  styleUrl: `cargo.component.css`,
})
export class CargoComponent {
  cargos: Cargo[] = [];
  filteredCargos: Cargo[] = [];
  searchTerm: string = '';
  currentPage = 1;
  pageSize = 10;
  resultsPage: ResultsPage = <ResultsPage>{};
  constructor(private cargoService: CargoService, private modalService: ModalService) { }


  ngOnInit() {
    this.getCargos();
  }


  getCargos() {
    this.cargoService.byPage(this.currentPage, this.pageSize)
      .subscribe((dataPackage) => {
        this.resultsPage = <ResultsPage>dataPackage.data;
        this.cargos = this.resultsPage.content;
        this.filteredCargos = [...this.cargos];
      });
  }


  buscar() {
    const term = this.searchTerm.trim();
    if (!term) {
      this.getCargos();
      return;
    }
    this.currentPage = 1;
    this.cargoService.searchTerm(term, 0, this.pageSize)
      .subscribe((dataPackage) => {
        this.resultsPage = <ResultsPage>dataPackage.data;
        this.filteredCargos = this.resultsPage.content;
      });
  }


  limpiar() {
    this.searchTerm = '';
    this.getCargos();
  }


  remove(id: number): void {
    let that = this;
    this.modalService.confirm("Eliminar persona", "¿Esta seguro que desea eliminar la persona?", "La operacion no tiene vuelta atras").then(
      function () {
        that.cargoService.remove(id).subscribe(dataPackage => that.getCargos());
      }
    )
  }

  onPageChangeRequested(page: number): void {
    this.currentPage = page;
    this.getCargos();
  }
}
