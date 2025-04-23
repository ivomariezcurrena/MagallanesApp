import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { PersonasComponent } from './personas/personas.component';
import { PersonaDetailComponent } from './personas/persona-detail.component';

export const routes: Routes = [
    { path: '', component: HomeComponent },
    { path: 'personas', component: PersonasComponent },
    { path: 'personas/:dni', component: PersonaDetailComponent }
];