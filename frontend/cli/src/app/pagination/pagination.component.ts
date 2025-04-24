import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output, SimpleChange, SimpleChanges } from '@angular/core';

@Component({
  selector: 'app-pagination',
  imports: [CommonModule],
  template: `
<nav aria-label="Page navigation" class="d-flex justify-content-center">
  <ul class="pagination custom-pagination ">
    <li class="page-item" [class.disabled]="number === 0">
      <a class="page-link" (click)="onPageChange(-2)" title="Primera página">&laquo;</a>
    </li>
    <li class="page-item" [class.disabled]="number === 0">
      <a class="page-link" (click)="onPageChange(-1)" title="Anterior">&lsaquo;</a>
    </li>
    <li *ngFor="let t of pages" class="page-item" [class.active]="t === number">
      <a class="page-link" (click)="onPageChange(t + 1)">{{ t + 1 }}</a>
    </li>
    <li class="page-item" [class.disabled]="number === totalPages - 1">
      <a class="page-link" (click)="onPageChange(-3)" title="Siguiente">&rsaquo;</a>
    </li>
    <li class="page-item" [class.disabled]="number === totalPages - 1">
      <a class="page-link" (click)="onPageChange(-4)" title="Última página">&raquo;</a>
    </li>
  </ul>
</nav>

  `,
  styles: `
  .custom-pagination .page-link {
  background-color:rgb(33,37,41,255);
  color: #dcdcdc;
  border: 1px solid #3c3c4a;
  margin: 0 2px;
  transition: all 0.2s ease;
  border-radius: 0.375rem;
}

.custom-pagination .page-link:hover {
  background-color: #343a40;
  color: #ffffff;
  transform: scale(1.05);
}

.custom-pagination .page-item.active .page-link {
  background-color: #343a40; /* Bootstrap primary */
  border-color: #343a40;
  color: #fff;
}

.custom-pagination .page-item.disabled .page-link {
  background-color: #343a40;;
  
  color: #666;
  pointer-events: none;
  cursor: not-allowed;
  opacity: 0.6;
}

.custom-pagination {
  user-select: none;
}
`
})
export class PaginationComponent {
  @Input() totalPages: number = 0;
  @Input() last: boolean = false;
  @Input() currentPage: number = 1;
  @Input() number: number = 1;
  @Output() pageChangesRequested = new EventEmitter<number>();
  pages: number[] = [];

  constructor() { }

  ngOnChanges(changes: SimpleChanges) {
    if (changes['totalPages']) {
      this.pages = Array.from(Array(this.totalPages).keys());
    }
  }

  onPageChange(pageId: number): void {
    if (!this.currentPage) {
      this.currentPage = 1;
    }
    let page = this.currentPage;

    switch (pageId) {
      case -2: // Primera 
        page = 1;
        break;

      case -1: // Anterior
        page = this.currentPage > 1 ? this.currentPage - 1 : 1;
        break;

      case -3: // Siguiente
        page = this.currentPage < this.totalPages ? this.currentPage + 1 : this.currentPage;
        break;

      case -4: // Ultima
        page = this.totalPages;
        break;

      default:
        if (pageId >= 1 && pageId <= this.totalPages) {
          page = pageId;
        } else {
          return;
        }
    }
    if (page !== this.currentPage) {
      this.currentPage = page;
      this.pageChangesRequested.emit(page);
    }
  }
}

