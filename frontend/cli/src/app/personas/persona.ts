import { Designacion } from "../designacion/designacion";

export interface Persona {
    dni: number;
    cuil: string;
    nombre: string;
    apellido: string;
    titulo: string;
    sexo: string;
    domicilio: string;
    telefono: string;
    designaciones?: Designacion[];
}