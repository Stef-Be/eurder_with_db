package com.switchfully.eurder;

import com.switchfully.eurder.api.dto.CustomerDTO;
import com.switchfully.eurder.api.mapper.CustomerMapper;
import com.switchfully.eurder.domain.customer.Customer;
import com.switchfully.eurder.domain.repository.CustomerRepository;
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
    }

    @Test
    public void createSameCustomerShowsErrorMessage() {

        customerRepository.addNewCustomer(new Customer("Stef", "Bemindt", "NoneAYaBussiness@something.com", "indifferent", "doesn't matter"));

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

        assertEquals("This customer already exists!", response.jsonPath().getString("message"));
    }

    @Test
    public void getAllCustomersHappyPath() {
        customerRepository.addNewCustomer(new Customer("Stef", "Bemindt", "NoneAYaBussiness@something.com", "indifferent", "doesn't matter"));

        customerRepository.addNewCustomer(new Customer("Stefke", "Bemendt", "NoneAYaBussiness@something.com", "indifferent", "doesn't matter"));

        CustomerDTO[] response = given()
                .baseUri("http://localhost")
                .port(port)
                .header("Content-type", "application/json")
                .when()
                .get("/customers")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract().as(CustomerDTO[].class);
        assertEquals(response.length, 2);
    }

    @Test
    public void getExactCustomerHappyPath() {
        customerRepository.addNewCustomer(new Customer("Stef", "Bemindt", "NoneAYaBussiness@something.com", "indifferent", "doesn't matter"));

        customerRepository.addNewCustomer(new Customer("Stefke", "Bemendt", "NoneAYaBussiness@something.com", "indifferent", "doesn't matter"));

        Customer customerToFind = customerRepository.getAllCustomers().stream().findFirst().orElseThrow();

        CustomerDTO response = given()
                .baseUri("http://localhost")
                .port(port)
                .header("Content-type", "application/json")
                .when()
                .get("/customers/" + customerToFind.getId())
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract().as(CustomerDTO.class);
        assertEquals(customerToFind, customerMapper.mapToCustomer(response));
    }
}
