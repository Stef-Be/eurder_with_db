package com.switchfully.eurder;

import com.switchfully.eurder.domain.repository.ItemRepository;
import com.switchfully.eurder.service.dto.item.PrintItemDTO;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;


import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ItemControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ItemRepository itemRepository;

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


  String updateRequestBody = "{\n" +
          "  \"name\": \"Driver\",\n" +
          "  \"description\": \"Comes after screw\",\n" +
          "  \"price\": 15,\n" +
          "  \"amount\": 12}";

    @Test
    void addItemHappyPath() {

        given()
                .baseUri("http://localhost")
                .port(port)
                .auth()
                .preemptive()
                .basic("stef@switchfully.com", "admin")
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

        assertThat(itemRepository.findById(4L).isPresent());
    }

    @Test
    void addItem_asMember_unauthorized() {

        Response response = given()
                .baseUri("http://localhost")
                .port(port)
                .auth()
                .preemptive()
                .basic("staf@switchfully.com", "customer1")
                .header("Accept", ContentType.JSON.getAcceptHeader())
                .header("Content-type", "application/json")
                .and()
                .body(requestBody)
                .when()
                .post("/items")
                .then()
                .assertThat()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .extract().response();

        assertEquals("You do not have the authorization for this feature", response.jsonPath().getString("message"));
    }

    @Test
    void addItemWithZeroFieldShowsCorrectError() {

        Response response = given()
                .baseUri("http://localhost")
                .port(port)
                .auth()
                .preemptive()
                .basic("stef@switchfully.com", "admin")
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
        assertEquals("Price must be more than 0!", response.jsonPath().getString("message"));
        ;
    }

    @Test
    void getAllItemsHappyPath() {
        PrintItemDTO[] response = given()
                .baseUri("http://localhost")
                .port(port)
                .auth()
                .preemptive()
                .basic("stef@switchfully.com", "admin")
                .header("Accept", ContentType.JSON.getAcceptHeader())
                .header("Content-type", "application/json")
                .and()
                .body(requestBody)
                .when()
                .get("/items")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract().as(PrintItemDTO[].class);

        assertEquals(response.length, itemRepository.findAll().size());
    }

    @Test
    void updateItemHappyPath() {
        given()
                .baseUri("http://localhost")
                .port(port)
                .auth()
                .preemptive()
                .basic("stef@switchfully.com", "admin")
                .header("Accept", ContentType.JSON.getAcceptHeader())
                .header("Content-type", "application/json")
                .and()
                .body(updateRequestBody)
                .when()
                .put("/items/1")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract();
        Assertions.assertEquals(12, itemRepository.findById(1L).orElseThrow().getAmount() );
    }

    @Test
    void updateItem_asNonAdmin_Unauthorized() {
        Response response = given()
                .baseUri("http://localhost")
                .port(port)
                .auth()
                .preemptive()
                .basic("staf@switchfully.com", "customer1")
                .header("Accept", ContentType.JSON.getAcceptHeader())
                .header("Content-type", "application/json")
                .and()
                .body(updateRequestBody)
                .when()
                .put("/items/1")
                .then()
                .assertThat()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .extract().response();

        assertEquals("You do not have the authorization for this feature", response.jsonPath().getString("message"));
    }

        @Test
        void updateItemWithZeroField() {
            Response response = given()
                    .baseUri("http://localhost")
                    .port(port)
                    .auth()
                    .preemptive()
                    .basic("stef@switchfully.com", "admin")
                    .header("Accept", ContentType.JSON.getAcceptHeader())
                    .header("Content-type", "application/json")
                    .and()
                    .body(requestBodyZeroField)
                    .when()
                    .put("/items/1")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .extract().response();
            assertEquals("Price must be more than 0!", response.jsonPath().getString("message"));
    }
}