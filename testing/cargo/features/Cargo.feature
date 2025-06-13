            # language: es
            Característica: Gestión de cargos institucionales
            Módulo responsable de administrar los cargos y espacios curriculares en una escuela.

            Esquema del escenario: ingresar nuevo cargo institucional
            Dado el cargo institucional cuyo "<nombre>" que da título al mismo
            Y que es del tipo de designación "<tipoDesignación>"
            Y que tiene una carga horaria de <cargaHoraria> horas, con vigencia desde "<fechaDesde>" hasta "<fechaHasta>"
            Y que si el tipo es "ESPACIO CURRICULAR", opcionalmente se asigna a la división "<año>" "<número>" "<turno>"
            Cuando se presiona el botón de guardar
            Entonces se espera el siguiente <status> con la "<respuesta>"

            Ejemplos:
            | nombre                     | tipoDesignación    | cargaHoraria | fechaDesde | fechaHasta | año | número | turno  | status | respuesta                                                                                        |
            | Vicedirector/a             | CARGO              | 36           | 2020-03-01 |            |     |        |        | 200    | Cargo de Vicedirector/a ingresado correctamente                                                  |
            | Preceptor/a                | CARGO              | 36           | 2020-03-01 |            |     |        |        | 200    | Cargo de Preceptor/a ingresado correctamente                                                     |
            | Historia                   | ESPACIO_CURRICULAR | 4            | 2020-03-01 |            | 5   | 2      | Mañana | 200    | Espacio Curricular Historia para la división 5º 2º Turno Mañana ingresado correctamente          |
            | Geografía                  | ESPACIO_CURRICULAR | 3            | 2020-03-01 |            | 3   | 1      | Tarde  | 200    | Espacio Curricular Geografía para la división 3º 1º Turno Tarde ingresado correctamente          |
            | Física                     | ESPACIO_CURRICULAR | 5            | 2020-03-01 |            | 2   | 3      | Mañana | 200    | Espacio Curricular Física para la división 2º 3º Turno Mañana ingresado correctamente            |
            | Matemáticas                | ESPACIO_CURRICULAR | 4            | 2020-03-01 |            | 1   | 1      | Tarde  | 200    | Espacio Curricular Matemáticas para la división 1º 1º Turno Tarde ingresado correctamente        |
            | Tecnología                 | ESPACIO_CURRICULAR | 3            | 2020-03-01 |            | 4   | 3      | Mañana | 200    | Espacio Curricular Tecnología para la división 4º 3º Turno Mañana ingresado correctamente        |
            | Auxiliar                   | CARGO              | 30           | 2020-03-01 |            |     |        |        | 200    | Cargo de Auxiliar ingresado correctamente                                                        |
            | Auxiliar ADM               | CARGO              | 30           | 2020-03-01 |            |     |        |        | 200    | Cargo de Auxiliar ADM ingresado correctamente                                                    |
            | Auxiliar ACAD              | CARGO              | 30           | 2020-03-01 |            | 1   | 1      | Tarde  | 501    | Cargo de Auxiliar ACAD es CARGO y no corresponde asignar división                                |
            | Matemática                 | ESPACIO_CURRICULAR | 6            | 2020-03-01 |            |     |        |        | 501    | Espacio Curricular Matemática falta asignar división                                             |
            | Secretario/a               | CARGO              | 36           | 2020-03-01 |            |     |        |        | 200    | Cargo de Secretario/a ingresado correctamente                                                    |
            | Bibliotecario/a            | CARGO              | 24           | 2020-03-01 |            |     |        |        | 200    | Cargo de Bibliotecario/a ingresado correctamente                                                 |
            | Regente                    | CARGO              | 36           | 2020-03-01 |            |     |        |        | 200    | Cargo de Regente ingresado correctamente                                                         |
            | Jefe de Taller             | CARGO              | 36           | 2020-03-01 |            |     |        |        | 200    | Cargo de Jefe de Taller ingresado correctamente                                                  |
            | Coordinador TIC            | CARGO              | 18           | 2020-03-01 |            |     |        |        | 200    | Cargo de Coordinador TIC ingresado correctamente                                                 |
            | Portero/a                  | CARGO              | 30           | 2020-03-01 |            |     |        |        | 200    | Cargo de Portero/a ingresado correctamente                                                       |
            | Jefe de Departamento       | CARGO              | 24           | 2020-03-01 |            |     |        |        | 200    | Cargo de Jefe de Departamento ingresado correctamente                                            |
            | Encargado de Medios        | CARGO              | 18           | 2020-03-01 |            |     |        |        | 200    | Cargo de Encargado de Medios ingresado correctamente                                             |
            | Coordinador de Turno       | CARGO              | 24           | 2020-03-01 |            |     |        |        | 200    | Cargo de Coordinador de Turno ingresado correctamente                                            |
            | Prosecretario/a            | CARGO              | 24           | 2020-03-01 |            |     |        |        | 200    | Cargo de Prosecretario/a ingresado correctamente                                                 |
            | Referente ESI              | CARGO              | 12           | 2020-03-01 |            |     |        |        | 200    | Cargo de Referente ESI ingresado correctamente                                                   |
            | Coordinador de Biblioteca  | CARGO              | 18           | 2020-03-01 |            |     |        |        | 200    | Cargo de Coordinador de Biblioteca ingresado correctamente                                       |
            | Jefe de Laboratorio        | CARGO              | 18           | 2020-03-01 |            |     |        |        | 200    | Cargo de Jefe de Laboratorio ingresado correctamente                                             |
            | Psicopedagogo/a            | CARGO              | 18           | 2020-03-01 |            |     |        |        | 200    | Cargo de Psicopedagogo/a ingresado correctamente                                                 |
            | Lengua                     | ESPACIO_CURRICULAR | 4            | 2020-03-01 |            | 1   | 2      | Tarde  | 200    | Espacio Curricular Lengua para la división 1º 2º Turno Tarde ingresado correctamente             |
            | Inglés                     | ESPACIO_CURRICULAR | 3            | 2020-03-01 |            | 3   | 2      | Mañana | 200    | Espacio Curricular Inglés para la división 3º 2º Turno Mañana ingresado correctamente            |
            | Química                    | ESPACIO_CURRICULAR | 4            | 2020-03-01 |            | 6   | 1      | Noche  | 200    | Espacio Curricular Química para la división 6º 1º Turno Noche ingresado correctamente            |
            | Biología                   | ESPACIO_CURRICULAR | 4            | 2020-03-01 |            | 2   | 2      | Mañana | 200    | Espacio Curricular Biología para la división 2º 2º Turno Mañana ingresado correctamente          |
            | Historia                   | ESPACIO_CURRICULAR | 4            | 2020-03-01 |            | 5   | 1      | Tarde  | 200    | Espacio Curricular Historia para la división 5º 1º Turno Tarde ingresado correctamente           |
            | Jefe de Preceptores        | CARGO              | 24           | 2020-03-01 |            |     |        |        | 200    | Cargo de Jefe de Preceptores ingresado correctamente                                             |
            | Coordinador de Área        | CARGO              | 18           | 2020-03-01 |            |     |        |        | 200    | Cargo de Coordinador de Área ingresado correctamente                                             |
            | Asesor Pedagógico          | CARGO              | 12           | 2020-03-01 |            |     |        |        | 200    | Cargo de Asesor Pedagógico ingresado correctamente                                               |
            | Coordinador de Convivencia | CARGO              | 18           | 2020-03-01 |            |     |        |        | 200    | Cargo de Coordinador de Convivencia ingresado correctamente                                      |
            | Computación                | ESPACIO_CURRICULAR | 4            | 2020-03-01 |            | 3   | 2      | Mañana | 200    | Espacio Curricular Computación para la división 3º 2º Turno Mañana ingresado correctamente       |
            | Educación Plástica         | ESPACIO_CURRICULAR | 2            | 2020-03-01 |            | 1   | 1      | Tarde  | 200    | Espacio Curricular Educación Plástica para la división 1º 1º Turno Tarde ingresado correctamente |
            | Música                     | ESPACIO_CURRICULAR | 2            | 2020-03-01 |            | 2   | 2      | Mañana | 200    | Espacio Curricular Música para la división 2º 2º Turno Mañana ingresado correctamente            |
            | Educación Física           | ESPACIO_CURRICULAR | 3            | 2020-03-01 |            | 3   | 1      | Tarde  | 200    | Espacio Curricular Educación Física para la división 3º 1º Turno Tarde ingresado correctamente   |