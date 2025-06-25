import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { PaginationComponent } from '../pagination/pagination.component';
import { ModalService } from '../modal/modal.service';
import { ResultsPage } from '../results-page';
import { Licencia } from './licencia';
import { LicenciaService } from './licencia.service';

@Component({
  selector: 'app-partediario',
  imports: [CommonModule, FormsModule, RouterModule, PaginationComponent],
  templateUrl: `partediario.component.html`,
  styleUrl: `licencia.component.css`
})
export class PartediarioComponent {
  licencias: Licencia[] = [];
  filtredLicencias: Licencia[] = [];
  currentPage = 1;
  pageSize = 10;
  resultsPage: ResultsPage = <ResultsPage>{};
  fechaDesde: string = '';

  constructor(private licenciaService: LicenciaService,
    private modalService: ModalService
  ) { }

  ngOnInit() {
    this.getLicencias();
  }

  getLicencias() {
    this.licenciaService.byPageValidas(this.currentPage, this.pageSize).subscribe((dataPackage) => {
      this.resultsPage = <ResultsPage>dataPackage.data;
      this.licencias = this.resultsPage.content;
      this.filtredLicencias = [...this.licencias];
    })
  }

  buscarPorFecha() {
    if (!this.fechaDesde) {
      this.getLicencias();
      return;
    }
    this.licenciaService.byFechaDesde(this.fechaDesde, this.currentPage, this.pageSize).subscribe((dataPackage) => {
      this.resultsPage = <ResultsPage>dataPackage.data;
      this.licencias = this.resultsPage.content;
      this.filtredLicencias = [...this.licencias];
    });
  }
  limpiar() {
    this.fechaDesde = '';
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
    if (this.fechaDesde) {
      this.buscarPorFecha();
    } else {
      this.getLicencias();
    }
  }
  abrirModalSuplente(licencia: Licencia) {
    this.licenciaService.getPrimerSuplenteDeLicencia(licencia.id,this.fechaDesde).subscribe(dataPackage => {
      const suplente = dataPackage.data as any;
      if (!suplente) {
        this.modalService.confirm(
          'Sin suplente',
          'Esta licencia no tiene suplente asignado.',
          ''
        );
      } else {
        const inicio = new Date(suplente.fechaInicio);
        const fin = new Date(suplente.fechaFin);
        const formatoArgentino = new Intl.DateTimeFormat('es-AR');
        const inicioFormateado = formatoArgentino.format(inicio);
        const finFormateado = formatoArgentino.format(fin);
        this.modalService.confirm(
          'Suplente asignado',
          `Suplente: ${suplente.persona?.nombre} ${suplente.persona?.apellido}`,
          `Está suplantando a: ${licencia.persona?.nombre} ${licencia.persona?.apellido}, para el periodo: ${inicioFormateado} hasta ${finFormateado}`
        );
      }
    });
  }
}
