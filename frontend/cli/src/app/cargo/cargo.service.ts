import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DataPackage } from '../data-package';
import { Cargo } from './cargo';

@Injectable({
  providedIn: 'root'
})
export class CargoService {
  private cargosUrl = 'rest/cargos';
  constructor(private http: HttpClient) { }
  all() {
    return this.http.get<DataPackage>(this.cargosUrl);
  }
  get(id: number) {
    return this.http.get<DataPackage>(`${this.cargosUrl}/${id}`);
  }
  save(cargo: Cargo): Observable<DataPackage> {
    return cargo.id ? this.http.put<DataPackage>(this.cargosUrl, cargo) : this.http.post<DataPackage>(this.cargosUrl, cargo);
  }
  remove(id: number): Observable<DataPackage> {
    return this.http.delete<DataPackage>(`${this.cargosUrl}/${id}`);
  }
  byPage(page: number, size: number): Observable<DataPackage> {
    return this.http.get<DataPackage>(`${this.cargosUrl}/page?page=${page - 1}&size=${size}`);
  }
  searchTerm(searchTerm: string, page: number, size: number): Observable<DataPackage> {
    return this.http.get<DataPackage>(`${this.cargosUrl}/search`, {
      params: {
        term: searchTerm,
        page: page.toString(),
        size: size.toString()
      }
    });
  }
  search(searchTerm: string): Observable<DataPackage> {
    return this.http.get<DataPackage>(`${this.cargosUrl}/search/${searchTerm}`);
  }

  getByAnioYNumero(anio: number, numero: number): Observable<DataPackage> {
    return this.http.get<DataPackage>(`${this.cargosUrl}/buscar-division?anio=${anio}&numero=${numero}`);
  }
}
