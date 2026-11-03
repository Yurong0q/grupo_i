# API de coche compartido

## Descripción

Backend de una aplicación de coche compartido, desarrollada con Spring Boot  y hecha para la asignatura de Ingeniería de Software por el grupo i.

Permite a un conductor publicar viajes, a los pasajeros reservar plazas, y reseñar el viaje si ha finalizado.

## Integrantes 
- Yurong Chen
- Álvaro Vicente Bonilla
- Pablo Regidor Méndez
- Jia Jie Ye

## Funcionalidades
- Registro, login y cambio de contraseña de usuarios.
- Creación, búsqueda y cancelación de viajes.
- Reservas con control automático de plazas disponibles.
- Reseñas de viajes finalizados.
- Pagos y notificaciones simulados (`PagosFake` y `NotificacionesFake`).

## Arquitectura
El proyecto sigue una arquitectura en capas:
- Controller: endpoints REST
- DTO: objetos de entrada y salida de la API
- Enums: estados de viaje, reserva, divisa y notificación
- Mapper: conversión entre entidades y DTOs
- Model: entidades 
- Repository: acceso a base de datos
- Service: lógica de negocio

## Requisitos
- Java 17 o superior

## Ejecución
Compilar y ejecutar con Maven:
```
./mvnw spring-boot:run
```

### Para acceder a la interfaz de Swagger: http://localhost:8080/swagger-ui/index.html

### Para acceder a la base de datos H2: http://localhost:8080/h2-console
- JDBC URL `jdbc:h2:mem:grupoidb`
- User Name `sa`
- Sin contraseña

## Tests
Compilar y ejecutar las pruebas automatizadas con:
```
./mvnw test
```

El reporte de JaCoCo se almacena en: target/site/jacoco/index.html

## Tecnologías usadas
- Java 17
- Spring Boot
- Spring Data JPA
- H2
- Swagger 
- JUnit
- REST Assured
- JaCoCo
- Maven
- MapStruct