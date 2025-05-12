import { Component, HostListener, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SidenavComponent } from './sidenav/sidenav.component';
import { MainComponent } from './main/main.component';
import { MenuItem } from './sidenav/menuItem';


@Component({
  selector: 'app-root',
  imports: [SidenavComponent, CommonModule, MainComponent],
  templateUrl: `app.component.html`,
  styles: [`
  body {
  background-color: var(--penguin-bg);
  color: var(--penguin-grey);
  font-family: 'Segoe UI', Roboto, sans-serif;
}

.container-main {
  padding: 1.5rem;
}


    `],
})
export class AppComponent implements OnInit {
  isLeftSidebarCollapsed = signal<boolean>(false);
  screenWidth = signal<number>(window.innerWidth);

  menuItems: MenuItem[] = [
    { route: '', icon: 'fal fa-home', label: 'Inicio' },
    { route: 'personas', icon: 'fal fa-users', label: 'Personas' },
    { route: 'divisiones', icon: 'fal fa-layer-group', label: 'Divisiones' },
    { route: 'cargos', icon: 'fal fa-briefcase', label: 'Cargos' },
    { route: 'designaciones', icon: 'fal fa-user-tie', label: 'Designaciones' },
    { route: 'licencias', icon: 'fal fa-file-alt', label: 'Licencias' },
  ];

  @HostListener('window:resize')
  onResize() {
    this.screenWidth.set(window.innerWidth);
    if (this.screenWidth() < 768) {
      this.isLeftSidebarCollapsed.set(true);
    }
  }

  ngOnInit(): void {
    this.isLeftSidebarCollapsed.set(this.screenWidth() < 768);
  }

  changeIsLeftSidebarCollapsed(isLeftSidebarCollapsed: boolean): void {
    this.isLeftSidebarCollapsed.set(isLeftSidebarCollapsed);
  }
}

