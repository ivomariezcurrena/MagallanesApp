import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-tabs-licencias',
  imports: [RouterModule],
  template: `
<ul class="nav nav-tabs penguin-tabs flex-grow-1 position-relative">
    <li class="nav-item">
        <a class="nav-link" [routerLink]="['/licencias']" routerLinkActive="active" aria-current="page">
            Lista de Licencias
        </a>
    </li>
    <li class="nav-item">
        <a class="nav-link" [routerLink]="['/partesdiarios']" routerLinkActive="active">
            Partes Diarios
        </a>
    </li>
    <div class="penguin-underline"></div>
</ul>
  `,
  styleUrl: `licencia.component.css`,
})
export class TabsLicenciasComponent {

}
