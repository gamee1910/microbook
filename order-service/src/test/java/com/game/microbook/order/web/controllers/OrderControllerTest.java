package com.game.microbook.order.web.controllers;

import com.game.microbook.order.AbstractIntegrationTest;
import com.game.microbook.order.domain.model.CreateOrderRequest;
import com.game.microbook.order.testdata.TestDataFactory;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.hamcrest.Matchers.notNullValue;

class OrderControllerTest extends AbstractIntegrationTest {

    @Nested
    class CreateOrdersTests {
        @Test
        void shouldCreateOrderSuccessfully() {
            String payload =
                    """
                            {
                              "items": [
                                {
                                  "code": "P100",
                                  "name": "Computer",
                                  "price": 6700,
                                  "quantity": 1
                                }
                              ],
                              "customer": {
                                "email": "hhhau1910@gmail.com",
                                "name": "game",
                                "phone": "0987654321"
                              },
                              "deliveryAddress": {
                                "addressLine1": "ABC",
                                "city": "Ho Chi Minh",
                                "country": "Viet Nam",
                                "state": "Thu Duc",
                                "zipCode": "70000",
                                "addressLine2": "none"
                              }
                            }
                            """;
            RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(payload)
                    .when()
                    .post("/api/orders")
                    .then()
                    .statusCode(HttpStatus.CREATED.value())
                    .body("orderNumber", notNullValue());
        }

        @Test
        void shouldReturnBadRequestWhenMissingMandatoryFields() {
            CreateOrderRequest request = TestDataFactory.createOrderRequestWithInvalidCustomer();
            RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(request)
                    .when()
                    .post("/api/orders")
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }
    }
}
