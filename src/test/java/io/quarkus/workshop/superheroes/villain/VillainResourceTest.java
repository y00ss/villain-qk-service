package io.quarkus.workshop.superheroes.villain;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class VillainResourceTest {
    @Test
    void testHelloEndpoint() {
        given()
          .when().get("/api/villains")
          .then()
             .statusCode(200)
             .body(is("Hello from Lodz\n"));
    }

}
