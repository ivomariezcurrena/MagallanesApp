import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-modal',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="modal-header modal-header-clean">
      <div class="header-left">
        <i class="bi bi-exclamation-circle-fill icon-alert"></i>
        <h5 class="modal-title">{{ title }}</h5>
      </div>
      <button type="button" class="btn-close btn-close-white" aria-label="Cerrar" (click)="modal.dismiss()"></button>
    </div>

    <div class="modal-body">
      <p class="main-message">{{ message }}</p>
      <p *ngIf="description" class="description">{{ description }}</p>
    </div>

    <div class="modal-footer">
      <button type="button" class="btn btn-outline-dark" (click)="modal.dismiss()">Cancelar</button>
      <button type="button" class="btn btn-danger" (click)="modal.close()">Aceptar</button>
    </div>
  `,
  styles: [`
    :host ::ng-deep .modal-content {
      border-radius: 14px;
      box-shadow: 0 20px 50px rgba(0, 0, 0, 0.2);
      border: none;
      overflow: hidden;
      font-family: 'Segoe UI', sans-serif;
    }

    .modal-header-clean {
      background: var(--violeta);
      padding: 1rem 1.5rem;
      display: flex;
      justify-content: space-between;
      align-items: center;
      color: white;
    }

    .header-left {
      display: flex;
      align-items: center;
      gap: 0.5rem;
    }

    .icon-alert {
      font-size: 1.3rem;
    }

    .modal-title {
      font-weight: 600;
      font-size: 1.25rem;
      margin: 0;
    }

    .modal-body {
      padding: 1.5rem;
      background-color: #fff;
    }

    .main-message {
      color: #d32f2f;
      font-weight: 600;
      font-size: 1.05rem;
      margin-bottom: 0.5rem;
    }

    .description {
      color: #666;
      font-style: italic;
      font-size: 0.95rem;
    }

    .modal-footer {
      padding: 1rem 1.5rem;
      background-color: #f5f5f5;
      display: flex;
      justify-content: flex-end;
      gap: 0.5rem;
    }

    .btn-outline-dark {
      border: 1px solid #888;
      color: #333;
      background: white;
      font-weight: 500;
    }

    .btn-outline-dark:hover {
      background: #eee;
    }

    .btn-danger {
      background-color:var(--violeta);
      border-color:var(--violeta);
      color: white;
      font-weight: 500;
    }

    .btn-danger:hover {
      background-color:rgb(169, 149, 235);
      border-color: rgb(169, 149, 235);
    }
  `]
})
export class ModalComponent {
  constructor(public modal: NgbActiveModal) {}
  title = '';
  message = '';
  description = '';
}
