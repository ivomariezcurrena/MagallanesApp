import pdfMake from "pdfmake/build/pdfmake";
import pdfFonts from "pdfmake/build/vfs_fonts";
import { Division } from "../division/division";
import { Horario } from "./horario";

(pdfMake as any).vfs = pdfFonts.vfs;

const dias = ["Lunes", "Martes", "Miércoles", "Jueves", "Viernes"];

const generatePDF = (
  horarios: Horario[],
  horas: string[],
  division: Division
) => {
  const tableBody = [
    [
      { text: "Horas", style: "tableHeader" },
      ...dias.map(d => ({ text: d, style: "tableHeader" })),
    ],
    ...horas.map(hora => {
      const horaNum = parseInt(hora.split(':')[0], 10);

      return [
        { text: hora, style: "tableHour" },
        ...dias.map(dia => {
          const coincidencias = horarios.filter(h => h.dia === dia && h.hora === horaNum);

          const contenido = coincidencias.map(h =>
            `${h.nombre}`
          ).join("\n");

          return {
            text: contenido || '',
            style: "tableCell"
          };
        })
      ];
    })
  ];

  const content: any[] = [];

  content.push({
    text: `Horarios de ${division.anio}° ${division.numDivision} - Turno: ${division.turno}`,
    style: "header",
    margin: [0, 0, 0, 18],
  });

  content.push({
    table: {
      headerRows: 1,
      widths: [60, "*", "*", "*", "*", "*"],
      body: tableBody,
    },
    layout: {
      hLineWidth: () => 0.5,
      vLineWidth: () => 0.5,
      hLineColor: () => "#aaa",
      vLineColor: () => "#aaa",
    },
    margin: [0, 10, 0, 10],
  });

  const styles = {
    header: {
      fontSize: 20,
      bold: true,
      alignment: 'center'
    },
    tableHeader: {
      bold: true,
      fontSize: 14,
      alignment: "center",
      fillColor: "#f2f2f2",
      margin: [0, 6, 0, 6]
    },
    tableHour: {
      fontSize: 13,
      bold: true,
      alignment: "center",
      margin: [0, 6, 0, 6]
    },
    tableCell: {
      fontSize: 12,
      alignment: "center",
      margin: [0, 4, 0, 4]
    }
  };

  const docDefinition: any = {
    content,
    styles,
    pageOrientation: 'landscape',
    defaultStyle: {
      fontSize: 11,
    }
  };

  pdfMake.createPdf(docDefinition).open();
};

export default generatePDF;
