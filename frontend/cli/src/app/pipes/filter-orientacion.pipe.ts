import { Pipe, PipeTransform } from '@angular/core';
import { Division } from '../division/division';

@Pipe({
    name: 'filterOrientacion',
    pure: true
})
export class FilterOrientacionPipe implements PipeTransform {
    transform(divisiones: Division[], orientacion: string): Division[] {
        return orientacion
            ? divisiones.filter(d => d.orientacion === orientacion)
            : divisiones;
    }
}
