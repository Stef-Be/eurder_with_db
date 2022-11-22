package com.switchfully.eurder;

import com.switchfully.eurder.service.dto.customer.ShowCustomerDTO;
import com.switchfully.eurder.api.mapper.CustomerMapper;
import com.switchfully.eurder.domain.user.Customer;
import com.switchfully.eurder.domain.repository.CustomerRepository;
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
class CustomerControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerMapper customerMapper;

    String requestBody =
            """
                    \"firstName\": \"Stef\",
                    \"lastName\": \"Bemindt\",
                    \"email\": \"NoneAYaBussiness@something.com\",
                    \"address\": \"Funstreet 21 2000 Non-Parking\",
                    \"phoneNumber\": \"123456789\"}";
                    """;

    String requestBodyNullField = "{\n" +
            "  \"firstName\": \"Stef\",\n" +
            "  \"lastName\": \"Bemindt\",\n" +
            "  \"email\": \"NoneAYaBussiness@something.com\",\n" +
            "  \"address\": \"\",\n" +
            "  \"phoneNumber\": \"123456789\"}";

    @Test
    public void createCustomerHappyPath() {
        /* WITHOUT DATABASE
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
         */
    }

    @Test
    public void createCustomerWithEmptyField() {
        /* WITHOUT DATABASE

        Response response = given()
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
                .extract().response();
        assertEquals("Address can not be empty!", response.jsonPath().getString("message"));

         */
    }

    @Test
    public void createSameCustomerShowsErrorMessage() {
        /* WITHOUT DATABASE

        customerRepository.addNewCustomer(new Customer("Stef", "Bemindt", "NoneAYaBussiness@something.com", "indifferent", "doesn't matter", "pass"));

        Response response = given()
                .baseUri("http://localhost")
                .port(port)
                .header("Content-type", "application/json")
                .and()
                .body(requestBody)
                .when()
                .post("/customers")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .extract().response();

        assertEquals("You are already a customer!", response.jsonPath().getString("message"));

         */
    }

    @Test
    public void getAllCustomersHappyPath() {
        /* WITHOUT DATABASE
        customerRepository.addNewCustomer(new Customer("Stef", "Bemindt", "NoneAYaBussiness@something.com", "indifferent", "doesn't matter","pass"));

        customerRepository.addNewCustomer(new Customer("Stefke", "Bemendt", "NoneAYaBussiness@something.com", "indifferent", "doesn't matter","pass"));

        ShowCustomerDTO[] response = given()
                .baseUri("http://localhost")
                .port(port)
                .auth()
                .preemptive()
                .basic("admin@eurder.com", "password")
                .header("Accept", ContentType.JSON.getAcceptHeader())
                .header("Content-type", "application/json")
                .when()
                .get("/customers")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract().as(ShowCustomerDTO[].class);
        assertEquals(response.length, customerRepository.getAllCustomers().size());

         */
    }

    @Test
    public void getExactCustomerHappyPath() {
        /* WITHOUT DATABASE
        customerRepository.addNewCustomer(new Customer("Stef", "Bemindt", "NoneAYaBussiness@something.com", "indifferent", "doesn't matter","pass"));

        customerRepository.addNewCustomer(new Customer("Stefke", "Bemendt", "NoneAYaBussiness@something.com", "indifferent", "doesn't matter","pass"));

        Customer customerToFind = customerRepository.getAllCustomers().stream().findFirst().orElseThrow();

        ShowCustomerDTO response = given()
                .baseUri("http://localhost")
                .port(port)
                .auth()
                .preemptive()
                .basic("admin@eurder.com", "password")
                .header("Accept", ContentType.JSON.getAcceptHeader())
                .header("Content-type", "application/json")
                .when()
                .get("/customers/" + customerToFind.getId())
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract().as(ShowCustomerDTO.class);
        assertEquals(customerToFind, customerMapper.mapToCustomerToShow(response));

         */
    }
}
