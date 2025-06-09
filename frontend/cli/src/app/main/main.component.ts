import { CommonModule } from '@angular/common';
import { Component, computed, input } from '@angular/core';
import { RouterOutlet } from '@angular/router';

import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-main',
  imports: [RouterOutlet, CommonModule,RouterModule],
  templateUrl: `main.component.html`,
  styleUrl: `main.component.css`,
})
export class MainComponent {
 

}
