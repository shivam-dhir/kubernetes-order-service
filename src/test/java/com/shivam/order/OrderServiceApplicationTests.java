package com.shivam.order;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.testcontainers.containers.MySQLContainer;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderServiceApplicationTests {

    @LocalServerPort
    private Integer port;

    static MySQLContainer mySQLContainer = new MySQLContainer("mysql:8.3.0");

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    static {
        mySQLContainer.start();
    }

    @Test
    void testOrderCreation(){
        String request = """
                {
                    "skuCode": "phone",
                    "price": "100",
                    "quantity": "1"
                }
                """;
        RestAssured.given()
                .contentType("application/json")
                .body(request)
                .when()
                .post("/api/order")
                .then()
                .statusCode(201);
    }

}
