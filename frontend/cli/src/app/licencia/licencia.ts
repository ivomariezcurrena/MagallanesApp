import { Designacion } from "../designacion/designacion";
import { Persona } from "../personas/persona";
import { ArticuloLicencia } from "./aticuloLicencia";
export interface Log {
    id: number;
    estatus: number;
    mensaje: string;
    fecha: string;
}

export type Estado = 'Valido' | 'Invalido';

export interface Licencia {
    id: number;
    pedidoDesde: string;
    pedidoHasta: string;
    domicilio: string;
    certificadoMedico: boolean;
    persona?: Persona;
    designaciones?: Designacion[];
    articulo?: ArticuloLicencia;
    estado?: Estado;
    logs?: Log[];
}