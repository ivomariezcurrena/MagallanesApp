import pdfMake from "pdfmake/build/pdfmake";
import pdfFonts from "pdfmake/build/vfs_fonts";
import { Cargo } from "../cargo/cargo";
import { Division } from "../division/division";

(pdfMake as any).vfs = pdfFonts.vfs;

const dias = ["Lunes", "Martes", "Miércoles", "Jueves", "Viernes"];

const generatePDF = (
  cargos: Cargo[],
  horas: string[],
  division: Division
) => {
  // Construir la tabla de horarios
  const tableBody = [
    [
      { text: "Horas", style: "tableHeader" },
      ...dias.map(d => ({ text: d, style: "tableHeader" })),
    ],
    ...horas.map(hora => [
      { text: hora, style: "tableHour" },
      ...dias.map(dia => {
        const cargosEnCelda = cargos.filter(cargo =>
          cargo.horarios.some(h => h.dia === dia && h.hora === parseInt(hora.split(':')[0], 10))
        );
        return {
          text: cargosEnCelda.map(c => c.nombre).join(", ") || "",
          style: "tableCell"
        };
      })
    ])
  ];

  const content: any[] = [];

  content.push({
    text: `Horarios de ${division.anio}° ${division.numDivision}  - Turno: ${division.turno}`,
    style: "header",
    margin: [0, 0, 0, 18],
  });

  content.push({
    table: {
      headerRows: 1,
      widths: [60, "*", "*", "*", "*", "*"],
      body: tableBody,
    },
    margin: [0, 10, 0, 10],
  });

  const styles = {
    header: {
      fontSize: 22,
      bold: true,
      alignment: 'center'
    },
    tableHeader: {
      bold: true,
      fontSize: 15,
      alignment: "center",
      margin: [0, 8, 0, 8]
    },
    tableHour: {
      fontSize: 14,
      bold: true,
      alignment: "center",
      margin: [0, 6, 0, 6]
    },
    tableCell: {
      fontSize: 14,
      alignment: "center",
      margin: [0, 6, 0, 6]
    }
  };

  const docDefinition: any = {
    content,
    styles,
    pageOrientation: 'landscape',
    defaultStyle: {
      fontSize: 13,
    }
  };

  pdfMake.createPdf(docDefinition).open();
};

export default generatePDF;