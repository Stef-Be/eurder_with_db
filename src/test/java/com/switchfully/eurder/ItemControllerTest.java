package com.switchfully.eurder;

import com.switchfully.eurder.api.dto.PrintItemDTO;
import com.switchfully.eurder.api.dto.PrintOrderDTO;
import com.switchfully.eurder.domain.repository.CustomerRepository;
import com.switchfully.eurder.domain.repository.ItemRepository;
import com.switchfully.eurder.domain.user.Customer;
import com.switchfully.eurder.domain.user.Role;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Set;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ItemControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ItemRepository itemRepository;

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
    Customer adminSteve = new Customer("Steve", "The Chief", "admin@eurder.com", "boeien", "moetni", "password");

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
        assertEquals("Price can not be zero!", response.jsonPath().getString("message"));
        ;
    }

    @Test
    void orderItemsHappyPath() {
        String itemId = itemRepository.getItems().get(0).getId();
        String orderBody = "{\n" +
                "  \"itemGroupDTOList\": [\n" +
                "    {\n" +
                "      \"itemId\": \"" + itemId + "\",\n" +
                "      \"amount\": 5\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        PrintOrderDTO response = given()
                .baseUri("http://localhost")
                .port(port)
                .auth()
                .preemptive()
                .basic("customer@eurder.com", "password")
                .header("Accept", ContentType.JSON.getAcceptHeader())
                .header("Content-type", "application/json")
                .and()
                .body(orderBody)
                .when()
                .post("/items/order")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract().as(PrintOrderDTO.class);

        Assertions.assertEquals(response.getFinalPrice(), "Final price: "+itemRepository.getItem(itemId).getPrice()*5);
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
                .post("/items/order")
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value())
                .extract();
    }


}