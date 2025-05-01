import { TipoDesignacion } from "../designacion/tipoDesignacion";
import { Division } from "../division/division";
import { Horario } from "../horario/horario";

export interface Cargo {
    id: number;
    nombre: string;
    cargaHoraria: number;
    fechaInicio: Date;
    fechaFin?: Date;
    tipoDesignacion?: TipoDesignacion;
    division?: Division;
    horarios: Horario[];
}