import { TipoDesignacion } from "../designacion/tipoDesignacion";
import { Division } from "../division/division";
import { Horario } from "../horario/horario";

export interface Cargo {
    nombre: string;
    cargaHoraria: number;
    fechaInicio: string;
    fechaFin: string;
    tipoDesignacion?: TipoDesignacion;
    division?: Division;
    horario?: Horario[];
}