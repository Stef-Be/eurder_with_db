package com.switchfully.eurder;

import com.switchfully.eurder.domain.repository.ItemRepository;
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
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
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
        assertEquals("Price must be more than 0!", response.jsonPath().getString("message"));
        ;
    }

    @Test
    void getAllItemsHappyPath() {
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
                .get("/items")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract();
    }

    @Test
    void updateItemHappyPath() {
        String itemId = itemRepository.getItems().get(0).getId();
        given()
                .baseUri("http://localhost")
                .port(port)
                .auth()
                .preemptive()
                .basic("admin@eurder.com", "password")
                .header("Accept", ContentType.JSON.getAcceptHeader())
                .header("Content-type", "application/json")
                .and()
                .body(updateRequestBody)
                .when()
                .put("/items/" + itemId)
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract();
        Assertions.assertEquals(itemRepository.getItems().get(0).getAmount(), 12);
    }

        @Test
        void updateItemWithZeroField() {
            String itemId = itemRepository.getItems().get(0).getId();
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
                    .put("/items/" + itemId)
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .extract().response();
            assertEquals("Price must be more than 0!", response.jsonPath().getString("message"));
    }
}