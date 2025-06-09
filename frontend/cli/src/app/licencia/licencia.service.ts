import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DataPackage } from '../data-package';
import { Licencia } from './licencia';

@Injectable({
  providedIn: 'root'
})
export class LicenciaService {
  private licenciasUrl = 'rest/licencias';

  constructor(private http: HttpClient) { }

  all(): Observable<DataPackage> {
    return this.http.get<DataPackage>(this.licenciasUrl);
  }

  get(id: number): Observable<DataPackage> {
    return this.http.get<DataPackage>(`${this.licenciasUrl}/${id}`);
  }

  save(licencia: Licencia): Observable<DataPackage> {
    return licencia.id
      ? this.http.put<DataPackage>(this.licenciasUrl, licencia)
      : this.http.post<DataPackage>(this.licenciasUrl, licencia);
  }

  remove(id: number): Observable<DataPackage> {
    return this.http.delete<DataPackage>(`${this.licenciasUrl}/${id}`);
  }

  byPage(page: number, size: number): Observable<DataPackage> {
    return this.http.get<DataPackage>(
      `${this.licenciasUrl}/page?page=${page - 1}&size=${size}`
    );
  }


  byPageValidas(page: number, size: number): Observable<DataPackage> {
    return this.http.get<DataPackage>(
      `${this.licenciasUrl}/page/validas?page=${page - 1}&size=${size}`
    );
  }

  search(searchTerm: string): Observable<DataPackage> {
    return this.http.get<DataPackage>(`${this.licenciasUrl}/search/${searchTerm}`);
  }

  searchTerm(searchTerm: string, page: number, size: number): Observable<DataPackage> {
    return this.http.get<DataPackage>(`${this.licenciasUrl}/search`, {
      params: {
        term: searchTerm,
        page: page.toString(),
        size: size.toString()
      }
    });
  }
  byFechaDesde(fecha: string, page: number, size: number): Observable<DataPackage> {
    return this.http.get<DataPackage>(`${this.licenciasUrl}/desde`, {
      params: {
        fecha: fecha + 'T00:00:00',
        page: (page - 1).toString(),
        size: size.toString()
      }
    });
  }

  getPrimerSuplenteDeLicencia(id: number): Observable<DataPackage> {
    return this.http.get<DataPackage>(`${this.licenciasUrl}/${id}/suplente`);
  }

  byAño(anio: string, page: number, size: number): Observable<DataPackage> {
    // Asegura el formato yyyy-MM-ddTHH:mm:ss
    const fecha = `${anio}-01-01T00:00:00`;
    return this.http.get<DataPackage>(`${this.licenciasUrl}/anio`, {
      params: {
        anio: fecha,
        page: (page - 1).toString(),
        size: size.toString()
      }
    });
  }

  
  reporteConcepto(anio: string, page: number, size: number): Observable<DataPackage> {
    const fecha = `${anio}-01-01T00:00:00`;
    return this.http.get<DataPackage>(`${this.licenciasUrl}/reporte-concepto`, {
      params: {
        anio: fecha,
        page: (page - 1).toString(),
        size: size.toString()
      }
    });
  }

  getValidas(anio: string): Observable<DataPackage>{
    return this.http.get<DataPackage>(`${this.licenciasUrl}/validas`, {
      params: { anio }
    });
  }
  getInvalidas(anio: string): Observable<DataPackage>{
    return this.http.get<DataPackage>(`${this.licenciasUrl}/invalidas`, {
      params: { anio }
    });
  }
}


