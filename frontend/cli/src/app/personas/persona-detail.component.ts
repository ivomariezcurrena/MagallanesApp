import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule, ActivatedRoute } from '@angular/router';
import { NgbTypeaheadModule } from '@ng-bootstrap/ng-bootstrap';
import { Persona } from './persona';
import { PersonaService } from './persona.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-persona-detail',
  imports: [FormsModule, CommonModule, RouterModule, NgbTypeaheadModule],
  templateUrl: `./persona-detail.component.html`,
  styleUrl: `./persona-detail.component.css`,
})
export class PersonaDetailComponent {
  persona!: Persona;
  searching = false;
  searchFailed = false;
  errorMessage: string | null = null; // <-- Agrega esto

  constructor(
    private route: ActivatedRoute,
    private personaService: PersonaService,
    private location: Location,
  ) { }
  get(): void {
    const dni = Number(this.route.snapshot.paramMap.get('dni')!);
    if (isNaN(dni)) {
      this.persona = <Persona>{};
    }
    else {
      this.personaService.get(dni).subscribe(dataPackage => this.persona = <Persona>dataPackage.data);
    }
  }

  get isNew(): boolean {
    return this.persona?.dni === 0 || this.persona?.dni === undefined;
  }
  save(): void {
    this.personaService.save(this.persona).subscribe({
      next: dataPackage => {
        this.persona = <Persona>dataPackage.data;
        this.errorMessage = null; // Limpia el error si todo sale bien
        this.goBack();
      },
      error: err => {
        // Intenta obtener el mensaje del backend
        this.errorMessage = err.error?.message || 'Error al guardar la persona';
      }
    });
  }
  goBack(): void {
    this.location.back()
  }
  ngOnInit() {
    this.get();
  }

}
