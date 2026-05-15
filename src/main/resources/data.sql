INSERT INTO viajes (conductor_id, origen, destino, plazas_disponibles, fecha_salida, hora_salida, duracion_estimada, estado, cantidad, moneda) 
VALUES (null, 'Madrid', 'Barcelona', 3, null, null, 360, 'ACTIVO', 45.50, 'EURO');

INSERT INTO viajes (conductor_id, origen, destino, plazas_disponibles, fecha_salida, hora_salida, duracion_estimada, estado, cantidad, moneda)
VALUES (6, 'Madrid', 'Valencia', 2, null, null, 240, 'FINALIZADO', 30.00, 'EURO');


INSERT INTO reservas (viaje_id, pasajero_id, numero_pasajeros, fecha_creacion, estado, id_pago)
VALUES (1, 1, 2, null, 'CONFIRMADA', null);
INSERT INTO reservas (viaje_id, pasajero_id, numero_pasajeros, fecha_creacion, estado, id_pago)
VALUES (2, 2, 1, null, 'FINALIZADA', null);
INSERT INTO reservas (viaje_id, pasajero_id, numero_pasajeros, fecha_creacion, estado, id_pago)
VALUES (1, 3, 2, null, 'CONFIRMADA', null);