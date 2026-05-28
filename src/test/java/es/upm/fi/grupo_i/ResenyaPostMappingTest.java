package es.upm.fi.grupo_i;

import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ResenyaPostMappingTest {
    // =========================================================================
    // @PostMapping("/usuarios/{usuario-id}/resenyas")
    //
    // PRECONDICIÓN: data.sql crea:
    //   - Viaje id=2 (FINALIZADO, conductor=1)
    //   - Reserva id=2 (viaje=2, pasajero=2, FINALIZADA)
    // Por tanto el usuario 2 puede reseñar el viaje 2.
    //
    // NOTA SOBRE EL CONTROLADOR: actualmente registrarResenya recibe los
    // parámetros como @RequestParam (no @RequestBody), por lo que se envían
    // como query params.
    // =========================================================================

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    // --- Casos VÁLIDOS ---

    /**
     * TC1 – Todos los parámetros válidos, con comentario.
     * Cubre: 1,5,9,14
     */
    @Test
    void postResenyas_TC1_todosValidos_conComentario() {
        given()
            .queryParam("viajeId", 2)
            .queryParam("puntuacion", 8)
            .queryParam("comentario", "Muy buen viaje")
        .when()
            .post("/usuarios/2/resenyas")
        .then()
        .statusCode(201);
    }

    /**
     * TC2 – Sin comentario (comentario vacío, clase válida 15).
     * Cubre: 1,5,9,15
     */
    @Test
    void postResenyas_TC2_sinComentario() {
        given()
            .queryParam("viajeId", 2)
            .queryParam("puntuacion", 5)
            .queryParam("comentario", "")
        .when()
            .post("/usuarios/2/resenyas")
        .then()
            .statusCode(201);
    }

    // --- Casos INVÁLIDOS ---

    /**
     * TC3 – viajeId no num (clase inválida 2).
     * Espera 400 Bad Request.
     */
    @Test
    void postResenyas_TC3_viajeIdNoNum() {
        given()
            .queryParam("viajeId", "dos")
            .queryParam("puntuacion", 7)
            .queryParam("comentario", "OK")
        .when()
            .post("/usuarios/2/resenyas")
        .then()
            .statusCode(400);
    }

    /**
     * TC4 – viajeId vacío (clase inválida 3).
     * Espera 400 Bad Request .
     */
    @Test
    void postResenyas_TC4_viajeIdVacio() {
        given()
            .queryParam("viajeId", (Integer) null)
            .queryParam("puntuacion", 7)
            .queryParam("comentario", "OK")
        .when()
            .post("/usuarios/2/resenyas")
        .then()
            .statusCode(400);
    }

    /**
     * TC5 – viajeId negativo o 0 (clase inválida 4).
     * Espera 400 Bad Request.
     */
    @Test
    void postResenyas_TC5_viajeIdNegativo() {
        given()
            .queryParam("viajeId", -1)
            .queryParam("puntuacion", 7)
            .queryParam("comentario", "OK")
        .when()
            .post("/usuarios/2/resenyas")
        .then()
            .statusCode(400);
    }

   
    /**
     * TC6 – puntuacion no numérica (clase inválida 6).
     * Espera 400 Bad Request
     */
    @Test
    void postResenyas_TC6_puntuacionNoNumerica() {
        given()
            .queryParam("viajeId", 2)
            .queryParam("puntuacion", "excelente")
            .queryParam("comentario", "OK")
        .when()
            .post("/usuarios/2/resenyas")
        .then()
            .statusCode(400);
    }

    /**
     * TC7 – puntuacion vacía / ausente (clase inválida 7).
     * Espera 400 Bad Request.
     */
    @Test
    void postResenyas_TC7_puntuacionVacia() {
        given()
            .queryParam("viajeId", 2)
            .queryParam("puntuacion", (String) null)
            .queryParam("comentario", "OK")
        .when()
            .post("/usuarios/2/resenyas")
        .then()
            .statusCode(400);
    }

    /**
     * TC8 – puntuacion negativa (clase inválida 8).
     * Espera 400 Bad Request.
     */
    @Test
    void postResenyas_TC8_puntuacionNegativa() {
        given()
            .queryParam("viajeId", 2)
            .queryParam("puntuacion", -1)
            .queryParam("comentario", "OK")
        .when()
            .post("/usuarios/2/resenyas")
        .then()
            .statusCode(400);
    }

    /**
     * TC9 – puntuacion mayor que 10 (clase inválida 9).
     * Espera 400 Bad Request.
     */
    @Test
    void postResenyas_TC9_puntuacionMayorDe10() {
        given()
            .queryParam("viajeId", 2)
            .queryParam("puntuacion", 11)
            .queryParam("comentario", "OK")
        .when()
            .post("/usuarios/2/resenyas")
        .then()
            .statusCode(400);
    }
}
