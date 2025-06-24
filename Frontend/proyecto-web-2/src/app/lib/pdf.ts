import pdfMake from 'pdfmake/build/pdfmake';
import pdfFonts from 'pdfmake/build/vfs_fonts';
import { variable64 } from '../../../public/assets/img';

(pdfMake as any).vfs = pdfFonts.pdfMake.vfs;

export class Alquiler {
  numeroAlquiler: number;
  identificacion: string;
  fechaInicio: string;
  fechaFin: string;
  tipoVehiculo: string;
  placa: string;
  color: string;
  valor: number;
}

export const generateAlquilerPDF = (
  alquiler: any[][],
  fechaEmision: string
) => {
  const datos=alquiler[0];
  const alqui: Alquiler = {
    numeroAlquiler: datos[0],
    identificacion: datos[1],
    fechaInicio: datos[2],
    fechaFin: datos[3],
    tipoVehiculo: datos[4],
    placa: datos[5],
    color: datos[6],
    valor: datos[7],
  };
  console.log(alquiler,datos[0]);
  // Validar que alquiler tenga todos los datos necesarios
  if (!alquiler || !alqui.numeroAlquiler) {
    console.error('Datos de alquiler incompletos');
    return;
  }

  const content: any[] = [];
  const numAlqui = alqui.numeroAlquiler;
  const valorAlqu = alqui.valor || 0; // Valor por defecto

  // Encabezado con imagen
  content.push({
    columns: [
      { image: variable64.miVar, width: 60 },
      {
        stack: [
          { text: `Alquiler No. ${numAlqui}`, style: 'header' }, // Comillas corregidas
          { text: `Fecha de emisión: ${fechaEmision}`, style: 'subheader' }, // Comillas corregidas
        ],
        alignment: 'right',
      },
    ],
    margin: [0, 0, 0, 20],
  });

  // Código QR
  content.push({
    qr: `Alquiler: ${alqui.numeroAlquiler}`,
    fit: 90,
    alignment: 'right',
    margin: [0, 0, 0, 20],
  });

  // Cuerpo de la tabla - asegurar que cada celda es un objeto
  const tableBody = [
    [
      { text: 'Campo', style: 'tableHeader' },
      { text: 'Datos', style: 'tableHeader' },
    ],
    [
      { text: 'Identificación', style: 'tableCell' },
      { text: alqui.identificacion || 'N/A', style: 'tableCell' },
    ],
    [
      { text: 'Fecha de Inicio', style: 'tableCell' },
      { text: alqui.fechaInicio || 'N/A', style: 'tableCell' },
    ],
    [
      { text: 'Fecha de Fin', style: 'tableCell' },
      { text: alqui.fechaFin || 'N/A', style: 'tableCell' },
    ],
    [
      { text: 'Tipo de Vehículo', style: 'tableCell' },
      { text: alqui.tipoVehiculo || 'N/A', style: 'tableCell' },
    ],
    [
      { text: 'Placa', style: 'tableCell' },
      { text: alqui.placa || 'N/A', style: 'tableCell' },
    ],
    [
      { text: 'Color', style: 'tableCell' },
      { text: alqui.color || 'N/A', style: 'tableCell' },
    ],
    [
      { text: 'Valor del Alquiler', style: 'tableCell' },
      { text: `$ ${valorAlqu.toLocaleString()}`, style: 'tableCell' },
    ],
    [
      { text: 'Estado', style: 'tableCell' },
      { text: 'Pendiente de entrega', style: 'tableCell' },
    ],
  ];

  content.push({
    table: {
      headerRows: 1,
      widths: ['*', '*'],
      body: tableBody,
    },
    layout: 'lightHorizontalLines',
    margin: [0, 0, 0, 20],
  });

  // Estilos definidos
  const styles = {
    header: {
      fontSize: 14,
      bold: true,
      margin: [0, 0, 0, 5],
    },
    subheader: {
      fontSize: 12,
      margin: [0, 0, 0, 10],
    },
    tableHeader: {
      bold: true,
      fontSize: 12,
      color: 'black',
      fillColor: '#f5f5f5',
    },
    tableCell: {
      fontSize: 11,
      margin: [0, 5, 0, 5],
    },
  };

  // Definición del documento
  const docDefinition = {
    content: content,
    styles: styles,
    defaultStyle: {
      font: 'Roboto',
    },
  };

  // Generar PDF
  pdfMake.createPdf(docDefinition).open();
};
