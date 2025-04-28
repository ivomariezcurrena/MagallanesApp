import { Turno } from "./turno";

export interface Division {
    id: number;
    anio: number;
    numDivision: number;
    orientacion: string;
    turno: Turno;
}