import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { TabsLicenciasComponent } from "./tabs-licencias.component";
@Component({
  selector: 'app-licencias-layout',
  imports: [TabsLicenciasComponent, RouterModule],
  template: `
<div class="container mt-4">
    <app-tabs-licencias></app-tabs-licencias>
    <router-outlet></router-outlet>
</div>
  `,
  styles: ``
})

export class LicenciasLayoutComponent {

}
