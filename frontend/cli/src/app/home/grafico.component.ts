import { Component, OnInit } from '@angular/core';
import Chart from 'chart.js/auto';

@Component({
  selector: 'app-grafico',
  imports: [],
  template: `
<canvas id="chart">{{chart}}</canvas>
  `,
  styles: `
`
})
export class GraficoComponent implements OnInit {
  public chart!: Chart;

  ngOnInit() {
    this.chart = new Chart('chart', {
      type: 'doughnut',
      data: {
        labels: [
          'Sin suplente',
          'Con suplente',
          'Normal'
        ],
        datasets: [{
          label: 'Cantidad',
          data: [300, 50, 100],
          backgroundColor: [
            '#dc3545',
            '#198754',
            '#36393b'
          ],
          hoverOffset: 4
        }]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
      }
    });
  }
}
