package com.switchfully.eurder;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CustomerControllerTest {

    @LocalServerPort
    private int port;

    @Test
    public void createCustomerHappyPath() {
        String requestBody = "{\n" +
                "  \"firstName\": \"Stef\",\n" +
                "  \"lastName\": \"Bemindt\",\n" +
                "  \"email\": \"NoneAYaBussiness@something.com\",\n" +
                "  \"address\": \"Funstreet 21 2000 Non-Parking\",\n" +
                "  \"phoneNumber\": \"123456789\"}";

        given()
                .baseUri("http://localhost")
                .port(port)
                .header("Content-type", "application/json")
                .and()
                .body(requestBody)
                .when()
                .post("/customers")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract();
    }
}
