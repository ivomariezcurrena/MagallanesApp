import { Designacion } from "../designacion/designacion";
import { Persona } from "../personas/persona";
import { ArticuloLicencia } from "./aticuloLicencia";

export interface Licencia {
    pedidoDesde: string;
    pedidoHasta: string;
    domicilio: string;
    certificadoMedico: boolean;
    persona?: Persona;
    designaciones?: Designacion[];
    articuloLicencia?: ArticuloLicencia;
}