import { Cargo } from "../cargo/cargo";
import { Persona } from "../personas/persona";

export interface Designacion {
    situacionRevista: string;
    fechaInicio: string; // ISO Date string
    fechaFin: string;
    persona?: Persona;
    cargo?: Cargo;
}