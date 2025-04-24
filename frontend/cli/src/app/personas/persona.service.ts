import { Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { DataPackage } from '../data-package';
import { Persona } from './persona';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PersonaService {
  private peronasUrl = 'rest/personas';
  constructor(private http: HttpClient) { }

  all() {
    return this.http.get<DataPackage>(this.peronasUrl);
  }

  get(dni: number) {
    return this.http.get<DataPackage>(`${this.peronasUrl}/${dni}`);
  }

  save(persona: Persona): Observable<DataPackage> {
    return persona.dni ? this.http.put<DataPackage>(this.peronasUrl, persona) : this.http.post<DataPackage>(this.peronasUrl, persona);
  }

  remove(dni: number): Observable<DataPackage> {
    return this.http.delete<DataPackage>(`${this.peronasUrl}/${dni}`);
  }

  byPage(page: number, size: number): Observable<DataPackage> {
    return this.http.get<DataPackage>(`${this.peronasUrl}/page?page=${page - 1}&size=${size}`);
  }

  search(searchTerm: string): Observable<DataPackage> {
    return this.http.get<DataPackage>(`${this.peronasUrl}/search/${searchTerm}`);
  }
}
