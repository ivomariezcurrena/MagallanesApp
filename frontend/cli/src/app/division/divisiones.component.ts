import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule, TouchedChangeEvent } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { PaginationComponent } from '../pagination/pagination.component';
import { ModalService } from '../modal/modal.service';
import { ResultsPage } from '../results-page';
import { DivisionService } from './division.service';
import { Division } from './division';

@Component({
  selector: 'app-division',
  imports: [CommonModule, FormsModule, RouterModule, PaginationComponent],
  templateUrl: `divisiones.component.html`,
  styleUrl: `divisiones.component.css`,
})
export class DivisionesComponent {
  divisiones: Division[] = [];
  filteredDivisiones: Division[] = [];
  orientaciones: string[] = [];
  searchOrientacion: string = '';
  currentPage = 1;
  pageSize = 10;
  resultsPage: ResultsPage = <ResultsPage>{};
  constructor(private DivisionService: DivisionService,
    private modalService: ModalService
  ) { }

  ngOnInit() {
    this.loadOrientaciones();
    this.fetchDivisiones();
  }


  getDivisiones() {
    this.DivisionService.byPage(this.currentPage, this.pageSize)
      .subscribe((dataPackage) => {
        this.resultsPage = <ResultsPage>dataPackage.data;
        this.divisiones = this.resultsPage.content;
        this.filteredDivisiones = [...this.divisiones];
      });
  }

  remove(id: number) {
    let that = this;
    this.modalService.confirm("Eliminar división", "¿Está seguro de eliminar la división?", "La división se eliminará permanentemente").then(function () {
      that.DivisionService.remove(id).subscribe(dataPackage => that.getDivisiones());
    })
  }
  loadOrientaciones() {
    this.DivisionService.getOrientaciones()
      .subscribe(res => this.orientaciones = res.data as string[]);
  }

  fetchDivisiones() {
    const term = this.searchOrientacion.trim();
    // Si hay filtro, uso searchOrientacion (server-side, paginado)
    if (term) {
      this.DivisionService.searchOrientacion(term, this.currentPage - 1, this.pageSize)
        .subscribe(pkg => {
          this.resultsPage = <ResultsPage>pkg.data;
          this.filteredDivisiones = this.resultsPage.content;
        });
    }
    // Si no hay filtro, pido byPage normal
    else {
      this.DivisionService.byPage(this.currentPage, this.pageSize)
        .subscribe(pkg => {
          this.resultsPage = <ResultsPage>pkg.data;
          this.filteredDivisiones = this.resultsPage.content;
        });
    }
  }

  filtrarDivisiones() {
    this.currentPage = 1;
    this.fetchDivisiones();
  }


  onPageChangeRequested(page: number): void {
    this.currentPage = page;
    this.fetchDivisiones();
  }
}
