package com.switchfully.eurder;

import com.switchfully.eurder.domain.repository.ItemGroupRepository;
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

import java.time.LocalDate;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemGroupRepository itemGroupRepository;
        String orderBody = """
                {
                  "customerId": 2,
                  "itemGroupDTOList": [
                    {
                      "itemId": 1,
                      "amount": 10
                    }
                  ]
                }""";
    @Test
    void orderItemsHappyPath() {

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

        Assertions.assertEquals(response.getFinalPrice(), "Final price: " + itemRepository.findById(1L).orElseThrow().getPrice() * 10);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void orderItems_whenInStock_shippingDateTomorrow() {

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

        Assertions.assertEquals(itemGroupRepository.findById(4L).orElseThrow().getShippingDate(), LocalDate.now().plusDays(1));
    }

    @Test
    void orderItems_whenInsufficientStock_shippingDateTomorrow() {

        String orderBodyBig = """
                {
                  "customerId": 2,
                  "itemGroupDTOList": [
                    {
                      "itemId": 1,
                      "amount": 101
                    }
                  ]
                }""";

        PrintOrderDTO response = given()
                .baseUri("http://localhost")
                .port(port)
                .auth()
                .preemptive()
                .basic("staf@switchfully.com", "customer1")
                .header("Accept", ContentType.JSON.getAcceptHeader())
                .header("Content-type", "application/json")
                .and()
                .body(orderBodyBig)
                .when()
                .post("/orders")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract().as(PrintOrderDTO.class);


        Assertions.assertEquals(itemGroupRepository.findById(4L).orElseThrow().getShippingDate(), LocalDate.now().plusDays(7));
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
                .body(orderBody)
                .when()
                .post("/orders")
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value())
                .extract();
    }
}