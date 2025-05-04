import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { PaginationComponent } from '../pagination/pagination.component';
import { Designacion } from './designacion';
import { ResultsPage } from '../results-page';
import { DesignacionService } from './designacion.service';
import { ModalService } from '../modal/modal.service';

@Component({
  selector: 'app-desingancion',
  imports: [CommonModule, FormsModule, RouterModule, PaginationComponent],
  templateUrl: `desingancion.component.html`,
  styleUrl: `desingancion.component.css`
})
export class DesingancionComponent {
  designaciones: Designacion[] = [];
  fliteredDesignaciones: Designacion[] = [];
  searchTerm: string = '';
  currentPage = 1;
  pageSize = 10;
  resultsPage: ResultsPage = <ResultsPage>{};
  constructor(private designacionService: DesignacionService, private modalService: ModalService) { }

  ngOnInit() {
    this.getDesignaciones();
  }
  getDesignaciones() {
    this.designacionService.byPage(this.currentPage, this.pageSize).subscribe((dataPackage) => {
      this.resultsPage = <ResultsPage>dataPackage.data;
      this.designaciones = this.resultsPage.content;
      this.fliteredDesignaciones = [...this.designaciones];
    });
  }

  buscar() {
    const term = this.searchTerm.trim();
    if (!term) {
      this.getDesignaciones();
      return;
    }
    this.currentPage = 1;
    this.designacionService.searchTerm(term, 0, this.pageSize).subscribe((dataPackage) => {
      this.resultsPage = <ResultsPage>dataPackage.data;
      this.fliteredDesignaciones = this.resultsPage.content;
    });
  }

  limpiar() {
    this.searchTerm = '';
    this.getDesignaciones();

  }

  remove(id: number): void {
    let that = this;
    this.modalService.confirm("Eliminar Designacion", "¿Esta seguro que desea eliminar la designacion?", "La operacion no tiene vuelta atras").then(
      function () {
        that.designacionService.remove(id).subscribe(datapackage => that.getDesignaciones());
      }
    )
  }


  onPageChangeRequested(page: number): void {
    this.currentPage = page;
    this.getDesignaciones();
  }
}
