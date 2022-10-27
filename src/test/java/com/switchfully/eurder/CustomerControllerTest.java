package com.switchfully.eurder;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CustomerControllerTest {

    @LocalServerPort
    private int port;

    String requestBody = "{\n" +
            "  \"firstName\": \"Stef\",\n" +
            "  \"lastName\": \"Bemindt\",\n" +
            "  \"email\": \"NoneAYaBussiness@something.com\",\n" +
            "  \"address\": \"Funstreet 21 2000 Non-Parking\",\n" +
            "  \"phoneNumber\": \"123456789\"}";

    String requestBodyNullField = "{\n" +
            "  \"firstName\": \"Stef\",\n" +
            "  \"lastName\": \"Bemindt\",\n" +
            "  \"email\": \"NoneAYaBussiness@something.com\",\n" +
            "  \"address\": \"\",\n" +
            "  \"phoneNumber\": \"123456789\"}";

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

    @Test
    public void createCustomerWithEmptyField() {

        given()
                .baseUri("http://localhost")
                .port(port)
                .header("Content-type", "application/json")
                .and()
                .body(requestBodyNullField)
                .when()
                .post("/customers")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .extract();
    }
}
