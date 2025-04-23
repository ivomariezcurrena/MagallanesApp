import { Injectable, signal } from '@angular/core';
import { Persona } from './persona';
import { PERSONAS_MOCK } from './mock';

@Injectable({
  providedIn: 'root'
})
export class PersonaService {
  private peronasUrl = 'rest/personas';
  personas = signal<Persona[]>([...PERSONAS_MOCK]);

  obtenerTodas(): Persona[] {
    return this.personas();
  }
}
