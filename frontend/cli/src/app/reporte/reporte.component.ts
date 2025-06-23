import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Licencia } from '../licencia/licencia';
import { LicenciaService } from '../licencia/licencia.service';
import { ResultsPage } from '../results-page';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { PaginationComponent } from '../pagination/pagination.component';
import { ReportePersona } from './ReportePersona';
import generatePDF from './pdf';
import { ExcelService } from './exel';

@Component({
  selector: 'app-reporte',
  imports: [CommonModule, FormsModule, RouterModule, PaginationComponent],
  templateUrl: './reporte.component.html',
  styleUrl: `reporte.component.css`,
})
export class ReporteComponent {

  reporte: ReportePersona[] = [];
  reportePersonas: ReportePersona[] = [];
  currentPage = 1;
  pageSize = 10;
  resultsPage: ResultsPage = <ResultsPage>{};
  fechaDesde: string = '';
  totalLicenciasValidas: number = 0;
  totalLicenciasInvalidas: number = 0;
  totalLicencias: number = 0;
  constructor(
    private licenciaService: LicenciaService,
    private excelService: ExcelService
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
      this.totalLicenciasValidas = Array.isArray(dataPackage.data) ? dataPackage.data.length : 0;
    });
  }

  cargarTotalLicenciasInvalidas() {
    this.licenciaService.getInvalidas(this.fechaDesde).subscribe((dataPackage) => {
      this.totalLicenciasInvalidas = Array.isArray(dataPackage.data) ? dataPackage.data.length : 0;
    })
  }

  // cargarTotalLicencias() {
  //   this.licenciaService.byAño(this.fechaDesde).subscribe((dataPackage) => {
  //     console.log('Total Licencias:', dataPackage.data);
  //     this.totalLicencias = Array.isArray(dataPackage.data) ? dataPackage.data.length : 0;
  //   });
  // }

  exportarPDF() {
    // Pedir todos los resultados (por ejemplo, hasta 10 000)
    this.licenciaService.reporteConcepto(this.fechaDesde, 1, 10000).subscribe((dataPackage) => {
      const resultsPage = dataPackage.data as ResultsPage;
      const allPersonas = Array.isArray(resultsPage.content) ? resultsPage.content : [];
      generatePDF(allPersonas, this.fechaDesde, this.totalLicenciasValidas, this.totalLicenciasInvalidas);
    });
  }
  exportarExcel() {
    this.licenciaService.reporteConcepto(this.fechaDesde, 1, 10000).subscribe((dataPackage) => {
      const resultsPage = dataPackage.data as ResultsPage;
      const allPersonas = Array.isArray(resultsPage.content) ? resultsPage.content : [];
      this.excelService.exportarReporte(allPersonas, this.fechaDesde);
    });
  }

}