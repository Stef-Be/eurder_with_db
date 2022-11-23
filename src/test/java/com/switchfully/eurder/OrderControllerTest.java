package com.switchfully.eurder;

import com.switchfully.eurder.service.dto.order.PrintOrderDTO;
import com.switchfully.eurder.domain.repository.ItemRepository;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class OrderControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ItemRepository itemRepository;

    String requestBody = "{\n" +
            "  \"name\": \"Screw\",\n" +
            "  \"description\": \"Something to make stuff fixed\",\n" +
            "  \"price\": 5.9,\n" +
            "  \"amount\": 5}";

    @Test
    void orderItemsHappyPath() {
        /* String orderBody = "{\n" +
                "  \"itemGroupDTOList\": [\n" +
                "    {\n" +
                "      \"itemId\": 1\",\n" +
                "      \"amount\": 5\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        PrintOrderDTO response = given()
                .baseUri("http://localhost")
                .port(port)
                .auth()
                .preemptive()
                .basic("staf@switchfully.com", "customer1")
                .header("Accept", ContentType.JSON.getAcceptHeader())
                .header("Content-type", "application/json")
                .and()
                .body(orderBody)
                .when()
                .post("/orders")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract().as(PrintOrderDTO.class);

        Assertions.assertEquals(response.getFinalPrice(), "Final price: " + itemRepository.findById(1L).orElseThrow().getPrice()*5);

         */
    }

    @Test
    void orderItemsUnauthorized() {
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
                .post("/orders")
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value())
                .extract();
    }
}