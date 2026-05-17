INSERT INTO usuarios (nombre, email, password) VALUES ('Jia Jie', 'jia.jie@alumnos.upm.es', '1234'); -- ID 1
INSERT INTO usuarios (nombre, email, password) VALUES ('Alvaro Vicente', 'alvaro.vicente@alumnos.upm.es', '**'); -- ID 2

INSERT INTO viajes (conductor_id, origen, destino, plazas_disponibles, fecha_salida, hora_salida, duracion_estimada, estado, cantidad, moneda) 
VALUES (2, 'Madrid', 'Barcelona', 3, '2026-05-15', '8:00:00.0', 360, 'ACTIVO', 45.50, 'EURO');

INSERT INTO viajes (conductor_id, origen, destino, plazas_disponibles, fecha_salida, hora_salida, duracion_estimada, estado, cantidad, moneda)
VALUES (1, 'Madrid', 'Valencia', 2, '2023-08-31', '15:00:00.0', 240, 'FINALIZADO', 30.00, 'EURO');


INSERT INTO reservas (viaje_id, pasajero_id, numero_pasajeros, fecha_creacion, estado, id_pago)
VALUES (1, 1, 2, null, 'CONFIRMADA', null);
INSERT INTO reservas (viaje_id, pasajero_id, numero_pasajeros, fecha_creacion, estado, id_pago)
VALUES (2, 2, 1, null, 'FINALIZADA', null);
INSERT INTO reservas (viaje_id, pasajero_id, numero_pasajeros, fecha_creacion, estado, id_pago)
VALUES (1, 1, 2, null, 'CONFIRMADA', null);