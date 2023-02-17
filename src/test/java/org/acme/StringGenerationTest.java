package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
class StringGenerationTest {

    @Test
    void testMutinyStringGeneration() {
        given()
                .when().get("/mutiny/random/2")
                .then()
                .statusCode(200);
    }

    @Test
    void testVertxStringGeneration() {
        given()
                .when().get("/vertx/random/2")
                .then()
                .statusCode(200);
    }

}
