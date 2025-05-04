import { Cargo } from "../cargo/cargo";
import { Persona } from "../personas/persona";

export interface Designacion {
    id: number;
    situacionRevista: string;
    fechaInicio: Date;
    fechaFin?: Date;
    persona?: Persona;
    cargo?: Cargo;
}