import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { DataPackage } from '../data-package';
import { Observable } from 'rxjs';
import { Division } from './division';

@Injectable({
  providedIn: 'root'
})
export class DivisionService {
  private divisionUrl = 'rest/divisiones';
  constructor(private http: HttpClient) { }

  all() {
    return this.http.get<DataPackage>(this.divisionUrl);
  }
  get(id: number) {
    return this.http.get<DataPackage>(`${this.divisionUrl}/${id}`);
  }
  save(division: Division): Observable<DataPackage> {
    return division.id ? this.http.put<DataPackage>(this.divisionUrl, division) : this.http.post<DataPackage>(this.divisionUrl, division);
  }
  remove(id: number): Observable<DataPackage> {
    return this.http.delete<DataPackage>(`${this.divisionUrl}/${id}`);
  }
  byPage(page: number, size: number): Observable<DataPackage> {
    return this.http.get<DataPackage>(`${this.divisionUrl}/page?page=${page - 1}&size=${size}`);
  }
  searchOrientacion(searchTerm: string, page: number, size: number): Observable<DataPackage> {
    return this.http.get<DataPackage>(`${this.divisionUrl}/search`, {
      params: {
        term: searchTerm,
        page: page.toString(),
        size: size.toString()
      }
    });
  }
  getOrientaciones(): Observable<DataPackage> {
    return this.http.get<DataPackage>(`${this.divisionUrl}/orientaciones`);
  }
  search(searchTerm: string): Observable<DataPackage> {
    return this.http.get<DataPackage>(`${this.divisionUrl}/search/${searchTerm}`);
  }
}
