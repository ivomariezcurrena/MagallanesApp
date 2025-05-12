import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { PaginationComponent } from '../pagination/pagination.component';
import { Licencia } from './licencia';
import { ResultsPage } from '../results-page';
import { LicenciaService } from './licencia.service';
import { ModalService } from '../modal/modal.service';

@Component({
  selector: 'app-licencia',
  imports: [CommonModule, FormsModule, RouterModule, PaginationComponent],
  templateUrl: `licencia.component.html`,
  styleUrl: `licencia.component.css`
})
export class LicenciaComponent {
  licencias: Licencia[] = [];
  filtredLicencias: Licencia[] = [];
  searchTerm: string = '';
  currentPage = 1;
  pageSize = 10;
  resultsPage: ResultsPage = <ResultsPage>{};

  constructor(private licenciaService: LicenciaService,
    private modalService: ModalService
  ) { }

  ngOnInit() {
    this.getLicencias();
  }

  getLicencias() {
    this.licenciaService.byPage(this.currentPage, this.pageSize).subscribe((dataPackage) => {
      this.resultsPage = <ResultsPage>dataPackage.data;
      this.licencias = this.resultsPage.content;
      this.filtredLicencias = [...this.licencias];
    })
  }

  buscar() {
    const term = this.searchTerm.trim();
    if (!term) {
      this.getLicencias();
      return;
    }

    this.currentPage = 1;
    this.licenciaService.searchTerm(term, 0, this.pageSize)
      .subscribe((dataPackage) => {
        this.resultsPage = <ResultsPage>dataPackage.data;
        this.filtredLicencias = this.resultsPage.content;
      });
  }
  limpiar() {
    this.searchTerm = '';
    this.getLicencias();
  }

  remove(id: number): void {
    let that = this;
    this.modalService.confirm("Eliminar licencia", "¿Esta seguro que desea eliminar esta licencia?", "La operacion no tiene vuelta atras").then(
      function () {
        that.licenciaService.remove(id).subscribe(dataPackage => that.getLicencias());
      }
    )
  }

  onPageChangeRequested(page: number): void {
    this.currentPage = page;
    this.getLicencias();
  }
}
