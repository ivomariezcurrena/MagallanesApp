-- 15 licencias para designaciones de 1° año, artículos 5A (id=3), 5B (id=4), 23A (id=19), 36A (id=30)

INSERT INTO
    licencia (
        id,
        pedido_desde,
        pedido_hasta,
        domicilio,
        certificado_Medico,
        estado,
        persona_id,
        articulo_id
    )
VALUES
    -- Artículo 5A
    (
        1,
        '2025-03-01T00:00:00',
        '2025-03-05T00:00:00',
        'Calle Lavalle 123',
        true,
        'Valido',
        40000028,
        3
    ),
    (
        2,
        '2025-04-10T00:00:00',
        '2025-04-15T00:00:00',
        'Av. Pellegrini 456',
        true,
        'Valido',
        40000041,
        3
    ),
    (
        3,
        '2025-05-01T00:00:00',
        '2025-05-03T00:00:00',
        'Calle Córdoba 789',
        true,
        'Valido',
        40000054,
        3
    ),

-- Artículo 5B
(
    4,
    '2025-03-15T00:00:00',
    '2025-03-25T00:00:00',
    'Av. Libertador 101',
    true,
    'Valido',
    40000029,
    4
),
(
    5,
    '2025-06-01T00:00:00',
    '2025-06-10T00:00:00',
    'Calle Suipacha 202',
    true,
    'Valido',
    40000042,
    4
),
(
    6,
    '2025-07-05T00:00:00',
    '2025-07-15T00:00:00',
    'Calle Mitre 404',
    true,
    'Valido',
    40000055,
    4
),

-- Artículo 23A
(
    7,
    '2025-04-20T00:00:00',
    '2025-04-25T00:00:00',
    'Av. Belgrano 505',
    true,
    'Valido',
    40000030,
    19
),
(
    8,
    '2025-05-10T00:00:00',
    '2025-05-15T00:00:00',
    'Calle Mendoza 606',
    true,
    'Valido',
    40000043,
    19
),
(
    9,
    '2025-06-20T00:00:00',
    '2025-06-25T00:00:00',
    'Calle Sarmiento 808',
    true,
    'Valido',
    40000056,
    19
),

-- Artículo 36A
(
    10,
    '2025-03-10T00:00:00',
    '2025-03-11T00:00:00',
    'Av. San Martín 707',
    false,
    'Invalido',
    40000031,
    30
),
(
    11,
    '2025-04-15T00:00:00',
    '2025-04-16T00:00:00',
    'Av. Santa Fe 909',
    false,
    'Invalido',
    40000044,
    30
),
(
    12,
    '2025-05-20T00:00:00',
    '2025-05-21T00:00:00',
    'Calle Tucumán 1010',
    false,
    'Invalido',
    40000057,
    30
),

-- Combinadas
(
    13,
    '2025-06-01T00:00:00',
    '2025-06-05T00:00:00',
    'Av. Córdoba 1111',
    true,
    'Valido',
    40000032,
    3
),
(
    14,
    '2025-07-10T00:00:00',
    '2025-07-15T00:00:00',
    'Calle San Juan 1212',
    true,
    'Valido',
    40000045,
    4
),
(
    15,
    '2025-08-01T00:00:00',
    '2025-08-03T00:00:00',
    'Av. Independencia 1313',
    true,
    'Valido',
    40000058,
    19
),
-- 5A: No superar 30 días en el año
(
    16,
    '2025-08-10T00:00:00',
    '2025-08-15T00:00:00',
    'Calle Entre Ríos 1414',
    true,
    'Valido',
    40000017,
    3
),
(
    17,
    '2025-09-01T00:00:00',
    '2025-09-05T00:00:00',
    'Av. Italia 1515',
    true,
    'Valido',
    40000018,
    3
),
(
    18,
    '2025-10-01T00:00:00',
    '2025-10-06T00:00:00',
    'Calle Corrientes 1616',
    true,
    'Valido',
    40000019,
    3
),
(
    19,
    '2025-11-01T00:00:00',
    '2025-11-05T00:00:00',
    'Av. España 1717',
    true,
    'Valido',
    40000020,
    3
),

-- 5B: No superar 60 días en el año
(
    20,
    '2025-08-01T00:00:00',
    '2025-08-20T00:00:00',
    'Calle Salta 1818',
    true,
    'Valido',
    40000021,
    4
),
(
    21,
    '2025-09-01T00:00:00',
    '2025-09-15T00:00:00',
    'Av. Alem 1919',
    true,
    'Valido',
    40000022,
    4
),
(
    22,
    '2025-10-01T00:00:00',
    '2025-10-20T00:00:00',
    'Calle Catamarca 2020',
    true,
    'Valido',
    40000023,
    4
),
(
    23,
    '2025-11-01T00:00:00',
    '2025-11-10T00:00:00',
    'Av. Vélez Sarsfield 2121',
    true,
    'Valido',
    40000024,
    4
),

-- 23A: No superar 30 días en el año
(
    24,
    '2025-08-01T00:00:00',
    '2025-08-05T00:00:00',
    'Calle San Luis 2222',
    true,
    'Valido',
    40000025,
    19
),
(
    25,
    '2025-09-01T00:00:00',
    '2025-09-06T00:00:00',
    'Av. Mitre 2323',
    true,
    'Valido',
    40000026,
    19
),
(
    26,
    '2025-10-01T00:00:00',
    '2025-10-05T00:00:00',
    'Av. Callao 2525',
    true,
    'Valido',
    40000028,
    19
),
(
    27,
    '2025-11-01T00:00:00',
    '2025-11-06T00:00:00',
    'Calle Alberdi 2626',
    true,
    'Valido',
    40000029,
    19
),

-- 36A: No superar 2 días por mes ni 6 días por año
(
    28,
    '2025-08-10T00:00:00',
    '2025-08-11T00:00:00',
    'Av. Santa Fe 2727',
    false,
    'Invalido',
    40000030,
    30
),
(
    29,
    '2025-09-15T00:00:00',
    '2025-09-16T00:00:00',
    'Calle Moreno 2828',
    false,
    'Invalido',
    40000031,
    30
),
(
    30,
    '2025-10-20T00:00:00',
    '2025-10-21T00:00:00',
    'Av. San Juan 2929',
    false,
    'Invalido',
    40000032,
    30
),
(
    31,
    '2025-11-10T00:00:00',
    '2025-11-11T00:00:00',
    'Calle Roca 3030',
    false,
    'Invalido',
    40000033,
    30
),
-- 15 licencias adicionales, respetando los topes de días de los artículos 5A (30/año), 5B (60/año), 23A (30/año), 36A (2/mes y 6/año)

-- 5A: No superar 30 días en el año
(
    32,
    '2025-12-01T00:00:00',
    '2025-12-05T00:00:00',
    'Calle Laprida 4040',
    true,
    'Valido',
    40000034,
    3
),
(
    33,
    '2025-12-10T00:00:00',
    '2025-12-15T00:00:00',
    'Calle Rioja 4141',
    true,
    'Valido',
    40000035,
    3
),

-- 5B: No superar 60 días en el año
(
    34,
    '2025-12-01T00:00:00',
    '2025-12-15T00:00:00',
    'Calle Santa Fe 4242',
    true,
    'Valido',
    40000036,
    4
),
(
    35,
    '2025-12-16T00:00:00',
    '2025-12-30T00:00:00',
    'Calle Buenos Aires 4343',
    true,
    'Valido',
    40000037,
    4
),

-- 23A: No superar 30 días en el año
(
    36,
    '2025-12-01T00:00:00',
    '2025-12-05T00:00:00',
    'Calle Entre Ríos 4444',
    true,
    'Valido',
    40000038,
    19
),
(
    37,
    '2025-12-10T00:00:00',
    '2025-12-15T00:00:00',
    'Calle Mendoza 4545',
    true,
    'Valido',
    40000039,
    19
),

-- 36A: No superar 2 días por mes ni 6 días por año (todas de 1 o 2 días, meses distintos)
(
    38,
    '2025-12-01T00:00:00',
    '2025-12-02T00:00:00',
    'Calle Salta 4646',
    false,
    'Invalido',
    40000040,
    30
),
(
    39,
    '2025-12-10T00:00:00',
    '2025-12-11T00:00:00',
    'Calle Tucumán 4747',
    false,
    'Invalido',
    40000041,
    30
),

-- 5A: más ejemplos, sin superar el tope anual
(
    40,
    '2025-01-10T00:00:00',
    '2025-01-12T00:00:00',
    'Calle San Juan 4848',
    true,
    'Valido',
    40000042,
    3
),
(
    41,
    '2025-02-01T00:00:00',
    '2025-02-03T00:00:00',
    'Calle Mitre 4949',
    true,
    'Valido',
    40000043,
    3
),

-- 5B: más ejemplos, sin superar el tope anual
(
    42,
    '2025-01-15T00:00:00',
    '2025-01-25T00:00:00',
    'Calle Pellegrini 5050',
    true,
    'Valido',
    40000044,
    4
),
(
    43,
    '2025-02-10T00:00:00',
    '2025-02-20T00:00:00',
    'Calle Balcarce 5151',
    true,
    'Valido',
    40000045,
    4
),

-- 23A: más ejemplos, sin superar el tope anual
(
    44,
    '2025-01-05T00:00:00',
    '2025-01-07T00:00:00',
    'Calle Moreno 5252',
    true,
    'Valido',
    40000046,
    19
),
(
    45,
    '2025-02-10T00:00:00',
    '2025-02-13T00:00:00',
    'Calle San Luis 5353',
    true,
    'Valido',
    40000047,
    19
),

-- 36A: más ejemplos, sin superar el tope mensual/anual
(
    46,
    '2025-01-15T00:00:00',
    '2025-01-16T00:00:00',
    'Calle Córdoba 5454',
    false,
    'Invalido',
    40000048,
    30
),

-- Licencias adicionales para las mismas personas (40000028 a 40000048), respetando los topes de cada artículo

-- 5A: No superar 30 días en el año por persona
(
    47,
    '2025-03-10T00:00:00',
    '2025-03-12T00:00:00',
    'Calle Lavalle 123',
    true,
    'Valido',
    40000028,
    3
),
(
    48,
    '2025-04-20T00:00:00',
    '2025-04-22T00:00:00',
    'Av. Pellegrini 456',
    true,
    'Valido',
    40000041,
    3
),

-- 5B: No superar 60 días en el año por persona
(
    49,
    '2025-05-15T00:00:00',
    '2025-05-25T00:00:00',
    'Av. Libertador 101',
    true,
    'Valido',
    40000029,
    4
),
(
    50,
    '2025-06-15T00:00:00',
    '2025-06-20T00:00:00',
    'Calle Suipacha 202',
    true,
    'Valido',
    40000042,
    4
),

-- 23A: No superar 30 días en el año por persona
(
    51,
    '2025-07-01T00:00:00',
    '2025-07-03T00:00:00',
    'Av. Belgrano 505',
    true,
    'Valido',
    40000030,
    19
),
(
    52,
    '2025-08-10T00:00:00',
    '2025-08-12T00:00:00',
    'Calle Mendoza 606',
    true,
    'Valido',
    40000043,
    19
),

-- 36A: No superar 2 días por mes ni 6 días por año por persona
(
    53,
    '2025-09-01T00:00:00',
    '2025-09-02T00:00:00',
    'Av. San Martín 707',
    false,
    'Invalido',
    40000031,
    30
),
(
    54,
    '2025-10-01T00:00:00',
    '2025-10-02T00:00:00',
    'Av. Santa Fe 909',
    false,
    'Invalido',
    40000044,
    30
),

-- 5A: Más ejemplos para las mismas personas
(
    55,
    '2025-05-10T00:00:00',
    '2025-05-12T00:00:00',
    'Calle Córdoba 789',
    true,
    'Valido',
    40000054,
    3
),
(
    56,
    '2025-06-01T00:00:00',
    '2025-06-03T00:00:00',
    'Calle Entre Ríos 1414',
    true,
    'Valido',
    40000017,
    3
),

-- 5B: Más ejemplos para las mismas personas
(
    57,
    '2025-07-20T00:00:00',
    '2025-07-25T00:00:00',
    'Calle Mitre 404',
    true,
    'Valido',
    40000055,
    4
),
(
    58,
    '2025-08-01T00:00:00',
    '2025-08-05T00:00:00',
    'Calle Salta 1818',
    true,
    'Valido',
    40000021,
    4
),

-- 23A: Más ejemplos para las mismas personas
(
    59,
    '2025-09-10T00:00:00',
    '2025-09-12T00:00:00',
    'Calle San Luis 2222',
    true,
    'Valido',
    40000025,
    19
),
(
    60,
    '2025-10-15T00:00:00',
    '2025-10-17T00:00:00',
    'Av. Mitre 2323',
    true,
    'Valido',
    40000026,
    19
);