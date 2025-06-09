import { Component, HostListener, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SidenavComponent } from './sidenav/sidenav.component';
import { MainComponent } from './main/main.component';
import { MenuItem } from './sidenav/menuItem';


@Component({
  selector: 'app-root',
  imports: [CommonModule, MainComponent],
  templateUrl: `app.component.html`,
  styles: [`
  body {
  background-color: var(--penguin-bg);
  color: var(--penguin-grey);
  font-family: 'Segoe UI', Roboto, sans-serif;
}


    `],
})
export class AppComponent {




}

