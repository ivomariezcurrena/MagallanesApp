import pdfMake from "pdfmake/build/pdfmake";
import pdfFonts from "pdfmake/build/vfs_fonts";
import { ReportePersona } from "./ReportePersona";

(pdfMake as any).vfs = pdfFonts.vfs;

const generatePDF = (
  reporte: ReportePersona[],
    fecha: string,
    totalLicenciasValidas: number,
    totalLicenciasInvalidas: number
) => {
  // Ajusta los nombres de las columnas y los campos según tu modelo
  const tableBody = [
    [
      { text: "Nombre", style: "tableHeader" },
      { text: "Apellido", style: "tableHeader" },
      { text: "Licencias", style: "tableHeader" },
      { text: "Total días", style: "tableHeader" },
      { text: "% Presencia", style: "tableHeader" },
    ],
    ...reporte.map((persona) => [
      persona.nombre,
      persona.apellido,
      persona.cantidadLicencias,
      persona.totalDias,
      persona.porcentajePresencia + '%',
    ]),
  ];

  const content: any[] = [];

  content.push({
    text: `Reporte de Concepto - Fecha: ${fecha}`,
    style: "header",
    margin: [0, 0, 0, 10],
  });
    
  content.push({
    text: `Escuela secundaria numero 775`,
    style: "subtitulo",
    margin: [0, 0, 0, 10],
  });
  content.push({
    text: `Direccion: Leandro N. Alem 2726`,
    style: "direccion",
    margin: [0, 0, 0, 10],
  });
  content.push({
    text: `Telefono: 0280 447-3231`,
    style: "telefono",
    margin: [0, 0, 0, 10],
  });

  content.push({
    table: {
      headerRows: 1,
      widths: ["*", "*", "auto", "auto", "auto"],
      body: tableBody,
    },
    layout: "lightHorizontalLines",
    margin: [0, 10, 0, 10],
  });
    
    content.push({
        text: `Total Licencias: ${totalLicenciasValidas +totalLicenciasInvalidas}            Total de licencias validas: ${totalLicenciasValidas}            Total de licencias invalidas: ${totalLicenciasInvalidas}`,
        style: "direccion",
        margin: [0, 10, 0, 10],
    });


  const styles = {
    header: {
      fontSize: 16,
      bold: true,
      },
      subtitulo: {
        fontSize: 14,
      },
      direccion: {
        fontSize: 12,
      },
      telefono: {
        fontSize: 12,
      },
      tableHeader: {
        bold: true,
        fontSize: 12,
        color: "black",
      },
  };

  const docDefinition: any = {
    content,
    styles,
    pageOrientation: 'landscape'
  };

  pdfMake.createPdf(docDefinition).open();
};

export default generatePDF;