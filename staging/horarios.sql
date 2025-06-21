-- 1° 1ra (división 1) - Turno Mañana (7 a 12)
INSERT INTO
    horario (id, dia, hora, cargo_id)
VALUES
    -- Lunes: Lengua (2), Matemática (2), Historia (1), Tutoría (1)
    (1, 'Lunes', 7, 1001),
    (2, 'Lunes', 8, 1001),
    (3, 'Lunes', 9, 1002),
    (4, 'Lunes', 10, 1002),
    (5, 'Lunes', 11, 1003),
    (6, 'Lunes', 12, 1013),
    -- Martes: Matemática (2), Biología (2), Geografía (1), Física (1)
    (7, 'Martes', 7, 1002),
    (8, 'Martes', 8, 1002),
    (9, 'Martes', 9, 1005),
    (10, 'Martes', 10, 1005),
    (11, 'Martes', 11, 1004),
    (12, 'Martes', 12, 1011),
    -- Miércoles: Lengua (2), Historia (1), Inglés (2), Educación Física (1)
    (13, 'Miércoles', 7, 1001),
    (14, 'Miércoles', 8, 1001),
    (15, 'Miércoles', 9, 1003),
    (16, 'Miércoles', 10, 1007),
    (17, 'Miércoles', 11, 1007),
    (18, 'Miércoles', 12, 1006),
    -- Jueves: Biología (2), Tecnología (2), Química (1), Tutoría (1)
    (19, 'Jueves', 7, 1005),
    (20, 'Jueves', 8, 1005),
    (21, 'Jueves', 9, 1010),
    (22, 'Jueves', 10, 1010),
    (23, 'Jueves', 11, 1012),
    (24, 'Jueves', 12, 1013),
    -- Viernes: Geografía (1), Educación Artística (2), Educación Física (1), Construcción de Ciudadanía (1), Física (1)
    (25, 'Viernes', 7, 1004),
    (26, 'Viernes', 8, 1008),
    (27, 'Viernes', 9, 1008),
    (28, 'Viernes', 10, 1006),
    (29, 'Viernes', 11, 1009),
    (30, 'Viernes', 12, 1011);

-- 1° 2da (división 2) - Turno Mañana (7 a 12)
INSERT INTO
    horario (id, dia, hora, cargo_id)
VALUES
    -- Lunes: Lengua (2), Matemática (2), Historia (1), Tutoría (1)
    (31, 'Lunes', 7, 1021),
    (32, 'Lunes', 8, 1021),
    (33, 'Lunes', 9, 1022),
    (34, 'Lunes', 10, 1022),
    (35, 'Lunes', 11, 1023),
    (36, 'Lunes', 12, 1033),
    -- Martes: Matemática (2), Biología (2), Geografía (1), Física (1)
    (37, 'Martes', 7, 1022),
    (38, 'Martes', 8, 1022),
    (39, 'Martes', 9, 1025),
    (40, 'Martes', 10, 1025),
    (41, 'Martes', 11, 1024),
    (42, 'Martes', 12, 1031),
    -- Miércoles: Lengua (2), Historia (1), Inglés (2), Educación Física (1)
    (43, 'Miércoles', 7, 1021),
    (44, 'Miércoles', 8, 1021),
    (45, 'Miércoles', 9, 1023),
    (46, 'Miércoles', 10, 1027),
    (47, 'Miércoles', 11, 1027),
    (48, 'Miércoles', 12, 1026),
    -- Jueves: Biología (2), Tecnología (2), Química (1), Tutoría (1)
    (49, 'Jueves', 7, 1025),
    (50, 'Jueves', 8, 1025),
    (51, 'Jueves', 9, 1030),
    (52, 'Jueves', 10, 1030),
    (53, 'Jueves', 11, 1032),
    (54, 'Jueves', 12, 1033),
    -- Viernes: Geografía (1), Educación Artística (2), Educación Física (1), Construcción de Ciudadanía (1), Física (1)
    (55, 'Viernes', 7, 1024),
    (56, 'Viernes', 8, 1028),
    (57, 'Viernes', 9, 1028),
    (58, 'Viernes', 10, 1026),
    (59, 'Viernes', 11, 1029),
    (60, 'Viernes', 12, 1031);

-- 1° 3ra (división 3) - Turno Tarde (13 a 18)
INSERT INTO
    horario (id, dia, hora, cargo_id)
VALUES
    -- Lunes: Lengua (2), Matemática (2), Historia (1), Tutoría (1)
    (61, 'Lunes', 13, 1041),
    (62, 'Lunes', 14, 1041),
    (63, 'Lunes', 15, 1042),
    (64, 'Lunes', 16, 1042),
    (65, 'Lunes', 17, 1043),
    (66, 'Lunes', 18, 1053),
    -- Martes: Matemática (2), Biología (2), Geografía (1), Física (1)
    (67, 'Martes', 13, 1042),
    (68, 'Martes', 14, 1042),
    (69, 'Martes', 15, 1045),
    (70, 'Martes', 16, 1045),
    (71, 'Martes', 17, 1044),
    (72, 'Martes', 18, 1051),
    -- Miércoles: Lengua (2), Historia (1), Inglés (2), Educación Física (1)
    (73, 'Miércoles', 13, 1041),
    (74, 'Miércoles', 14, 1041),
    (75, 'Miércoles', 15, 1043),
    (76, 'Miércoles', 16, 1047),
    (77, 'Miércoles', 17, 1047),
    (78, 'Miércoles', 18, 1046),
    -- Jueves: Biología (2), Tecnología (2), Química (1), Tutoría (1)
    (79, 'Jueves', 13, 1045),
    (80, 'Jueves', 14, 1045),
    (81, 'Jueves', 15, 1050),
    (82, 'Jueves', 16, 1050),
    (83, 'Jueves', 17, 1052),
    (84, 'Jueves', 18, 1053),
    -- Viernes: Geografía (1), Educación Artística (2), Educación Física (1), Construcción de Ciudadanía (1), Física (1)
    (85, 'Viernes', 13, 1044),
    (86, 'Viernes', 14, 1048),
    (87, 'Viernes', 15, 1048),
    (88, 'Viernes', 16, 1046),
    (89, 'Viernes', 17, 1049),
    (90, 'Viernes', 18, 1051);

-- 1° 4ta (división 4) - Turno Tarde (13 a 18)
INSERT INTO
    horario (id, dia, hora, cargo_id)
VALUES
    -- Lunes: Lengua (2), Matemática (2), Historia (1), Tutoría (1)
    (91, 'Lunes', 13, 1061),
    (92, 'Lunes', 14, 1061),
    (93, 'Lunes', 15, 1062),
    (94, 'Lunes', 16, 1062),
    (95, 'Lunes', 17, 1063),
    (96, 'Lunes', 18, 1073),
    -- Martes: Matemática (2), Biología (2), Geografía (1), Física (1)
    (97, 'Martes', 13, 1062),
    (98, 'Martes', 14, 1062),
    (99, 'Martes', 15, 1065),
    (100, 'Martes', 16, 1065),
    (101, 'Martes', 17, 1064),
    (102, 'Martes', 18, 1071),
    -- Miércoles: Lengua (2), Historia (1), Inglés (2), Educación Física (1)
    (103, 'Miércoles', 13, 1061),
    (104, 'Miércoles', 14, 1061),
    (105, 'Miércoles', 15, 1063),
    (106, 'Miércoles', 16, 1067),
    (107, 'Miércoles', 17, 1067),
    (108, 'Miércoles', 18, 1066),
    -- Jueves: Biología (2), Tecnología (2), Química (1), Tutoría (1)
    (109, 'Jueves', 13, 1065),
    (110, 'Jueves', 14, 1065),
    (111, 'Jueves', 15, 1070),
    (112, 'Jueves', 16, 1070),
    (113, 'Jueves', 17, 1072),
    (114, 'Jueves', 18, 1073),
    -- Viernes: Geografía (1), Educación Artística (2), Educación Física (1), Construcción de Ciudadanía (1), Física (1)
    (115, 'Viernes', 13, 1064),
    (116, 'Viernes', 14, 1068),
    (117, 'Viernes', 15, 1068),
    (118, 'Viernes', 16, 1066),
    (119, 'Viernes', 17, 1069),
    (120, 'Viernes', 18, 1071);
-- 2° 1ra (división 5) - Turno Mañana (7 a 12)
INSERT INTO horario (id, dia, hora, cargo_id) VALUES
    -- Lunes: Lengua (2), Matemática (2), Historia (2)
    (301, 'Lunes', 7, 2001),
    (302, 'Lunes', 8, 2001),
    (303, 'Lunes', 9, 2002),
    (304, 'Lunes', 10, 2002),
    (305, 'Lunes', 11, 2003),
    (306, 'Lunes', 12, 2003),
    -- Martes: Matemática (2), Biología (2), Geografía (2)
    (307, 'Martes', 7, 2002),
    (308, 'Martes', 8, 2002),
    (309, 'Martes', 9, 2005),
    (310, 'Martes', 10, 2005),
    (311, 'Martes', 11, 2004),
    (312, 'Martes', 12, 2004),
    -- Miércoles: Lengua (2), Inglés (2), Educación Física (2)
    (313, 'Miércoles', 7, 2001),
    (314, 'Miércoles', 8, 2001),
    (315, 'Miércoles', 9, 2007),
    (316, 'Miércoles', 10, 2007),
    (317, 'Miércoles', 11, 2006),
    (318, 'Miércoles', 12, 2006),
    -- Jueves: Tecnología (2), Química (2), Tutoría (2)
    (319, 'Jueves', 7, 2010),
    (320, 'Jueves', 8, 2010),
    (321, 'Jueves', 9, 2012),
    (322, 'Jueves', 10, 2012),
    (323, 'Jueves', 11, 2013),
    (324, 'Jueves', 12, 2013),
    -- Viernes: Educación Artística (2), Construcción de Ciudadanía (2), Física (2)
    (325, 'Viernes', 7, 2008),
    (326, 'Viernes', 8, 2008),
    (327, 'Viernes', 9, 2009),
    (328, 'Viernes', 10, 2009),
    (329, 'Viernes', 11, 2011),
    (330, 'Viernes', 12, 2011);

-- 2° 2da (división 6) - Turno Mañana (7 a 12)
INSERT INTO horario (id, dia, hora, cargo_id) VALUES
    -- Lunes: Lengua (2), Matemática (2), Historia (2)
    (331, 'Lunes', 7, 2021),
    (332, 'Lunes', 8, 2021),
    (333, 'Lunes', 9, 2022),
    (334, 'Lunes', 10, 2022),
    (335, 'Lunes', 11, 2023),
    (336, 'Lunes', 12, 2023),
    -- Martes: Matemática (2), Biología (2), Geografía (2)
    (337, 'Martes', 7, 2022),
    (338, 'Martes', 8, 2022),
    (339, 'Martes', 9, 2025),
    (340, 'Martes', 10, 2025),
    (341, 'Martes', 11, 2024),
    (342, 'Martes', 12, 2024),
    -- Miércoles: Lengua (2), Inglés (2), Educación Física (2)
    (343, 'Miércoles', 7, 2021),
    (344, 'Miércoles', 8, 2021),
    (345, 'Miércoles', 9, 2027),
    (346, 'Miércoles', 10, 2027),
    (347, 'Miércoles', 11, 2026),
    (348, 'Miércoles', 12, 2026),
    -- Jueves: Tecnología (2), Química (2), Tutoría (2)
    (349, 'Jueves', 7, 2030),
    (350, 'Jueves', 8, 2030),
    (351, 'Jueves', 9, 2032),
    (352, 'Jueves', 10, 2032),
    (353, 'Jueves', 11, 2033),
    (354, 'Jueves', 12, 2033),
    -- Viernes: Educación Artística (2), Construcción de Ciudadanía (2), Física (2)
    (355, 'Viernes', 7, 2028),
    (356, 'Viernes', 8, 2028),
    (357, 'Viernes', 9, 2029),
    (358, 'Viernes', 10, 2029),
    (359, 'Viernes', 11, 2031),
    (360, 'Viernes', 12, 2031);

-- 2° 3ra (división 7) - Turno Tarde (13 a 18)
INSERT INTO horario (id, dia, hora, cargo_id) VALUES
    -- Lunes: Lengua (2), Matemática (2), Historia (2)
    (361, 'Lunes', 13, 2041),
    (362, 'Lunes', 14, 2041),
    (363, 'Lunes', 15, 2042),
    (364, 'Lunes', 16, 2042),
    (365, 'Lunes', 17, 2043),
    (366, 'Lunes', 18, 2043),
    -- Martes: Matemática (2), Biología (2), Geografía (2)
    (367, 'Martes', 13, 2042),
    (368, 'Martes', 14, 2042),
    (369, 'Martes', 15, 2045),
    (370, 'Martes', 16, 2045),
    (371, 'Martes', 17, 2044),
    (372, 'Martes', 18, 2044),
    -- Miércoles: Lengua (2), Inglés (2), Educación Física (2)
    (373, 'Miércoles', 13, 2041),
    (374, 'Miércoles', 14, 2041),
    (375, 'Miércoles', 15, 2047),
    (376, 'Miércoles', 16, 2047),
    (377, 'Miércoles', 17, 2046),
    (378, 'Miércoles', 18, 2046),
    -- Jueves: Tecnología (2), Química (2), Tutoría (2)
    (379, 'Jueves', 13, 2050),
    (380, 'Jueves', 14, 2050),
    (381, 'Jueves', 15, 2052),
    (382, 'Jueves', 16, 2052),
    (383, 'Jueves', 17, 2053),
    (384, 'Jueves', 18, 2053),
    -- Viernes: Educación Artística (2), Construcción de Ciudadanía (2), Física (2)
    (385, 'Viernes', 13, 2048),
    (386, 'Viernes', 14, 2048),
    (387, 'Viernes', 15, 2049),
    (388, 'Viernes', 16, 2049),
    (389, 'Viernes', 17, 2051),
    (390, 'Viernes', 18, 2051);

-- 2° 4ta (división 8) - Turno Tarde (13 a 18)
INSERT INTO horario (id, dia, hora, cargo_id) VALUES
    -- Lunes: Lengua (2), Matemática (2), Historia (2)
    (391, 'Lunes', 13, 2061),
    (392, 'Lunes', 14, 2061),
    (393, 'Lunes', 15, 2062),
    (394, 'Lunes', 16, 2062),
    (395, 'Lunes', 17, 2063),
    (396, 'Lunes', 18, 2063),
    -- Martes: Matemática (2), Biología (2), Geografía (2)
    (397, 'Martes', 13, 2062),
    (398, 'Martes', 14, 2062),
    (399, 'Martes', 15, 2065),
    (400, 'Martes', 16, 2065),
    (401, 'Martes', 17, 2064),
    (402, 'Martes', 18, 2064),
    -- Miércoles: Lengua (2), Inglés (2), Educación Física (2)
    (403, 'Miércoles', 13, 2061),
    (404, 'Miércoles', 14, 2061),
    (405, 'Miércoles', 15, 2067),
    (406, 'Miércoles', 16, 2067),
    (407, 'Miércoles', 17, 2066),
    (408, 'Miércoles', 18, 2066),
    -- Jueves: Tecnología (2), Química (2), Tutoría (2)
    (409, 'Jueves', 13, 2070),
    (410, 'Jueves', 14, 2070),
    (411, 'Jueves', 15, 2072),
    (412, 'Jueves', 16, 2072),
    (413, 'Jueves', 17, 2073),
    (414, 'Jueves', 18, 2073),
    -- Viernes: Educación Artística (2), Construcción de Ciudadanía (2), Física (2)
    (415, 'Viernes', 13, 2068),
    (416, 'Viernes', 14, 2068),
    (417, 'Viernes', 15, 2069),
    (418, 'Viernes', 16, 2069),
    (419, 'Viernes', 17, 2071),
    (420, 'Viernes', 18, 2071);
-- 3° 1ra (división 9) - Turno Mañana (7 a 12)
INSERT INTO horario (id, dia, hora, cargo_id) VALUES
    -- Lunes: Lengua (2), Matemática (2), Historia (2)
    (501, 'Lunes', 7, 3001),
    (502, 'Lunes', 8, 3001),
    (503, 'Lunes', 9, 3002),
    (504, 'Lunes', 10, 3002),
    (505, 'Lunes', 11, 3003),
    (506, 'Lunes', 12, 3003),
    -- Martes: Matemática (2), Biología (2), Geografía (2)
    (507, 'Martes', 7, 3002),
    (508, 'Martes', 8, 3002),
    (509, 'Martes', 9, 3005),
    (510, 'Martes', 10, 3005),
    (511, 'Martes', 11, 3004),
    (512, 'Martes', 12, 3004),
    -- Miércoles: Lengua (2), Inglés (2), Educación Física (2)
    (513, 'Miércoles', 7, 3001),
    (514, 'Miércoles', 8, 3001),
    (515, 'Miércoles', 9, 3007),
    (516, 'Miércoles', 10, 3007),
    (517, 'Miércoles', 11, 3006),
    (518, 'Miércoles', 12, 3006),
    -- Jueves: Tecnología (2), Química (2), Tutoría (2)
    (519, 'Jueves', 7, 3010),
    (520, 'Jueves', 8, 3010),
    (521, 'Jueves', 9, 3012),
    (522, 'Jueves', 10, 3012),
    (523, 'Jueves', 11, 3013),
    (524, 'Jueves', 12, 3013),
    -- Viernes: Educación Artística (2), Construcción de Ciudadanía (2), Física (2)
    (525, 'Viernes', 7, 3008),
    (526, 'Viernes', 8, 3008),
    (527, 'Viernes', 9, 3009),
    (528, 'Viernes', 10, 3009),
    (529, 'Viernes', 11, 3011),
    (530, 'Viernes', 12, 3011);

-- 3° 2da (división 10) - Turno Mañana (7 a 12)
INSERT INTO horario (id, dia, hora, cargo_id) VALUES
    -- Lunes: Lengua (2), Matemática (2), Historia (2)
    (531, 'Lunes', 7, 3021),
    (532, 'Lunes', 8, 3021),
    (533, 'Lunes', 9, 3022),
    (534, 'Lunes', 10, 3022),
    (535, 'Lunes', 11, 3023),
    (536, 'Lunes', 12, 3023),
    -- Martes: Matemática (2), Biología (2), Geografía (2)
    (537, 'Martes', 7, 3022),
    (538, 'Martes', 8, 3022),
    (539, 'Martes', 9, 3025),
    (540, 'Martes', 10, 3025),
    (541, 'Martes', 11, 3024),
    (542, 'Martes', 12, 3024),
    -- Miércoles: Lengua (2), Inglés (2), Educación Física (2)
    (543, 'Miércoles', 7, 3021),
    (544, 'Miércoles', 8, 3021),
    (545, 'Miércoles', 9, 3027),
    (546, 'Miércoles', 10, 3027),
    (547, 'Miércoles', 11, 3026),
    (548, 'Miércoles', 12, 3026),
    -- Jueves: Tecnología (2), Química (2), Tutoría (2)
    (549, 'Jueves', 7, 3030),
    (550, 'Jueves', 8, 3030),
    (551, 'Jueves', 9, 3032),
    (552, 'Jueves', 10, 3032),
    (553, 'Jueves', 11, 3033),
    (554, 'Jueves', 12, 3033),
    -- Viernes: Educación Artística (2), Construcción de Ciudadanía (2), Física (2)
    (555, 'Viernes', 7, 3028),
    (556, 'Viernes', 8, 3028),
    (557, 'Viernes', 9, 3029),
    (558, 'Viernes', 10, 3029),
    (559, 'Viernes', 11, 3031),
    (560, 'Viernes', 12, 3031);

-- 3° 3ra (división 11) - Turno Tarde (13 a 18)
INSERT INTO horario (id, dia, hora, cargo_id) VALUES
    -- Lunes: Lengua (2), Matemática (2), Historia (2)
    (561, 'Lunes', 13, 3041),
    (562, 'Lunes', 14, 3041),
    (563, 'Lunes', 15, 3042),
    (564, 'Lunes', 16, 3042),
    (565, 'Lunes', 17, 3043),
    (566, 'Lunes', 18, 3043),
    -- Martes: Matemática (2), Biología (2), Geografía (2)
    (567, 'Martes', 13, 3042),
    (568, 'Martes', 14, 3042),
    (569, 'Martes', 15, 3045),
    (570, 'Martes', 16, 3045),
    (571, 'Martes', 17, 3044),
    (572, 'Martes', 18, 3044),
    -- Miércoles: Lengua (2), Inglés (2), Educación Física (2)
    (573, 'Miércoles', 13, 3041),
    (574, 'Miércoles', 14, 3041),
    (575, 'Miércoles', 15, 3047),
    (576, 'Miércoles', 16, 3047),
    (577, 'Miércoles', 17, 3046),
    (578, 'Miércoles', 18, 3046),
    -- Jueves: Tecnología (2), Química (2), Tutoría (2)
    (579, 'Jueves', 13, 3050),
    (580, 'Jueves', 14, 3050),
    (581, 'Jueves', 15, 3052),
    (582, 'Jueves', 16, 3052),
    (583, 'Jueves', 17, 3053),
    (584, 'Jueves', 18, 3053),
    -- Viernes: Educación Artística (2), Construcción de Ciudadanía (2), Física (2)
    (585, 'Viernes', 13, 3048),
    (586, 'Viernes', 14, 3048),
    (587, 'Viernes', 15, 3049),
    (588, 'Viernes', 16, 3049),
    (589, 'Viernes', 17, 3051),
    (590, 'Viernes', 18, 3051);

-- 3° 4ta (división 12) - Turno Tarde (13 a 18)
INSERT INTO horario (id, dia, hora, cargo_id) VALUES
    -- Lunes: Lengua (2), Matemática (2), Historia (2)
    (591, 'Lunes', 13, 3061),
    (592, 'Lunes', 14, 3061),
    (593, 'Lunes', 15, 3062),
    (594, 'Lunes', 16, 3062),
    (595, 'Lunes', 17, 3063),
    (596, 'Lunes', 18, 3063),
    -- Martes: Matemática (2), Biología (2), Geografía (2)
    (597, 'Martes', 13, 3062),
    (598, 'Martes', 14, 3062),
    (599, 'Martes', 15, 3065),
    (600, 'Martes', 16, 3065),
    (601, 'Martes', 17, 3064),
    (602, 'Martes', 18, 3064),
    -- Miércoles: Lengua (2), Inglés (2), Educación Física (2)
    (603, 'Miércoles', 13, 3061),
    (604, 'Miércoles', 14, 3061),
    (605, 'Miércoles', 15, 3067),
    (606, 'Miércoles', 16, 3067),
    (607, 'Miércoles', 17, 3066),
    (608, 'Miércoles', 18, 3066),
    -- Jueves: Tecnología (2), Química (2), Tutoría (2)
    (609, 'Jueves', 13, 3070),
    (610, 'Jueves', 14, 3070),
    (611, 'Jueves', 15, 3072),
    (612, 'Jueves', 16, 3072),
    (613, 'Jueves', 17, 3073),
    (614, 'Jueves', 18, 3073),
    -- Viernes: Educación Artística (2), Construcción de Ciudadanía (2), Física (2)
    (615, 'Viernes', 13, 3068),
    (616, 'Viernes', 14, 3068),
    (617, 'Viernes', 15, 3069),
    (618, 'Viernes', 16, 3069),
    (619, 'Viernes', 17, 3071),
    (620, 'Viernes', 18, 3071);
-- 4° 1ra (división 13) - Economía y Administración - Mañana (7 a 11)
INSERT INTO
    horario (id, dia, hora, cargo_id)
VALUES (821, 'Lunes', 7, 4001),
    (822, 'Lunes', 8, 4002),
    (823, 'Lunes', 9, 4007),
    (824, 'Lunes', 10, 4008),
    (825, 'Lunes', 11, 4005),
    (826, 'Martes', 7, 4001),
    (827, 'Martes', 8, 4002),
    (828, 'Martes', 9, 4009),
    (829, 'Martes', 10, 4010),
    (830, 'Martes', 11, 4006),
    (831, 'Miércoles', 7, 4003),
    (832, 'Miércoles', 8, 4004),
    (833, 'Miércoles', 9, 4007),
    (834, 'Miércoles', 10, 4008),
    (835, 'Miércoles', 11, 4011),
    (836, 'Jueves', 7, 4001),
    (837, 'Jueves', 8, 4002),
    (838, 'Jueves', 9, 4009),
    (839, 'Jueves', 10, 4010),
    (840, 'Jueves', 11, 4012),
    (841, 'Viernes', 7, 4003),
    (842, 'Viernes', 8, 4004),
    (843, 'Viernes', 9, 4007),
    (844, 'Viernes', 10, 4008),
    (845, 'Viernes', 11, 4013);

-- 4° 2da (división 14) - Administración Pública - Mañana (7 a 11)
INSERT INTO
    horario (id, dia, hora, cargo_id)
VALUES (846, 'Lunes', 7, 4021),
    (847, 'Lunes', 8, 4022),
    (848, 'Lunes', 9, 4027),
    (849, 'Lunes', 10, 4028),
    (850, 'Lunes', 11, 4025),
    (851, 'Martes', 7, 4021),
    (852, 'Martes', 8, 4022),
    (853, 'Martes', 9, 4029),
    (854, 'Martes', 10, 4030),
    (855, 'Martes', 11, 4026),
    (856, 'Miércoles', 7, 4023),
    (857, 'Miércoles', 8, 4024),
    (858, 'Miércoles', 9, 4027),
    (859, 'Miércoles', 10, 4028),
    (860, 'Miércoles', 11, 4031),
    (861, 'Jueves', 7, 4021),
    (862, 'Jueves', 8, 4022),
    (863, 'Jueves', 9, 4029),
    (864, 'Jueves', 10, 4030),
    (865, 'Jueves', 11, 4032),
    (866, 'Viernes', 7, 4023),
    (867, 'Viernes', 8, 4024),
    (868, 'Viernes', 9, 4027),
    (869, 'Viernes', 10, 4028),
    (870, 'Viernes', 11, 4033);

-- 4° 3ra (división 15) - Economía y Administración - Tarde (13 a 17)
INSERT INTO
    horario (id, dia, hora, cargo_id)
VALUES (871, 'Lunes', 13, 4041),
    (872, 'Lunes', 14, 4042),
    (873, 'Lunes', 15, 4047),
    (874, 'Lunes', 16, 4048),
    (875, 'Lunes', 17, 4045),
    (876, 'Martes', 13, 4041),
    (877, 'Martes', 14, 4042),
    (878, 'Martes', 15, 4049),
    (879, 'Martes', 16, 4050),
    (880, 'Martes', 17, 4046),
    (881, 'Miércoles', 13, 4043),
    (882, 'Miércoles', 14, 4044),
    (883, 'Miércoles', 15, 4047),
    (884, 'Miércoles', 16, 4048),
    (885, 'Miércoles', 17, 4051),
    (886, 'Jueves', 13, 4041),
    (887, 'Jueves', 14, 4042),
    (888, 'Jueves', 15, 4049),
    (889, 'Jueves', 16, 4050),
    (890, 'Jueves', 17, 4052),
    (891, 'Viernes', 13, 4043),
    (892, 'Viernes', 14, 4044),
    (893, 'Viernes', 15, 4047),
    (894, 'Viernes', 16, 4048),
    (895, 'Viernes', 17, 4053);

-- 4° 4ta (división 16) - Administración Pública - Tarde (13 a 17)
INSERT INTO
    horario (id, dia, hora, cargo_id)
VALUES (896, 'Lunes', 13, 4061),
    (897, 'Lunes', 14, 4062),
    (898, 'Lunes', 15, 4067),
    (899, 'Lunes', 16, 4068),
    (900, 'Lunes', 17, 4065),
    (901, 'Martes', 13, 4061),
    (902, 'Martes', 14, 4062),
    (903, 'Martes', 15, 4069),
    (904, 'Martes', 16, 4070),
    (905, 'Martes', 17, 4066),
    (906, 'Miércoles', 13, 4063),
    (907, 'Miércoles', 14, 4064),
    (908, 'Miércoles', 15, 4067),
    (909, 'Miércoles', 16, 4068),
    (910, 'Miércoles', 17, 4071),
    (911, 'Jueves', 13, 4061),
    (912, 'Jueves', 14, 4062),
    (913, 'Jueves', 15, 4069),
    (914, 'Jueves', 16, 4070),
    (915, 'Jueves', 17, 4072),
    (916, 'Viernes', 13, 4063),
    (917, 'Viernes', 14, 4064),
    (918, 'Viernes', 15, 4067),
    (919, 'Viernes', 16, 4068),
    (920, 'Viernes', 17, 4073);

-- 5° 1ra (división 17) - Economía y Administración - Mañana (7 a 11)
INSERT INTO
    horario (id, dia, hora, cargo_id)
VALUES (921, 'Lunes', 7, 5001), -- Lengua y Literatura
    (922, 'Lunes', 8, 5002), -- Matemática
    (923, 'Lunes', 9, 5007), -- Economía
    (924, 'Lunes', 10, 5008), -- Contabilidad
    (925, 'Lunes', 11, 5005), -- Educación Física
    (926, 'Martes', 7, 5001),
    (927, 'Martes', 8, 5002),
    (928, 'Martes', 9, 5009), -- Gestión de las Organizaciones
    (929, 'Martes', 10, 5010), -- Derecho
    (930, 'Martes', 11, 5006), -- Inglés
    (931, 'Miércoles', 7, 5003), -- Historia
    (932, 'Miércoles', 8, 5004), -- Geografía Económica
    (933, 'Miércoles', 9, 5007),
    (934, 'Miércoles', 10, 5008),
    (935, 'Miércoles', 11, 5011), -- Tecnología de la Información
    (936, 'Jueves', 7, 5001),
    (937, 'Jueves', 8, 5002),
    (938, 'Jueves', 9, 5009),
    (939, 'Jueves', 10, 5010),
    (940, 'Jueves', 11, 5012), -- Tutoría
    (941, 'Viernes', 7, 5003),
    (942, 'Viernes', 8, 5004),
    (943, 'Viernes', 9, 5007),
    (944, 'Viernes', 10, 5008),
    (945, 'Viernes', 11, 5013);
-- Educación Artística

-- 5° 2da (división 18) - Administración Pública - Mañana (7 a 11)
INSERT INTO
    horario (id, dia, hora, cargo_id)
VALUES (946, 'Lunes', 7, 5021), -- Lengua y Literatura
    (947, 'Lunes', 8, 5022), -- Matemática
    (948, 'Lunes', 9, 5027), -- Administración Pública
    (949, 'Lunes', 10, 5028), -- Derecho Administrativo
    (950, 'Lunes', 11, 5025), -- Educación Física
    (951, 'Martes', 7, 5021),
    (952, 'Martes', 8, 5022),
    (953, 'Martes', 9, 5029), -- Gestión Pública
    (954, 'Martes', 10, 5030), -- Ética y Ciudadanía
    (955, 'Martes', 11, 5026), -- Inglés
    (956, 'Miércoles', 7, 5023), -- Historia Argentina
    (957, 'Miércoles', 8, 5024), -- Geografía Política
    (958, 'Miércoles', 9, 5027),
    (959, 'Miércoles', 10, 5028),
    (960, 'Miércoles', 11, 5031), -- Tecnología de la Información
    (961, 'Jueves', 7, 5021),
    (962, 'Jueves', 8, 5022),
    (963, 'Jueves', 9, 5029),
    (964, 'Jueves', 10, 5030),
    (965, 'Jueves', 11, 5032), -- Tutoría
    (966, 'Viernes', 7, 5023),
    (967, 'Viernes', 8, 5024),
    (968, 'Viernes', 9, 5027),
    (969, 'Viernes', 10, 5028),
    (970, 'Viernes', 11, 5033);
-- Educación Artística

-- 5° 3ra (división 19) - Economía y Administración - Tarde (13 a 17)
INSERT INTO
    horario (id, dia, hora, cargo_id)
VALUES (971, 'Lunes', 13, 5041),
    (972, 'Lunes', 14, 5042),
    (973, 'Lunes', 15, 5047),
    (974, 'Lunes', 16, 5048),
    (975, 'Lunes', 17, 5045),
    (976, 'Martes', 13, 5041),
    (977, 'Martes', 14, 5042),
    (978, 'Martes', 15, 5049),
    (979, 'Martes', 16, 5050),
    (980, 'Martes', 17, 5046),
    (981, 'Miércoles', 13, 5043),
    (982, 'Miércoles', 14, 5044),
    (983, 'Miércoles', 15, 5047),
    (984, 'Miércoles', 16, 5048),
    (985, 'Miércoles', 17, 5051),
    (986, 'Jueves', 13, 5041),
    (987, 'Jueves', 14, 5042),
    (988, 'Jueves', 15, 5049),
    (989, 'Jueves', 16, 5050),
    (990, 'Jueves', 17, 5052),
    (991, 'Viernes', 13, 5043),
    (992, 'Viernes', 14, 5044),
    (993, 'Viernes', 15, 5047),
    (994, 'Viernes', 16, 5048),
    (995, 'Viernes', 17, 5053);

-- 5° 4ta (división 20) - Administración Pública - Tarde (13 a 17)
INSERT INTO
    horario (id, dia, hora, cargo_id)
VALUES (996, 'Lunes', 13, 5061),
    (997, 'Lunes', 14, 5062),
    (998, 'Lunes', 15, 5067),
    (999, 'Lunes', 16, 5068),
    (1000, 'Lunes', 17, 5065),
    (1001, 'Martes', 13, 5061),
    (1002, 'Martes', 14, 5062),
    (1003, 'Martes', 15, 5069),
    (1004, 'Martes', 16, 5070),
    (1005, 'Martes', 17, 5066),
    (1006, 'Miércoles', 13, 5063),
    (1007, 'Miércoles', 14, 5064),
    (1008, 'Miércoles', 15, 5067),
    (1009, 'Miércoles', 16, 5068),
    (1010, 'Miércoles', 17, 5071),
    (1011, 'Jueves', 13, 5061),
    (1012, 'Jueves', 14, 5062),
    (1013, 'Jueves', 15, 5069),
    (1014, 'Jueves', 16, 5070),
    (1015, 'Jueves', 17, 5072),
    (1016, 'Viernes', 13, 5063),
    (1017, 'Viernes', 14, 5064),
    (1018, 'Viernes', 15, 5067),
    (1019, 'Viernes', 16, 5068),
    (1020, 'Viernes', 17, 5073);

-- 6° 1ra (división 21) - Economía y Administración - Mañana (7 a 11)
INSERT INTO
    horario (id, dia, hora, cargo_id)
VALUES (1021, 'Lunes', 7, 6001), -- Lengua y Literatura
    (1022, 'Lunes', 8, 6002), -- Matemática
    (1023, 'Lunes', 9, 6007), -- Economía III
    (1024, 'Lunes', 10, 6008), -- Contabilidad III
    (1025, 'Lunes', 11, 6005), -- Educación Física
    (1026, 'Martes', 7, 6001),
    (1027, 'Martes', 8, 6002),
    (1028, 'Martes', 9, 6009), -- Gestión de las Organizaciones III
    (1029, 'Martes', 10, 6010), -- Derecho Laboral
    (1030, 'Martes', 11, 6006), -- Inglés
    (1031, 'Miércoles', 7, 6003), -- Historia
    (1032, 'Miércoles', 8, 6004), -- Geografía Económica
    (1033, 'Miércoles', 9, 6007),
    (1034, 'Miércoles', 10, 6008),
    (1035, 'Miércoles', 11, 6011), -- Tecnología de la Información
    (1036, 'Jueves', 7, 6001),
    (1037, 'Jueves', 8, 6002),
    (1038, 'Jueves', 9, 6009),
    (1039, 'Jueves', 10, 6010),
    (1040, 'Jueves', 11, 6012), -- Tutoría
    (1041, 'Viernes', 7, 6003),
    (1042, 'Viernes', 8, 6004),
    (1043, 'Viernes', 9, 6007),
    (1044, 'Viernes', 10, 6008),
    (1045, 'Viernes', 11, 6013);
-- Educación Artística

-- 6° 2da (división 22) - Administración Pública - Mañana (7 a 11)
INSERT INTO
    horario (id, dia, hora, cargo_id)
VALUES (1046, 'Lunes', 7, 6021), -- Lengua y Literatura
    (1047, 'Lunes', 8, 6022), -- Matemática
    (1048, 'Lunes', 9, 6027), -- Administración Pública III
    (1049, 'Lunes', 10, 6028), -- Derecho Administrativo III
    (1050, 'Lunes', 11, 6025), -- Educación Física
    (1051, 'Martes', 7, 6021),
    (1052, 'Martes', 8, 6022),
    (1053, 'Martes', 9, 6029), -- Gestión Pública III
    (1054, 'Martes', 10, 6030), -- Ética y Ciudadanía III
    (1055, 'Martes', 11, 6026), -- Inglés
    (1056, 'Miércoles', 7, 6023), -- Historia Argentina
    (1057, 'Miércoles', 8, 6024), -- Geografía Política
    (1058, 'Miércoles', 9, 6027),
    (1059, 'Miércoles', 10, 6028),
    (1060, 'Miércoles', 11, 6031), -- Tecnología de la Información
    (1061, 'Jueves', 7, 6021),
    (1062, 'Jueves', 8, 6022),
    (1063, 'Jueves', 9, 6029),
    (1064, 'Jueves', 10, 6030),
    (1065, 'Jueves', 11, 6032), -- Tutoría
    (1066, 'Viernes', 7, 6023),
    (1067, 'Viernes', 8, 6024),
    (1068, 'Viernes', 9, 6027),
    (1069, 'Viernes', 10, 6028),
    (1070, 'Viernes', 11, 6033);
-- Educación Artística

-- 6° 3ra (división 23) - Economía y Administración - Tarde (13 a 17)
INSERT INTO
    horario (id, dia, hora, cargo_id)
VALUES (1071, 'Lunes', 13, 6041),
    (1072, 'Lunes', 14, 6042),
    (1073, 'Lunes', 15, 6047),
    (1074, 'Lunes', 16, 6048),
    (1075, 'Lunes', 17, 6045),
    (1076, 'Martes', 13, 6041),
    (1077, 'Martes', 14, 6042),
    (1078, 'Martes', 15, 6049),
    (1079, 'Martes', 16, 6050),
    (1080, 'Martes', 17, 6046),
    (1081, 'Miércoles', 13, 6043),
    (1082, 'Miércoles', 14, 6044),
    (1083, 'Miércoles', 15, 6047),
    (1084, 'Miércoles', 16, 6048),
    (1085, 'Miércoles', 17, 6051),
    (1086, 'Jueves', 13, 6041),
    (1087, 'Jueves', 14, 6042),
    (1088, 'Jueves', 15, 6049),
    (1089, 'Jueves', 16, 6050),
    (1090, 'Jueves', 17, 6052),
    (1091, 'Viernes', 13, 6043),
    (1092, 'Viernes', 14, 6044),
    (1093, 'Viernes', 15, 6047),
    (1094, 'Viernes', 16, 6048),
    (1095, 'Viernes', 17, 6053);

-- 6° 4ta (división 24) - Administración Pública - Tarde (13 a 17)
INSERT INTO
    horario (id, dia, hora, cargo_id)
VALUES (1096, 'Lunes', 13, 6061),
    (1097, 'Lunes', 14, 6062),
    (1098, 'Lunes', 15, 6067),
    (1099, 'Lunes', 16, 6068),
    (1100, 'Lunes', 17, 6065),
    (1101, 'Martes', 13, 6061),
    (1102, 'Martes', 14, 6062),
    (1103, 'Martes', 15, 6069),
    (1104, 'Martes', 16, 6070),
    (1105, 'Martes', 17, 6066),
    (1106, 'Miércoles', 13, 6063),
    (1107, 'Miércoles', 14, 6064),
    (1108, 'Miércoles', 15, 6067),
    (1109, 'Miércoles', 16, 6068),
    (1110, 'Miércoles', 17, 6071),
    (1111, 'Jueves', 13, 6061),
    (1112, 'Jueves', 14, 6062),
    (1113, 'Jueves', 15, 6069),
    (1114, 'Jueves', 16, 6070),
    (1115, 'Jueves', 17, 6072),
    (1116, 'Viernes', 13, 6063),
    (1117, 'Viernes', 14, 6064),
    (1118, 'Viernes', 15, 6067),
    (1119, 'Viernes', 16, 6068),
    (1120, 'Viernes', 17, 6073);