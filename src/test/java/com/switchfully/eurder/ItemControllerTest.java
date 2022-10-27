package com.switchfully.eurder;

import com.switchfully.eurder.domain.repository.CustomerRepository;
import com.switchfully.eurder.domain.user.Customer;
import com.switchfully.eurder.domain.user.Role;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ItemControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
            private CustomerRepository customerRepository;

    String requestBody = "{\n" +
            "  \"name\": \"Screw\",\n" +
            "  \"description\": \"Something to make stuff fixed\",\n" +
            "  \"price\": 5.9,\n" +
            "  \"amount\": 5}";

    String requestBodyZeroField = "{\n" +
            "  \"name\": \"Screw\",\n" +
            "  \"description\": \"Something to make stuff fixed\",\n" +
            "  \"price\": 0,\n" +
            "  \"amount\": 5}";
    Customer adminSteve = new Customer("Steve","The Chief", "admin@eurder.com", "boeien", "moetni", "password");


    @Test
    public void addItemHappyPath() {

        given()
                .baseUri("http://localhost")
                .port(port)
                .auth()
                .preemptive()
                .basic("admin@eurder.com", "password")
                .header("Accept", ContentType.JSON.getAcceptHeader())
                .header("Content-type", "application/json")
                .and()
                .body(requestBody)
                .when()
                .post("/items")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract();
    }

    @Test
    public void addItemWithZeroFieldShowsCorrectError() {

        Response response = given()
                .baseUri("http://localhost")
                .port(port)
                .auth()
                .preemptive()
                .basic("admin@eurder.com", "password")
                .header("Accept", ContentType.JSON.getAcceptHeader())
                .header("Content-type", "application/json")
                .and()
                .body(requestBodyZeroField)
                .when()
                .post("/items")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .extract().response();
        assertEquals("Price can not be zero!", response.jsonPath().getString("message"));;
    }


}