import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { DataPackage } from '../data-package';
import { Designacion } from './designacion';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DesignacionService {
  private designacionesUrl = 'rest/designaciones'
  constructor(private http: HttpClient) { }

  all() {
    return this.http.get<DataPackage>(this.designacionesUrl);
  }

  get(id: number) {
    return this.http.get<DataPackage>(`${this.designacionesUrl}/${id}`);
  }
  save(designacion: Designacion): Observable<DataPackage> {
    return designacion.id ? this.http.put<DataPackage>(this.designacionesUrl, designacion) : this.http.post<DataPackage>(this.designacionesUrl, designacion);
  }

  remove(id: number): Observable<DataPackage> {
    return this.http.delete<DataPackage>(`${this.designacionesUrl}/${id}`);
  }

  byPage(page: number, size: number): Observable<DataPackage> {
    return this.http.get<DataPackage>(`${this.designacionesUrl}/page?page=${page - 1}&size=${size}`);
  }


  searchTerm(searchTerm: string, page: number, size: number): Observable<DataPackage> {
    return this.http.get<DataPackage>(`${this.designacionesUrl}/search`, {
      params: {
        term: searchTerm,
        page: page.toString(),
        size: size.toString()
      }
    });
  }
}
