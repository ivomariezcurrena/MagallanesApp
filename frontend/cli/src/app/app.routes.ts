import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { PersonasComponent } from './personas/personas.component';
import { PersonaDetailComponent } from './personas/persona-detail.component';
import { DivisionesComponent } from './division/divisiones.component';
import { DivisionDetailComponent } from './division/division-detail.component';
import { CargoComponent } from './cargo/cargo.component';

export const routes: Routes = [
    { path: '', component: HomeComponent },
    { path: 'personas', component: PersonasComponent },
    { path: 'personas/dni/:dni', component: PersonaDetailComponent },
    { path: 'divisiones', component: DivisionesComponent },
    { path: 'divisiones/:id', component: DivisionDetailComponent },
    { path: 'cargos', component: CargoComponent } // Redirigir a la página de inicio si la ruta no coincide
];