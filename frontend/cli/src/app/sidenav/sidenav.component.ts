import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, input, Output, output } from '@angular/core';
import { RouterModule } from '@angular/router';
import { MenuItem } from './menuItem';

@Component({
  selector: 'app-sidenav',
  imports: [RouterModule, CommonModule],
  templateUrl: `sidenav.component.html`,
  styleUrl: `sidenav.component.css`,
})
export class SidenavComponent {
  @Input() isLeftSidebarCollapsed = false;
  @Input() items: MenuItem[] = [];
  @Output() changeIsLeftSidebarCollapsed = new EventEmitter<boolean>();

  toggleCollapse(): void {
    this.changeIsLeftSidebarCollapsed.emit(!this.isLeftSidebarCollapsed);
  }

  closeSidenav(): void {
    this.changeIsLeftSidebarCollapsed.emit(true);
  }
}
