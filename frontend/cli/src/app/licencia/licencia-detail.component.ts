import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { NgbTypeaheadModule, NgbModule, NgbDatepickerModule, NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';
import { ArticuloLicencia } from './aticuloLicencia';
import { Persona } from '../personas/persona';
import { Licencia } from './licencia';
import { LicenciaService } from './licencia.service';

@Component({
  selector: 'app-licencia-detail',
  imports: [FormsModule, CommonModule, RouterModule, NgbTypeaheadModule, NgbModule, NgbDatepickerModule],
  templateUrl: `licencia-detail.component.html`,
  styleUrl: `licencia-detail.component.css`
})
export class LicenciaDetailComponent {
  personas: Persona[] = [];
  articuloLicencia: ArticuloLicencia[] = [];
  licencia!: Licencia;
  searching: boolean = false;
  searchFailed: boolean = false;
  fechaInicioModel!: NgbDateStruct;
  fechaFinModel!: NgbDateStruct;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private licenciaService: LicenciaService,

  ) { }

}
