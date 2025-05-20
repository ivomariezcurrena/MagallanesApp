-- Elimina todos los datos de las tablas (sin borrar las tablas)
TRUNCATE TABLE licencia RESTART IDENTITY CASCADE;

TRUNCATE TABLE persona RESTART IDENTITY CASCADE;

TRUNCATE TABLE articulo_licencia RESTART IDENTITY CASCADE;

TRUNCATE TABLE designacion RESTART IDENTITY CASCADE;

TRUNCATE TABLE cargo RESTART IDENTITY CASCADE;

TRUNCATE TABLE division RESTART IDENTITY CASCADE;

-- Agrega aquí otras tablas si tienes más en tu modelo