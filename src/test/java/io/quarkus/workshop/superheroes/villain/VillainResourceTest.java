package io.quarkus.workshop.superheroes.villain;

import io.quarkus.test.junit.QuarkusTest;

import io.quarkus.workshop.superheroes.villain.entity.Villain;
import io.restassured.common.mapper.TypeRef;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;


import java.util.List;

import static jakarta.ws.rs.core.HttpHeaders.ACCEPT;
import static jakarta.ws.rs.core.HttpHeaders.CONTENT_TYPE;
import static io.restassured.RestAssured.given;
import static java.util.Optional.empty;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.not;

import static org.jboss.resteasy.reactive.RestResponse.Status.CREATED;
import static org.jboss.resteasy.reactive.RestResponse.Status.OK;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class VillainResourceTest {

    private static final String JSON = "application/json;charset=UTF-8";
    private static final long villainIdTest = 28550;


    @Test
    void testUpVillainService() {
        given()
            .when().get("/api/villains/health")
            .then()
            .statusCode(200);
    }

    @Test
    void testVillainEndpointGetAll() {
        given()
            .when().get("/api/villains")
            .then()
            .statusCode(200)
            .body("", not(empty()))
            .body("size()", greaterThan(0))
            .body("id", everyItem(notNullValue()))
            .body("name", not(empty()));
    }


    @Test
    @Order(1)
    void testVillainEndpointPostVillain() {
        Villain villain = new Villain();
        villain.name = "QAA";
        villain.otherName = "TEST";
        villain.picture = "PATH://PIC.PNG";
        villain.powers = "TESTER";
        villain.level = 100;

        given()
            .body(villain)
            .header(CONTENT_TYPE, JSON)
            .header(ACCEPT, JSON)
            .when()
            .post("/api/villains")
            .then()
            .statusCode(CREATED.getStatusCode());
            //.extract().body().as(Villain.class);

    }


    /*@Test
    @Order(2)
    void testVillainEndpointGetById() {
        given().when().get("/api/villains/id/" + villainIdTest)
            .then()
            .statusCode(200)
            .body("", not(nullValue()))
            .body("id", equalTo(villainIdTest));
    }*/

    @Test
    @Order(3)
    void shouldUpdateQaVillain() {

        Villain villainUpdate = given().when().get("/api/villains/id/" + villainIdTest).getBody().as(Villain.class);
        assertNotNull(villainUpdate);

        villainUpdate.level = 200;

        Villain villanUpdate2 = given()
            .body(villainUpdate)
            .header(ACCEPT, JSON)
            .header(CONTENT_TYPE, JSON)
            .when()
            .put("api/villains")
            .then()
            .statusCode(200)
            .extract()
            .body()
            .as(Villain.class);

        assertNotNull(villanUpdate2);
        assertEquals(200, villanUpdate2.level);

    }

    /*@Test
    @Order(4)
    void testVillainEndpointDelete() {
        given().when().get("/api/villains/id/" + villainIdTest).then().statusCode(200);
        given().when().delete("/api/villains/id/" + villainIdTest).then().statusCode(405);
    }*/

    private TypeRef<List<Villain>> getVillainTypeRef() {
        return new TypeRef<List<Villain>>() {
            // Kept empty on purpose
        };
    }

    @Test
    void shouldPingOpenAPI() {
        given().header(ACCEPT, JSON).when().get("/q/openapi").then().statusCode(OK.getStatusCode());
    }

}
