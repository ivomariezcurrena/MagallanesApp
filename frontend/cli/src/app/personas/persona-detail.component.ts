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
  errorMessage: string | null = null;
  successMessage: string | null = null;
  loading = false;

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
    this.errorMessage = null;
    this.successMessage = null;
    this.loading = true;
    this.personaService.save(this.persona).subscribe({
      next: dataPackage => {
        this.persona = <Persona>dataPackage.data;
        this.successMessage = '¡Persona guardada exitosamente!';
        this.loading = false;
      },
      error: err => {
        this.errorMessage = err.error?.data || err.error || 'Error al guardar la persona';
        this.loading = false;
      }
    })
  }
  goBack(): void {
    this.location.back()
  }
  ngOnInit() {
    this.get();
  }

}
