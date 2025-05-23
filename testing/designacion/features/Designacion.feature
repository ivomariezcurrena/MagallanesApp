            # language: es

            Característica: designar una persona a un cargo docente
            actividad central de información de la escuela secundaria

            Esquema del escenario: Designación de persona en cargos NO cubiertos aún en el perído indicado de forma existosa
            Dada la persona con <DNI> "<nombre>" y "<apellido>"
            Y que se asigna al cargo  con tipo de designación "<tipo>" y "<nombreDesignación>"
            Y que si el tipo es "ESPACIO CURRICULAR", opcionalmente se asigna a la división "<año>" "<número>" "<turno>"
            Y se designa por el período "<fechaDesde>" "<fechaHasta>"
            Cuando se presiona el botón guardar
            Entonces se espera el siguiente <status> y "<respuesta>"

            Ejemplos:
            | DNI      | nombre      | apellido     | tipo               | nombreDesignación | año | número | turno  | fechaDesde | fechaHasta | status | respuesta                                                                                                      |
            | 10100100 | Alberto     | Lopez        | CARGO              | Vicedirector/a    |     |        |        | 2023-03-01 |            | 200    | Alberto Lopez ha sido designado/a como Vicedirector/a exitosamente                                             |
            | 20200200 | Susana      | Álvarez      | CARGO              | Preceptor/a       |     |        |        | 2023-03-01 | 2023-12-31 | 200    | Susana Álvarez ha sido designado/a como Preceptor/a exitosamente                                               |
            | 40400400 | Marísa      | Amuchástegui | ESPACIO_CURRICULAR | Historia          | 5   | 2      | Mañana | 2023-03-01 |            | 200    | Marísa Amuchástegui ha sido designado/a a la asignatura Historia a la división 5º 2º turno Mañana exitosamente |
            | 50500500 | Raúl        | Gómez        | ESPACIO_CURRICULAR | Geografía         | 3   | 1      | Tarde  | 2023-03-01 | 2025-12-31 | 200    | Raúl Gómez ha sido designado/a a la asignatura Geografía a la división 3º 1º turno Tarde exitosamente          |
            | 20000000 | Rosalía     | Fernandez    | CARGO              | Auxiliar ADM      |     |        |        | 2023-01-01 | 2023-12-31 | 200    | Rosalía Fernandez ha sido designado/a como Auxiliar ADM exitosamente                                           |
            | 90900900 | Ermenegildo | Sábat        | ESPACIO_CURRICULAR | Física            | 2   | 3      | Mañana | 2023-03-01 |            | 200    | Ermenegildo Sábat ha sido designado/a a la asignatura Física a la división 2º 3º turno Mañana exitosamente     |
            | 11110110 | María Rosa  | Gallo        | ESPACIO_CURRICULAR | Matemáticas       | 1   | 1      | Tarde  | 2023-03-01 |            | 200    | María Rosa Gallo ha sido designado/a a la asignatura Matemáticas a la división 1º 1º turno Tarde exitosamente  |
            | 12120120 | Homero      | Manzi        | ESPACIO_CURRICULAR | Tecnología        | 4   | 3      | Mañana | 2023-03-01 |            | 200    | Homero Manzi ha sido designado/a a la asignatura Tecnología a la división 4º 3º turno Mañana exitosamente      |
            | 88888888 | Marisa      | Balaguer     | ESPACIO_CURRICULAR | Física            | 2   | 3      | Mañana | 2022-03-01 | 2022-12-01 | 200    | Marisa Balaguer ha sido designado/a a la asignatura Física a la división 2º 3º turno Mañana exitosamente       |

