import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
@Component({
  selector: 'app-modal',
  imports: [CommonModule],
  template: `
    <div class="modal-header custom-modal-header">
      <h5 class="modal-title" id="modal-title">{{ title }}</h5>
      <button type="button" class="btn-close" aria-label="Cerrar" (click)="modal.dismiss()">
</button>
    </div>

    <div class="modal-body custom-modal-body">
      <p class="mb-2 fw-semibold text-danger">{{ message }}</p>
      <p *ngIf="description" class="text-muted fst-italic">{{ description }}</p>
    </div>

    <div class="modal-footer custom-modal-footer">
      <button type="button" class="btn btn-outline-secondary" (click)="modal.dismiss()">Cancelar</button>
      <button type="button" class="btn btn-success" (click)="modal.close()">Aceptar</button>
    </div>
  `,
  styles: [`
    :host ::ng-deep .modal-content {
      border-radius: 1rem;
      box-shadow: 0 0.75rem 1.5rem rgba(0, 0, 0, 0.2);
      border: none;
    }

    .custom-modal-header {
      background-color: #f8f9fa;
      border-bottom: 1px solid #dee2e6;
      border-top-left-radius: 1rem;
      border-top-right-radius: 1rem;
    }

    .close-button {
  background: transparent;
  border: none;
  font-size: 1.8rem; /* más grande */
  font-weight: bold;
  line-height: 1;
  color: #333;
  cursor: pointer;
}

    .btn-close:hover {
      opacity: 1;
    }

    .custom-modal-body {
      padding: 1rem 1.5rem;
      font-size: 1rem;
    }

    .custom-modal-footer {
      padding: 0.75rem 1.5rem;
      border-top: 1px solid #dee2e6;
      border-bottom-left-radius: 1rem;
      border-bottom-right-radius: 1rem;
    }

    .modal-title {
      font-weight: 600;
      font-size: 1.25rem;
    }
  `]
})
export class ModalComponent {
  constructor(public modal: NgbActiveModal) { }
  title = "";
  message = "";
  description = "";
}