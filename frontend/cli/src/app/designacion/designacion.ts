import { Cargo } from "../cargo/cargo";
import { Persona } from "../personas/persona";

export interface Designacion {
    id: number;
    situacionRevista: string;
    fechaInicio: string;
    fechaFin?: string;
    persona?: Persona;
    cargo?: Cargo;
}