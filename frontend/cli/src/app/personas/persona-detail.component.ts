import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule, ActivatedRoute } from '@angular/router';
import { NgbTypeaheadModule } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-persona-detail',
  imports: [FormsModule, CommonModule, RouterModule, NgbTypeaheadModule],
  templateUrl: `./persona-detail.component.html`,
  styleUrl: `./persona-detail.component.css`,
})
export class PersonaDetailComponent {

}
