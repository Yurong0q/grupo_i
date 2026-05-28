package es.upm.fi.grupo_i;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;


/**
 * Casos de prueba RestAssured para:
 *   - @PostMapping("/viajes")
 *   - @PostMapping("/usuarios/{usuario-id}/resenyas")
 *
 * Basados en las clases de equivalencia definidas en Software_pruebas.xlsx.
 *
 * NOTAS DE CONFIGURACIÓN:
 *  - El pom.xml no incluye RestAssured. Añadir la dependencia:
 *      <dependency>
 *          <groupId>io.rest-assured</groupId>
 *          <artifactId>rest-assured</artifactId>
 *          <scope>test</scope>
 *      </dependency>
 *  - Para los tests de /resenyas se necesita que exista en la BD
 *    un viaje FINALIZADO (id=2) y una reserva FINALIZADA del usuario 2 en ese viaje
 *    (ya presentes en data.sql).
 *  - Los tests de clases inválidas que el controlador/servicio no puede
 *    rechazar por tipo (p.ej. tipo no numérico en JSON) devuelven 400
 *    por error de deserialización de Spring.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ViajePostMappingTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
        RestAssured.defaultParser = Parser.JSON;
    }

    // =========================================================================
    // @PostMapping("/viajes")
    // =========================================================================

    // --- Casos VÁLIDOS ---

    /**
     * TC1 – Clases válidas: todas las clases válidas con divisa EURO y paradas.
     * Cubre: 1,5,9,13,16,19,22,29,31,32
     */
    @Test
    void postViajes_TC1() {
        String body = """
            {
              "conductorId": 1,
              "origen": "Madrid",
              "destino": "Barcelona",
              "paradas": ["Zaragoza"],
              "plazasDisponibles": 3,
              "precio": { "cantidad": 25.0, "moneda": "EURO" },
              "fechaSalida": "2030-01-01",
              "horaSalida": "08:00:00",
              "duracionEstimada": 300,
            }
            """;
        given()
            .contentType("application/json")
            .body(body)
        .when()
            .post("/viajes")
        .then()
            .statusCode(201);
    }

    /**
     * TC2 – Paradas vacías, divisa LIBRA.
     * Cubre: 1,5,9,14(paradas vacío),16,19,23,29,31,32
     */
    @Test
    void postViajes_TC2_paradasVacias_LIBRA() {
        String body = """
            {
              "conductorId": 1,
              "origen": "Sevilla",
              "destino": "Málaga",
              "paradas": [],
              "plazasDisponibles": 2,
              "precio": { "cantidad": 15.0, "moneda": "LIBRA" },
              "fechaSalida": "2030-06-01",
              "horaSalida": "10:00:00",
              "duracionEstimada": 180
            }
            """;

        given()
            .contentType("application/json")
            .body(body)
        .when()
            .post("/viajes")
        .then()
            .statusCode(201);
    }

    /**
     * TC3 – Divisa DOLAR.
     * Cubre: 1,5,9,13,16,19,24,29,31,32
     */
    @Test
    void postViajes_TC3_divisaDOLAR() {
        String body = """
            {
              "conductorId": 2,
              "origen": "Valencia",
              "destino": "Bilbao",
              "paradas": ["Madrid"],
              "plazasDisponibles": 1,
              "precio": { "cantidad": 50.0, "moneda": "DOLAR" },
              "fechaSalida": "2030-07-10",
              "horaSalida": "09:00:00",
              "duracionEstimada": 420
            }
            """;
        given()
            .contentType("application/json")
            .body(body)
        .when()
            .post("/viajes")
        .then()
            .statusCode(201);
    }

    /**
     * TC4 – Divisa PESOS.
     * Cubre: 1,5,9,13,16,19,25,29,31,32
     */
    @Test
    void postViajes_TC4_divisaPESOS() {
        String body = """
            {
              "conductorId": 1,
              "origen": "Granada",
              "destino": "Almería",
              "paradas": ["Zaragoza"],
              "plazasDisponibles": 4,
              "precio": { "cantidad": 10.0, "moneda": "PESOS" },
              "fechaSalida": "2030-08-01",
              "horaSalida": "07:00:00",
              "duracionEstimada": 120
            }
            """;
        given()
            .contentType("application/json")
            .body(body)
        .when()
            .post("/viajes")
        .then()
            .statusCode(201);
    }

    /**
     * TC5 – Divisa YEN.
     * Cubre: 1,5,9,13,16,19,26,29,31,32
     */
    @Test
    void postViajes_TC5_divisaYEN() {
        String body = """
            {
              "conductorId": 2,
              "origen": "Córdoba",
              "destino": "Jaén",
              "paradas": ["Úbeda"],
              "plazasDisponibles": 2,
              "precio": { "cantidad": 8.0, "moneda": "YEN" },
              "fechaSalida": "2030-09-15",
              "horaSalida": "11:00:00",
              "duracionEstimada": 90
            }
            """;
        given()
            .contentType("application/json")
            .body(body)
        .when()
            .post("/viajes")
        .then()
            .statusCode(201);
    }

    // --- Casos INVÁLIDOS ---

    /**
     * TC6 – conductorId no numérico (clase inválida 2: Valor no numerico).
     * Espera 400 Bad Request.
     */
    @Test
        void postViajes_TC6_conductorIdNoNumerico(){
        String body = """
            {
              "conductorId": "abc",
              "origen": "Madrid",
              "destino": "Barcelona",
              "paradas": ["Zaragoza"],
              "plazasDisponibles": 2,
              "precio": { "cantidad": 25.0, "moneda": "EURO" },
              "fechaSalida": "2030-01-01",
              "horaSalida": "08:00:00",
              "duracionEstimada": 300
            }
            """;
        given()
            .contentType("application/json")
            .body(body)
        .when()
            .post("/viajes")
        .then()
            .statusCode(400);
        }


    /**
     * TC7 – conductorId vacio (clase inválida 3).
     * Espera 400 Bad Request.
     */
    @Test
    void postViajes_TC7_conductorIdVacio() {
        String body = """
            {
              "conductorId": null,
              "origen": "Madrid",
              "destino": "Barcelona",
              "paradas": ["Zaragoza"],
              "plazasDisponibles": 2,
              "precio": { "cantidad": 25.0, "moneda": "EURO" },
              "fechaSalida": "2030-01-01",
              "horaSalida": "08:00:00",
              "duracionEstimada": 300
            }
            """;
        given()
            .contentType(ContentType.JSON)
            .body(body)
        .when()
            .post("/viajes")
        .then()
            .statusCode(400);
        }

    /**
     * TC8 – conductorId negativo (clase inválida 4).
     * Espera 400 Bad Request.
     */
    @Test
    void postViajes_TC8_conductorIdNegativo() {
        String body = """
            {
              "conductorId": -5,
              "origen": "Madrid",
              "destino": "Barcelona",
              "paradas": [],
              "plazasDisponibles": 2,
              "precio": { "cantidad": 25.0, "moneda": "EURO" },
              "fechaSalida": "2030-01-01",
              "horaSalida": "08:00:00",
              "duracionEstimada": 300
            }
            """;
        given()
            .contentType(ContentType.JSON)
            .body(body)
        .when()
            .post("/viajes")
        .then()
            .statusCode(400);
    }

    /**
     * TC9/TC12 – origen distinto destino (clase inválida 6, 10).
     * Espera 400 Bad Request.
     */ 
    @Test
    void postViajes_TC9_origenigualdestino() {
        String body = """
            {
              "conductorId": -5,
              "origen": "Madrid",
              "destino": "Madrid",
              "paradas": [],
              "plazasDisponibles": 2,
              "precio": { "cantidad": 25.0, "moneda": "EURO" },
              "fechaSalida": "2030-01-01",
              "horaSalida": "08:00:00",
              "duracionEstimada": 300
            }
            """;
        given()
            .contentType(ContentType.JSON)
            .body(body)
        .when()
            .post("/viajes")
        .then()
            .statusCode(400);

    }
    /**
     * TC10 – origen vacío (clase inválida 7).
     * Espera 400 Bad Request.
     */
    @Test
    void postViajes_TC9_origenVacio() {
        String body = """
            {
              "conductorId": 1,
              "origen": "",
              "destino": "Barcelona",
              "paradas": [],
              "plazasDisponibles": 2,
              "precio": { "cantidad": 25.0, "moneda": "EURO" },
              "fechaSalida": "2030-01-01",
              "horaSalida": "08:00:00",
              "duracionEstimada": 300
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .body(body)
        .when()
            .post("/viajes")
        .then()
            .statusCode(400);
    }

    /**
     * TC11 – origen no alphanumerico (clase inválida 8)
     * Espera 400 Bad Request.
     */
    @Test
    void postViajes_TC11_origenNoAlphaNum() {
        String body = """
            {
              "conductorId": 1,
              "origen": "?",
              "destino": "Madrid",
              "paradas": [],
              "plazasDisponibles": 2,
              "precio": { "cantidad": 25.0, "moneda": "EURO" },
              "fechaSalida": "2030-01-01",
              "horaSalida": "08:00:00",
              "duracionEstimada": 300
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .body(body)
        .when()
            .post("/viajes")
        .then()
            .statusCode(400);
    }

    /**
     * TC13 – destino vacío (clase inválida 11).
     * Espera 400 Bad Request.
     */
    @Test
    void postViajes_TC13_destinoVacio() {
        String body = """
            {
              "conductorId": 1,
              "origen": "Madrid",
              "destino": "",
              "paradas": [],
              "plazasDisponibles": 2,
              "precio": { "cantidad": 25.0, "moneda": "EURO" },
              "fechaSalida": "2030-01-01",
              "horaSalida": "08:00:00",
              "duracionEstimada": 300
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .body(body)
        .when()
            .post("/viajes")
        .then()
            .statusCode(400);
    }

    /**
     * TC14 – destino no alpha (clase inválida 12).
     * Espera 400 Bad Request.
     */
    @Test
    void postViajes_TC14_destinoNoAlphaNum() {
        String body = """
            {
              "conductorId": 1,
              "origen": "Sevilla",
              "destino": "?",
              "paradas": [],
              "plazasDisponibles": 2,
              "precio": { "cantidad": 20.0, "moneda": "EURO" },
              "fechaSalida": "2030-01-01",
              "horaSalida": "08:00:00",
              "duracionEstimada": 120
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .body(body)
        .when()
            .post("/viajes")
        .then()
            .statusCode(400);
    }

    /**
     * TC15 – destino nulo / ausente (clase inválida 15).
     * Espera 400 Bad Request.
     */
    @Test
    void postViajes_TC15_paradasNoAlphaNum() {
        String body = """
            {
              "conductorId": 1,
              "origen": "Madrid",
              "paradas": ["?"],
              "plazasDisponibles": 2,
              "precio": { "cantidad": 25.0, "moneda": "EURO" },
              "fechaSalida": "2030-01-01",
              "horaSalida": "08:00:00",
              "duracionEstimada": 300
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .body(body)
        .when()
            .post("/viajes")
        .then()
            .statusCode(400);
    }

    /**
     * TC16 – plazasDisponibles no Num (clase inválida 17)
     * Espera 400 Bad Request.
     */
    @Test
    void postViajes_TC16_plazasNoNum() {
        String body = """
            {
              "conductorId": 1,
              "origen": "Madrid",
              "destino": "Barcelona",
              "paradas": [],
              "plazasDisponibles": "cuatro",
              "precio": { "cantidad": 25.0, "moneda": "EURO" },
              "fechaSalida": "2030-01-01",
              "horaSalida": "08:00:00",
              "duracionEstimada": 300
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .body(body)
        .when()
            .post("/viajes")
        .then()
            .statusCode(400);
    }

    /**
     * TC17 – plazasDisponibles negativo o cero (clase inválida 18).
     * Espera 400 Bad Request.
     */
    @Test
    void postViajes_TC17_plazasNegativas() {
        String body = """
            {
              "conductorId": 1,
              "origen": "Madrid",
              "destino": "Barcelona",
              "paradas": [],
              "plazasDisponibles": -3,
              "precio": { "cantidad": 25.0, "moneda": "EURO" },
              "fechaSalida": "2030-01-01",
              "horaSalida": "08:00:00",
              "duracionEstimada": 300
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .body(body)
        .when()
            .post("/viajes")
        .then()
            .statusCode(400);
    }

    /**
     * TC18 – precio nulo / ausente (clase inválida 20).
     * Espera 400 Bad Request.
     */
    @Test
    void postViajes_TC18_precioNulo() {
        String body = """
            {
              "conductorId": 1,
              "origen": "Madrid",
              "destino": "Barcelona",
              "paradas": [],
              "plazasDisponibles": 2,
              "precio": null,
              "fechaSalida": "2030-01-01",
              "horaSalida": "08:00:00",
              "duracionEstimada": 300
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .body(body)
        .when()
            .post("/viajes")
        .then()
            .statusCode(400);
    }

    /**
     * TC19 – precio.cantidad negativa (clase inválida 21).
     * Espera 400 Bad Request.
     */
    @Test
    void postViajes_TC19_precioCantidadNegativa() {
        String body = """
            {
              "conductorId": 1,
              "origen": "Madrid",
              "destino": "Barcelona",
              "paradas": [],
              "plazasDisponibles": 2,
              "precio": { "cantidad": -10.0, "moneda": "EURO" },
              "fechaSalida": "2030-01-01",
              "horaSalida": "08:00:00",
              "duracionEstimada": 300
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .body(body)
        .when()
            .post("/viajes")
        .then()
            .statusCode(400);
    }

    /**
     * TC20 – precio.moneda vacía / nula (clase inválida 27).
     * Espera 400 Bad Request (fallo de deserialización del enum).
     */
    @Test
    void postViajes_TC20_divisaVacia() {
        String body = """
            {
              "conductorId": 1,
              "origen": "Madrid",
              "destino": "Barcelona",
              "paradas": [],
              "plazasDisponibles": 2,
              "precio": { "cantidad": 25.0, "moneda": null },
              "fechaSalida": "2030-01-01",
              "horaSalida": "08:00:00",
              "duracionEstimada": 300
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .body(body)
        .when()
            .post("/viajes")
        .then()
            .statusCode(400);
    }

    /**
     * TC21 – precio.moneda con valor inválido (clase inválida 28).
     * Espera 400 Bad Request (enum desconocido).
     */
    @Test
    void postViajes_TC21_divisaInvalida() {
        String body = """
            {
              "conductorId": 1,
              "origen": "Madrid",
              "destino": "Barcelona",
              "paradas": [],
              "plazasDisponibles": 2,
              "precio": { "cantidad": 25.0, "moneda": "BITCOIN" },
              "fechaSalida": "2030-01-01",
              "horaSalida": "08:00:00",
              "duracionEstimada": 300
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .body(body)
        .when()
            .post("/viajes")
        .then()
            .statusCode(400);
    }

    /**
     * TC22 – fechaSalida en el pasado (clase inválida 30).
     * Espera 400 Bad Request.
     * NOTA: requiere que el servicio valide que fechaSalida >= hoy.
     *       Si no está implementado aún, este test fallará en verde (201).
     */
    @Test
    void postViajes_TC22_fechaSalidaEnPasado() {
        String body = """
            {
              "conductorId": 1,
              "origen": "Madrid",
              "destino": "Barcelona",
              "paradas": [],
              "plazasDisponibles": 2,
              "precio": { "cantidad": 25.0, "moneda": "EURO" },
              "fechaSalida": "2000-01-01",
              "horaSalida": "08:00:00",
              "duracionEstimada": 300
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .body(body)
        .when()
            .post("/viajes")
        .then()
            .statusCode(400);
    }

    /**
     * TC23 – duracionEstimada no num (clase inválida 33).
     * Espera 400 Bad Request.
     */
    @Test
    void postViajes_TC23_duracionNoNum() {
        String body = """
            {
              "conductorId": 1,
              "origen": "Madrid",
              "destino": "Barcelona",
              "paradas": [],
              "plazasDisponibles": 2,
              "precio": { "cantidad": 25.0, "moneda": "EURO" },
              "fechaSalida": "2030-01-01",
              "horaSalida": "08:00:00",
              "duracionEstimada": "cincuenta"
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .body(body)
        .when()
            .post("/viajes")
        .then()
            .statusCode(400);
    }

    
    /**
     * TC24 – duracionEstimada ausente/nula (clase inválida 34).
     * Espera 400 Bad Request (int primitivo → 0 → rechazado por la validación de servicio).
     */
    @Test
    void postViajes_TC24_duracionAusente() {
        String body = """
            {
              "conductorId": 1,
              "origen": "Madrid",
              "destino": "Barcelona",
              "paradas": [],
              "plazasDisponibles": 2,
              "precio": { "cantidad": 25.0, "moneda": "EURO" },
              "fechaSalida": "2030-01-01",
              "horaSalida": "08:00:00"
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .body(body)
        .when()
            .post("/viajes")
        .then()
            .statusCode(400);
    }

    /**
     * TC25 – duracionEstimada negativa (clase inválida 35).
     * Espera 400 Bad Request.
     */
    @Test
    void postViajes_TC25_duracionNegativa() {
        String body = """
            {
              "conductorId": 1,
              "origen": "Madrid",
              "destino": "Barcelona",
              "paradas": [],
              "plazasDisponibles": 2,
              "precio": { "cantidad": 25.0, "moneda": "EURO" },
              "fechaSalida": "2030-01-01",
              "horaSalida": "08:00:00",
              "duracionEstimada": -60
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .body(body)
        .when()
            .post("/viajes")
        .then()
            .statusCode(400);
    }
}