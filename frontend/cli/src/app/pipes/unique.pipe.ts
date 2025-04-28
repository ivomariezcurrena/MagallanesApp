import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'unique',
    standalone: true
})
export class UniquePipe implements PipeTransform {
    transform<T>(items: T[]): T[] {
        return [...new Set(items)];
    }
}