            # language: es

            Característica: otorgar o denegar licencia a una persona a un cargo docente
            actividad central de información de la escuela secundaria

            Esquema del escenario: Otorgar Distintas licencias según las reglas de los distintos artículos
            Dado el docente con DNI <DNI>, nombre "<Nombre>" y apellido "<Apellido>"
            Cuando solicita una licencia artículo "<Artículo>" con descripción "<Descripción>" para el período "<Desde>" "<Hasta>"
            Entonces debería obtener la siguiente resultado de <status> y "<Respuesta>"

            Ejemplos:
            | DNI      | Nombre      | Apellido   | Artículo | Descripción                   | Desde      | Hasta      | status | Respuesta                                                                                                                  |
            | 90900900 | Ermenegildo | Sábat      | 5A       | ENFERMEDAD DE CORTA EVOLUCIÓN | 2023-05-07 | 2023-05-17 | 200    | Se otorga Licencia artículo 5A a Ermenegildo Sábat                                                                         |
            | 90900900 | Ermenegildo | Sábat      | 5A       | ENFERMEDAD DE CORTA EVOLUCIÓN | 2023-05-18 | 2023-05-31 | 200    | Se otorga Licencia artículo 5A a Ermenegildo Sábat                                                                         |
            | 90900900 | Ermenegildo | Sábat      | 5A       | ENFERMEDAD DE CORTA EVOLUCIÓN | 2023-06-01 | 2023-06-12 | 500    | NO se otorga Licencia artículo 5A a Ermenegildo Sábat debido a que supera el tope de 30 días de licencia                   |
            | 90900900 | Ermenegildo | Sábat      | 5A       | ENFERMEDAD DE CORTA EVOLUCIÓN | 2023-10-01 | 2023-10-03 | 200    | Se otorga Licencia artículo 5A a Ermenegildo Sábat                                                                         |
            | 90900900 | Ermenegildo | Sábat      | 5A       | ENFERMEDAD DE CORTA EVOLUCIÓN | 2023-10-04 | 2023-10-10 | 500    | NO se otorga Licencia artículo 5A a Ermenegildo Sábat debido a que supera el tope de 30 días de licencia                   |
            | 11110110 | María Rosa  | Gallo      | 23A      | ATENCIÓN DE UN MIEMBRO DEL GF | 2023-02-15 | 2023-03-01 | 200    | Se otorga Licencia artículo 23A a María Rosa Gallo                                                                         |
            | 11110110 | María Rosa  | Gallo      | 23A      | ATENCIÓN DE UN MIEMBRO DEL GF | 2023-04-01 | 2023-04-14 | 200    | Se otorga Licencia artículo 23A a María Rosa Gallo                                                                         |
            | 11110110 | María Rosa  | Gallo      | 23A      | ATENCIÓN DE UN MIEMBRO DEL GF | 2023-04-12 | 2023-04-20 | 500    | NO se otorga Licencia artículo 23A a María Rosa Gallo debido a que ya posee una licencia en el mismo período               |
            | 11110110 | María Rosa  | Gallo      | 23A      | ATENCIÓN DE UN MIEMBRO DEL GF | 2023-04-17 | 2023-04-20 | 500    | NO se otorga Licencia artículo 23A a María Rosa Gallo debido a que supera el tope de 30 días de licencia                   |
            | 12120120 | Homero      | Manzi      | 36A      | ASUNTOS PARTICULARES          | 2023-05-08 | 2023-05-08 | 200    | Se otorga Licencia artículo 36A a Homero Manzi                                                                             |
            | 12120120 | Homero      | Manzi      | 36A      | ASUNTOS PARTICULARES          | 2023-05-11 | 2023-05-11 | 200    | Se otorga Licencia artículo 36A a Homero Manzi                                                                             |
            | 12120120 | Homero      | Manzi      | 36A      | ASUNTOS PARTICULARES          | 2023-05-20 | 2023-05-20 | 500    | NO se otorga Licencia artículo 36A a Homero Manzi debido a que supera el tope de 2 días de licencia                        |
            | 12120120 | Homero      | Manzi      | 36A      | ASUNTOS PARTICULARES          | 2023-08-13 | 2023-08-14 | 200    | Se otorga Licencia artículo 36A a Homero Manzi                                                                             |
            | 12120120 | Homero      | Manzi      | 36A      | ASUNTOS PARTICULARES          | 2023-09-24 | 2023-09-25 | 200    | Se otorga Licencia artículo 36A a Homero Manzi                                                                             |
            | 12120120 | Homero      | Manzi      | 36A      | ASUNTOS PARTICULARES          | 2023-11-04 | 2023-11-04 | 500    | NO se otorga Licencia artículo 36A a Homero Manzi debido a que supera el tope de 6 días de licencia por año                |
            | 99999999 | Raúl        | Guitierrez | 36A      | ASUNTOS PARTICULARES          | 2023-03-04 | 2023-03-04 | 500    | NO se otorga Licencia artículo 36A a Raúl Guitierrez debido a que el agente no posee ningún cargo en la institución        |
            | 88888888 | Marisa      | Balaguer   | 36A      | ASUNTOS PARTICULARES          | 2023-03-04 | 2023-03-04 | 500    | NO se otorga Licencia artículo 36A a Marisa Balaguer debido a que el agente no tiene designación ese día en la institución |
            | 20200200 | Susana      | Álvarez    | 5A       | ENFERMEDAD DE CORTA EVOLUCIÓN | 2023-05-12 | 2023-05-30 | 200    | Se otorga Licencia artículo 5A a Susana Álvarez                                                                            |
            | 20000000 | Rosalía     | Fernandez  | 5A       | ENFERMEDAD DE CORTA EVOLUCIÓN | 2023-07-05 | 2023-07-15 | 200    | Se otorga Licencia artículo 5A a Rosalía Fernandez                                                                         |

            Escenario: 1 persona en instancias de designación de cargo que cubre una licencia de otra persona en la misma designación. Infomar que está correcto y que reemplaza al docente que solicitó licencia.
            Dado que existe la persona
            | DNI      | Nombre | Apellido |
            | 70700700 | Jorge  | Dismal   |
            Y que existen las siguientes instancias de designación asignada
            | TipoDesignacion | NombreTipoDesignacion | CargaHoraria |
            | CARGO           | Preceptor/a           | 36           |
            Y que la instancia de designación está asignada a la persona con licencia "5A" comprendida en el período desde "2023-05-12" hasta "2023-05-30"
            Cuando se solicita el servicio de designación de la persona al cargo en el período comprendido desde "2023-05-12" hasta "2023-05-30"
            Entonces se recupera el mensaje
            """
            {
                "StatusCode": 200,
                "StatusText": "Jorge Dismal ha sido designado/a como Preceptor/a exitosamente"
            }
            """


            Escenario: 1 persona en instancias de designación de cargo que cubre una licencia de otra persona en la misma designación, pero que no coincide el mismo período. Infomar el error respectivo y abortar la transacción.
            Dado que existe la persona
            | DNI      | Nombre | Apellido |
            | 80800800 | Analía | Rojas    |
            Y que existen las siguientes instancias de designación asignada
            | TipoDesignacion | NombreTipoDesignacion | CargaHoraria |
            | CARGO           | Auxiliar ADM          | 30           |
            Y que la instancia de designación está asignada a la persona
            | DNI      | Nombre  | Apellido  | Desde      | Hasta      |
            | 20000000 | Rosalía | Fernandez | 2023-01-01 | 2023-12-31 |
            Y que la instancia de designación está asignada a la persona con licencia "5A" comprendida en el período desde "2023-07-05" hasta "2023-07-15"
            Cuando se solicita el servicio de designación de la persona al cargo en el período comprendido desde "2023-06-05" hasta "2023-09-15"
            Entonces se recupera el mensaje
            """
            {
                "StatusCode": 500,
                "StatusText": "Analía Rojas NO ha sido designado/a como Auxiliar ADM. pues el cargo solicitado lo ocupa Rosalía Fernandez para el período"
            }
            """



