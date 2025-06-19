            # language: es

            Característica: otorgar o denegar licencia a una persona a un cargo docente
            actividad central de información de la escuela secundaria

            Esquema del escenario: Otorgar Distintas licencias según las reglas de los distintos artículos
            Dado el docente con DNI <DNI>, nombre "<Nombre>" y apellido "<Apellido>"
            Cuando solicita una licencia artículo "<Artículo>" con descripción "<Descripción>" para el período "<Desde>" "<Hasta>"
            Entonces debería obtener la siguiente resultado de <status> y "<Respuesta>"

            Ejemplos:
            | DNI      | Nombre      | Apellido     | Artículo | Descripción                   | Desde      | Hasta      | status | Respuesta                                                                                                                  |
            | 90900900 | Ermenegildo | Sábat        | 5A       | ENFERMEDAD DE CORTA EVOLUCIÓN | 2023-05-07 | 2023-05-17 | 200    | Se otorga Licencia artículo 5A a Ermenegildo Sábat                                                                         |
            | 90900900 | Ermenegildo | Sábat        | 5A       | ENFERMEDAD DE CORTA EVOLUCIÓN | 2023-05-18 | 2023-05-31 | 200    | Se otorga Licencia artículo 5A a Ermenegildo Sábat                                                                         |
            | 90900900 | Ermenegildo | Sábat        | 5A       | ENFERMEDAD DE CORTA EVOLUCIÓN | 2023-06-01 | 2023-06-12 | 500    | NO se otorga Licencia artículo 5A a Ermenegildo Sábat debido a que supera el tope de 30 días de licencia                   |
            | 90900900 | Ermenegildo | Sábat        | 5A       | ENFERMEDAD DE CORTA EVOLUCIÓN | 2023-10-01 | 2023-10-03 | 200    | Se otorga Licencia artículo 5A a Ermenegildo Sábat                                                                         |
            | 90900900 | Ermenegildo | Sábat        | 5A       | ENFERMEDAD DE CORTA EVOLUCIÓN | 2023-10-04 | 2023-10-10 | 500    | NO se otorga Licencia artículo 5A a Ermenegildo Sábat debido a que supera el tope de 30 días de licencia                   |
            | 11110110 | María Rosa  | Gallo        | 23A      | ATENCIÓN DE UN MIEMBRO DEL GF | 2023-02-15 | 2023-03-01 | 200    | Se otorga Licencia artículo 23A a María Rosa Gallo                                                                         |
            | 11110110 | María Rosa  | Gallo        | 23A      | ATENCIÓN DE UN MIEMBRO DEL GF | 2023-04-01 | 2023-04-12 | 200    | Se otorga Licencia artículo 23A a María Rosa Gallo                                                                         |
            | 11110110 | María Rosa  | Gallo        | 23A      | ATENCIÓN DE UN MIEMBRO DEL GF | 2023-04-01 | 2023-04-02 | 500    | NO se otorga Licencia artículo 23A a María Rosa Gallo debido a que ya posee una licencia en el mismo período               |
            | 11110110 | María Rosa  | Gallo        | 23A      | ATENCIÓN DE UN MIEMBRO DEL GF | 2023-04-17 | 2023-04-20 | 500    | NO se otorga Licencia artículo 23A a María Rosa Gallo debido a que supera el tope de 30 días de licencia                   |
            | 12120120 | Homero      | Manzi        | 36A      | ASUNTOS PARTICULARES          | 2023-05-08 | 2023-05-08 | 200    | Se otorga Licencia artículo 36A a Homero Manzi                                                                             |
            | 12120120 | Homero      | Manzi        | 36A      | ASUNTOS PARTICULARES          | 2023-05-11 | 2023-05-11 | 200    | Se otorga Licencia artículo 36A a Homero Manzi                                                                             |
            | 12120120 | Homero      | Manzi        | 36A      | ASUNTOS PARTICULARES          | 2023-05-20 | 2023-05-20 | 500    | NO se otorga Licencia artículo 36A a Homero Manzi debido a que supera el tope de 2 días de licencia                        |
            | 12120120 | Homero      | Manzi        | 36A      | ASUNTOS PARTICULARES          | 2023-08-13 | 2023-08-14 | 200    | Se otorga Licencia artículo 36A a Homero Manzi                                                                             |
            | 12120120 | Homero      | Manzi        | 36A      | ASUNTOS PARTICULARES          | 2023-09-24 | 2023-09-25 | 200    | Se otorga Licencia artículo 36A a Homero Manzi                                                                             |
            | 12120120 | Homero      | Manzi        | 36A      | ASUNTOS PARTICULARES          | 2023-11-04 | 2023-11-04 | 500    | NO se otorga Licencia artículo 36A a Homero Manzi debido a que supera el tope de 6 días de licencia por año                |
            | 99999999 | Raúl        | Guitierrez   | 36A      | ASUNTOS PARTICULARES          | 2023-03-04 | 2023-03-04 | 500    | NO se otorga Licencia artículo 36A a Raúl Guitierrez debido a que el agente no posee ningún cargo en la institución        |
            | 88888888 | Marisa      | Balaguer     | 36A      | ASUNTOS PARTICULARES          | 2023-03-04 | 2023-03-04 | 500    | NO se otorga Licencia artículo 36A a Marisa Balaguer debido a que el agente no tiene designación ese día en la institución |
            | 20200200 | Susana      | Álvarez      | 5A       | ENFERMEDAD DE CORTA EVOLUCIÓN | 2023-05-12 | 2023-05-30 | 200    | Se otorga Licencia artículo 5A a Susana Álvarez                                                                            |
            | 20000000 | Rosalía     | Fernandez    | 5A       | ENFERMEDAD DE CORTA EVOLUCIÓN | 2023-07-05 | 2023-07-15 | 200    | Se otorga Licencia artículo 5A a Rosalía Fernandez                                                                         |
            | 88100000 | Raúl        | Orellanos    | 5A       | ENFERMEDAD DE CORTA EVOLUCIÓN | 2024-05-07 | 2024-05-15 | 200    | Se otorga Licencia artículo 5A a Raúl Orellanos                                                                            |
            | 88200000 | Matías      | Barto        | 5A       | ENFERMEDAD DE CORTA EVOLUCIÓN | 2024-05-10 | 2024-05-15 | 200    | Se otorga Licencia artículo 5A a Matías Barto                                                                              |
            | 88300000 | Andrea      | Sosa         | 5A       | ENFERMEDAD DE CORTA EVOLUCIÓN | 2024-05-30 | 2024-06-17 | 200    | Se otorga Licencia artículo 5A a Andrea Sosa                                                                               |
            | 88400000 | Laura       | Barrientos   | 23A      | ATENCIÓN DE UN MIEMBRO DEL GF | 2024-05-08 | 2024-05-16 | 200    | Se otorga Licencia artículo 23A a Laura Barrientos                                                                         |
            | 88500000 | Natalia     | Zabala       | 23A      | ATENCIÓN DE UN MIEMBRO DEL GF | 2024-05-13 | 2024-05-22 | 200    | Se otorga Licencia artículo 23A a Natalia Zabala                                                                           |
            | 10100100 | Alberto     | Lopez        | 5A       | ENFERMEDAD DE CORTA EVOLUCIÓN | 2025-05-01 | 2025-05-10 | 200    | Se otorga Licencia artículo 5A a Alberto Lopez                                                                             |
            | 40400400 | Marísa      | Amuchástegui | 23A      | ATENCIÓN DE UN MIEMBRO DEL GF | 2025-04-10 | 2025-04-20 | 200    | Se otorga Licencia artículo 23A a Marísa Amuchástegui                                                                      |
            | 50500500 | Raúl        | Gómez        | 5A       | ENFERMEDAD DE CORTA EVOLUCIÓN | 2025-03-15 | 2025-03-25 | 200    | Se otorga Licencia artículo 5A a Raúl Gómez                                                                                |
            | 90900900 | Ermenegildo | Sábat        | 5A       | ENFERMEDAD DE CORTA EVOLUCIÓN | 2025-09-01 | 2025-09-10 | 200    | Se otorga Licencia artículo 5A a Ermenegildo Sábat                                                                         |
            | 11110110 | María Rosa  | Gallo        | 23A      | ATENCIÓN DE UN MIEMBRO DEL GF | 2025-07-01 | 2025-07-07 | 200    | Se otorga Licencia artículo 23A a María Rosa Gallo                                                                         |
            | 12120120 | Homero      | Manzi        | 36A      | ASUNTOS PARTICULARES          | 2025-05-05 | 2025-05-05 | 200    | Se otorga Licencia artículo 36A a Homero Manzi                                                                             |
            | 88100000 | Raúl        | Orellanos    | 5A       | ENFERMEDAD DE CORTA EVOLUCIÓN | 2025-04-01 | 2025-04-08 | 200    | Se otorga Licencia artículo 5A a Raúl Orellanos                                                                            |
            | 88200000 | Matías      | Barto        | 5A       | ENFERMEDAD DE CORTA EVOLUCIÓN | 2025-09-15 | 2025-09-22 | 200    | Se otorga Licencia artículo 5A a Matías Barto                                                                              |
            | 88300000 | Andrea      | Sosa         | 5A       | ENFERMEDAD DE CORTA EVOLUCIÓN | 2025-10-01 | 2025-10-10 | 200    | Se otorga Licencia artículo 5A a Andrea Sosa                                                                               |
            | 88400000 | Laura       | Barrientos   | 23A      | ATENCIÓN DE UN MIEMBRO DEL GF | 2025-03-01 | 2025-03-05 | 200    | Se otorga Licencia artículo 23A a Laura Barrientos                                                                         |
            | 88500000 | Natalia     | Zabala       | 23A      | ATENCIÓN DE UN MIEMBRO DEL GF | 2025-11-01 | 2025-11-08 | 200    | Se otorga Licencia artículo 23A a Natalia Zabala                                                                           |
            | 10101010 | Frank       | Sinatra      | 5A       | ENFERMEDAD DE CORTA EVOLUCIÓN | 2025-08-01 | 2025-08-10 | 200    | Se otorga Licencia artículo 5A a Frank Sinatra                                                                             |
            | 20202020 | Pity        | Alvarez      | 5A       | ENFERMEDAD DE CORTA EVOLUCIÓN | 2025-07-01 | 2025-07-08 | 200    | Se otorga Licencia artículo 5A a Pity Alvarez                                                                              |
            | 30303030 | Elvis       | Presley      | 23A      | ATENCIÓN DE UN MIEMBRO DEL GF | 2025-09-01 | 2025-09-10 | 200    | Se otorga Licencia artículo 23A a Elvis Presley                                                                            |
            | 40404040 | Julian      | Alvarez      | 36A      | ASUNTOS PARTICULARES          | 2025-05-01 | 2025-05-01 | 200    | Se otorga Licencia artículo 36A a Julian Alvarez                                                                           |
            | 50505050 | Paul        | Pogba        | 36A      | ASUNTOS PARTICULARES          | 2025-06-01 | 2025-06-01 | 200    | Se otorga Licencia artículo 36A a Paul Pogba                                                                               |
            | 60606060 | Kilian      | Mbappe       | 5A       | ENFERMEDAD DE CORTA EVOLUCIÓN | 2025-04-01 | 2025-04-10 | 200    | Se otorga Licencia artículo 5A a Kilian Mbappe                                                                             |
            | 70707070 | Lionel      | Messi        | 23A      | ATENCIÓN DE UN MIEMBRO DEL GF | 2025-10-01 | 2025-10-08 | 200    | Se otorga Licencia artículo 23A a Lionel Messi                                                                             |
            | 80808080 | Cristiano   | Ronaldo      | 5A       | ENFERMEDAD DE CORTA EVOLUCIÓN | 2025-11-01 | 2025-11-10 | 200    | Se otorga Licencia artículo 5A a Cristiano Ronaldo                                                                         |
            | 90909090 | Ariel       | Ortega       | 36A      | ASUNTOS PARTICULARES          | 2025-09-01 | 2025-09-01 | 200    | Se otorga Licencia artículo 36A a Ariel Ortega                                                                             |
            | 11111111 | Franco      | Mastantuono  | 5A       | ENFERMEDAD DE CORTA EVOLUCIÓN | 2025-03-01 | 2025-03-08 | 200    | Se otorga Licencia artículo 5A a Franco Mastantuono                                                                        |
            | 12121212 | Ricardo     | Darin        | 23A      | ATENCIÓN DE UN MIEMBRO DEL GF | 2025-06-01 | 2025-06-10 | 200    | Se otorga Licencia artículo 23A a Ricardo Darin                                                                            |
            | 13131313 | Guillermo   | Francella    | 36A      | ASUNTOS PARTICULARES          | 2025-07-01 | 2025-07-01 | 200    | Se otorga Licencia artículo 36A a Guillermo Francella                                                                      |
            | 14141414 | Martin      | Palermo      | 5A       | ENFERMEDAD DE CORTA EVOLUCIÓN | 2025-05-01 | 2025-05-08 | 200    | Se otorga Licencia artículo 5A a Martin Palermo                                                                            |
            | 15151515 | Enzo        | Perez        | 23A      | ATENCIÓN DE UN MIEMBRO DEL GF | 2025-08-01 | 2025-08-08 | 200    | Se otorga Licencia artículo 23A a Enzo Perez                                                                               |
            | 16161616 | Gianluigi   | Donnaruma    | 36A      | ASUNTOS PARTICULARES          | 2025-04-01 | 2025-04-01 | 200    | Se otorga Licencia artículo 36A a Gianluigi Donnaruma                                                                      |
            | 17171717 | Gianluigi   | Buffon       | 5A       | ENFERMEDAD DE CORTA EVOLUCIÓN | 2025-06-01 | 2025-06-08 | 200    | Se otorga Licencia artículo 5A a Gianluigi Buffon                                                                          |
            | 18181818 | Mario       | Kempes       | 23A      | ATENCIÓN DE UN MIEMBRO DEL GF | 2025-09-01 | 2025-09-08 | 200    | Se otorga Licencia artículo 23A a Mario Kempes                                                                             |
            | 19191919 | Diego       | Maradona     | 36A      | ASUNTOS PARTICULARES          | 2025-10-01 | 2025-10-01 | 200    | Se otorga Licencia artículo 36A a Diego Maradona                                                                           |
            | 19191919 | Diego       | Maradona     | 5B       | ENFERMEDAD DE LARGA DURACION  | 2025-10-01 | 2025-10-15 | 200    | Se otorga Licencia artículo 5B a Diego Maradona                                                                            |

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



