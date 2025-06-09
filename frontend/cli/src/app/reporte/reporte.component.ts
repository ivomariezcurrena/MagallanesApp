import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Licencia } from '../licencia/licencia';
import { LicenciaService } from '../licencia/licencia.service';
import { ResultsPage } from '../results-page';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { PaginationComponent } from '../pagination/pagination.component';
import { ReportePersona } from './ReportePersona';
import { GraficoComponent } from "./grafico.component";

@Component({
  selector: 'app-reporte',
  imports: [CommonModule, FormsModule, RouterModule, PaginationComponent, GraficoComponent],
  templateUrl: './reporte.component.html',
  styleUrl: `reporte.component.css`,
})
export class ReporteComponent {
exportarPDF() {
throw new Error('Method not implemented.');
}
exportarExcel() {
throw new Error('Method not implemented.');
}
  reporte: ReportePersona[] = [];
  reportePersonas: ReportePersona[] = [];
  currentPage = 1;
  pageSize = 10;
  resultsPage: ResultsPage = <ResultsPage>{};
  fechaDesde: string = '';
  totalLicenciasValidas: number = 0;
  totalLicenciasInvalidas: number = 0;

  constructor(
    private licenciaService: LicenciaService, 
  ) { }
  ngOnInit() {
    this.fechaDesde = '2025';
    this.buscar();
    this.cargarTotalLicenciasValidas();
    this.cargarTotalLicenciasInvalidas();
  }

  buscar() {
    if (!this.fechaDesde) {
      this.fechaDesde = new Date().getFullYear().toString();
      return;
    }
    this.licenciaService.reporteConcepto(this.fechaDesde, this.currentPage, this.pageSize).subscribe((dataPackage) => {
      this.resultsPage = <ResultsPage>dataPackage.data;
      this.reportePersonas = Array.isArray(this.resultsPage.content) ? this.resultsPage.content : [];
    });
    this.cargarTotalLicenciasValidas();
    this.cargarTotalLicenciasInvalidas();
  }
  onPageChangeRequested(page: number): void {
    this.currentPage = page;
    this.buscar();
  }
  cargarTotalLicenciasValidas() {
    this.licenciaService.getValidas(this.fechaDesde).subscribe((dataPackage) => {
      console.log('Validas:', dataPackage.data);
      this.totalLicenciasValidas = Array.isArray(dataPackage.data) ? dataPackage.data.length : 0;
    });
  }

  cargarTotalLicenciasInvalidas() {
    this.licenciaService.getInvalidas(this.fechaDesde).subscribe((dataPackage) => {
      this.totalLicenciasInvalidas = Array.isArray(dataPackage.data) ? dataPackage.data.length : 0;
    })
  }

}