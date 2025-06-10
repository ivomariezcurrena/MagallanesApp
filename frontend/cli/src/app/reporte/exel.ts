import { Injectable } from '@angular/core';
import { Workbook } from 'exceljs';
import * as fs from 'file-saver';
import { ReportePersona } from './ReportePersona';

@Injectable({ providedIn: 'root' })
export class ExcelService {
  async exportarReporte(reporte: ReportePersona[], anio: string): Promise<void> {
    const workbook = new Workbook();
    workbook.creator = 'DigiDev';

    // Crear hoja
    const sheet = workbook.addWorksheet(`Reporte ${anio}`);

    // Definir cabecera
    const header = [
      'ID',
      'Nombre',
      'Apellido',
      'Cantidad de Licencias',
      'Total de Días de Licencia',
      'Porcentaje de Presencia'
    ];
    sheet.addRow(header);

    // Agregar datos
    reporte.forEach(persona => {
      sheet.addRow([
        persona.id,
        persona.nombre,
        persona.apellido,
        persona.cantidadLicencias,
        persona.totalDias,
        persona.porcentajePresencia + '%'
      ]);
    });

    // Estilos básicos para cabecera
    sheet.getRow(1).font = { bold: true };
    sheet.columns.forEach(col => col.width = 20);

    // Descargar archivo
    const buffer = await workbook.xlsx.writeBuffer();
    const blob = new Blob([buffer]);
    fs.saveAs(blob, `ReporteLicencias_${anio}.xlsx`);
  }
}