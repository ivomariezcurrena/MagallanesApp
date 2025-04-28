import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule, ActivatedRoute } from '@angular/router';
import { NgbTypeaheadModule } from '@ng-bootstrap/ng-bootstrap';
import { Location } from '@angular/common';
import { Division } from './division';
import { DivisionService } from './division.service';

@Component({
  selector: 'app-division-detail',
  imports: [FormsModule, CommonModule, RouterModule, NgbTypeaheadModule],
  templateUrl: `./division-detail.component.html`,
  styleUrl: `./division-detail.component.css`,
})
export class DivisionDetailComponent {
  division!: Division;
  searching = false;
  searchFailed = false;
  constructor(
    private divisionServise: DivisionService,
    private route: ActivatedRoute,
    private location: Location,
  ) { }
  get(): void {
    const id = Number(this.route.snapshot.paramMap.get('id')!);
    if (id > 0) {
      this.divisionServise.get(id).subscribe(dataPackage => {
        this.division = <Division>dataPackage.data;
        console.log(this.division);  // Verifica los datos cargados
      });
    } else {
      this.division = <Division>{};  // División vacía para creación
    }
  }

  get isNew(): boolean {
    return this.division?.id === 0;
  }
  save(): void {
    this.divisionServise.save(this.division).subscribe(dataPackage => { this.division = <Division>dataPackage.data; this.goBack(); });
  }
  goBack(): void {
    this.location.back()
  }
  ngOnInit() {
    this.get();
  }
}
