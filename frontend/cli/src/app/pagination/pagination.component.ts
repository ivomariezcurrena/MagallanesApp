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
    <li class="page-item disabled" *ngIf="showLeftDots">
      <span class="page-link">...</span>
    </li>
    <li *ngFor="let t of pages" class="page-item" [class.active]="t === number">
      <a class="page-link" (click)="onPageChange(t + 1)">{{ t + 1 }}</a>
    </li>
    <li class="page-item disabled" *ngIf="showRightDots">
      <span class="page-link">...</span>
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
    background-color: var(--violeta);
    color: #dcdcdc;
    margin: 0 2px;
    transition: all 0.2s ease;
    border-radius: 0.375rem;
    opacity: 1;
    box-shadow: 0 2px 6px 0 rgba(0,0,0,0.04);
  }

  .custom-pagination .page-link:hover {
    background-color: var(--violeta);
    color: #ffffff;
    transform: scale(1.05);
    filter: brightness(1.1);
  }

  .custom-pagination .page-item.active .page-link {
    background-color: var(--violeta);
    border-color: var(--violeta);
    color: #fff;
    font-weight: bold;
    box-shadow: 0 0 0 2px #fff3, 0 2px 8px 0 rgba(0,0,0,0.08);
  }

  .custom-pagination .page-item.disabled .page-link {
    background-color: #e0d6f7;
    color: #a9a9a9;
    border-color: #d1c4e9;
    pointer-events: none;
    cursor: not-allowed;
    opacity: 0.6;
    filter: grayscale(0.4);
    box-shadow: none;
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
  showLeftDots = false;
  showRightDots = false;

  constructor() { }

  ngOnChanges(changes: SimpleChanges) {
    if (changes['totalPages'] || changes['number']) {
      this.updateVisiblePages();
    }
  }

  updateVisiblePages() {
    const maxVisible = 3;
    let start = Math.max(0, this.number - 1);
    let end = start + maxVisible;

    if (end > this.totalPages) {
      end = this.totalPages;
      start = Math.max(0, end - maxVisible);
    }

    this.pages = Array.from({ length: end - start }, (_, i) => start + i);
    this.showLeftDots = start > 0;
    this.showRightDots = end < this.totalPages;
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

