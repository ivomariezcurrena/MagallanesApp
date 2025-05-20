import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { DataPackage } from '../data-package';
import { Observable } from 'rxjs';
import { ArticuloLicencia } from './aticuloLicencia';

@Injectable({
  providedIn: 'root'
})
export class ArticuloLicenciaService {
  private articuloLicenciaUrl = 'rest/articulolicencias';

  constructor(private http: HttpClient) { }

  all(): Observable<DataPackage> {
    return this.http.get<DataPackage>(this.articuloLicenciaUrl);
  }

  get(id: number): Observable<DataPackage> {
    return this.http.get<DataPackage>(`${this.articuloLicenciaUrl}/${id}`);
  }

  save(articulo: ArticuloLicencia): Observable<DataPackage> {
    return articulo.id
      ? this.http.put<DataPackage>(this.articuloLicenciaUrl, articulo)
      : this.http.post<DataPackage>(this.articuloLicenciaUrl, articulo);
  }

  remove(id: number): Observable<DataPackage> {
    return this.http.delete<DataPackage>(`${this.articuloLicenciaUrl}/${id}`);
  }

  byPage(page: number, size: number): Observable<DataPackage> {
    return this.http.get<DataPackage>(
      `${this.articuloLicenciaUrl}/page?page=${page - 1}&size=${size}`
    );
  }

  search(searchTerm: string): Observable<DataPackage> {
    return this.http.get<DataPackage>(`${this.articuloLicenciaUrl}/search/${searchTerm}`);
  }

  searchTerm(searchTerm: string, page: number, size: number): Observable<DataPackage> {
    return this.http.get<DataPackage>(`${this.articuloLicenciaUrl}/search`, {
      params: {
        term: searchTerm,
        page: page.toString(),
        size: size.toString()
      }
    });
  }
}
