package com.switchfully.eurder;

import com.switchfully.eurder.domain.user.CountryCode;
import com.switchfully.eurder.domain.user.Phonenumber;
import com.switchfully.eurder.domain.user.address.Address;
import com.switchfully.eurder.domain.user.address.PostalCode;
import com.switchfully.eurder.service.dto.customer.ShowCustomerDTO;
import com.switchfully.eurder.domain.user.Customer;
import com.switchfully.eurder.domain.repository.CustomerRepository;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

class CustomerControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private CustomerRepository customerRepository;

    String requestBody =
            """
                     {
                       "firstName": "Stuf",
                       "lastName": "Bemundt",
                       "email": "stuf@switchfully.be",
                       "address": {
                         "streetName": "Tofstraat",
                         "houseNumber": "1",
                         "postalCode": {
                           "postalCode": "2180",
                           "city": "Ekeren"
                         },
                         "country": "Belgium"
                       },
                       "phoneNumber": {
                         "phone_body": "456789123",
                         "countryCode": "BELGIUM"
                       },
                       "password": "geheimpjeuh"
                     }
                    """;

    String requestBodyNullField =
            """
                     {
                       "firstName": "Stuf",
                       "lastName": "Bemundt",
                       "email": "",
                       "address": {
                         "streetName": "Tofstraat",
                         "houseNumber": "1",
                         "postalCode": {
                           "postalCode": "2180",
                           "city": "Ekeren"
                         },
                         "country": "Belgium"
                       },
                       "phoneNumber": {
                         "phone_body": "456789123",
                         "countryCode": "BELGIUM"
                       },
                       "password": "geheimpjeuh"
                     }
                    """;

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void createCustomerHappyPath() {

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

        Assertions.assertThat(customerRepository.findById(4L).isPresent());
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
        assertEquals("Email can not be empty!", response.jsonPath().getString("message"));

    }

    @Test
    public void createSameCustomerShowsErrorMessage() {
        customerRepository.save(new Customer("Stuf", "Bemundt", "stuf@switchfully.be", new Address("straat", "15", new PostalCode("2180", "Ekeren"), "Belgium"), new Phonenumber("123456789", CountryCode.BELGIUM), "password"));

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
    }

    @Test
    public void getAllCustomersHappyPath() {

        ShowCustomerDTO[] response = given()
                .baseUri("http://localhost")
                .port(port)
                .auth()
                .preemptive()
                .basic("stef@switchfully.com", "admin")
                .header("Accept", ContentType.JSON.getAcceptHeader())
                .header("Content-type", "application/json")
                .when()
                .get("/customers")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract().as(ShowCustomerDTO[].class);
        assertEquals(response.length, customerRepository.findAll().size());

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
