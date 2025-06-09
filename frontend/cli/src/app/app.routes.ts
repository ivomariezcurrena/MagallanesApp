import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { PersonasComponent } from './personas/personas.component';
import { PersonaDetailComponent } from './personas/persona-detail.component';
import { DivisionesComponent } from './division/divisiones.component';
import { DivisionDetailComponent } from './division/division-detail.component';
import { CargoComponent } from './cargo/cargo.component';
import { CargoDetailComponent } from './cargo/cargo-detail.component';
import { DesingancionComponent } from './designacion/desingancion.component';
import { DesignacionDetailComponent } from './designacion/designacion-detail.component';
import { LicenciaComponent } from './licencia/licencia.component';
import { LicenciaDetailComponent } from './licencia/licencia-detail.component';
import { PartediarioComponent } from './licencia/partediario.component';
import { LicenciasLayoutComponent } from './licencia/licencias-layout.component';
import { ReporteComponent } from './reporte/reporte.component';

export const routes: Routes = [
    { path: '', component: HomeComponent },
    { path: 'personas', component: PersonasComponent },
    { path: 'personas/dni/:dni', component: PersonaDetailComponent },
    { path: 'divisiones', component: DivisionesComponent },
    { path: 'divisiones/:id', component: DivisionDetailComponent },
    { path: 'cargos', component: CargoComponent },
    { path: 'cargos/:id', component: CargoDetailComponent },
    { path: 'designaciones', component: DesingancionComponent },
    { path: 'designaciones/:id', component: DesignacionDetailComponent },
    { path: 'licencias/:id', component: LicenciaDetailComponent },
    {
        path: '',
        component: LicenciasLayoutComponent,
        children: [
          { path: 'licencias', component: LicenciaComponent },
          { path: 'partesdiarios', component: PartediarioComponent },
        ]
    },
    { path: 'reportes', component: ReporteComponent }
];