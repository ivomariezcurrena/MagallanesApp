import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { PersonasComponent } from './personas/personas.component';
import { PersonaDetailComponent } from './personas/persona-detail.component';
import { DivisionComponent } from './division/division.component';

export const routes: Routes = [
    { path: '', component: HomeComponent },
    { path: 'personas', component: PersonasComponent },
    { path: 'personas/dni/:dni', component: PersonaDetailComponent },
    { path: 'divisiones', component: DivisionComponent }
];