package com.game.microbook.order.web.controllers;

import static org.hamcrest.Matchers.notNullValue;

import com.game.microbook.order.AbstractIntegrationTest;
import com.game.microbook.order.domain.model.CreateOrderRequest;
import com.game.microbook.order.testdata.TestDataFactory;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.math.BigDecimal;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class OrderControllerTest extends AbstractIntegrationTest {

    @Nested
    class CreateOrdersTests {
        @Test
        void shouldCreateOrderSuccessfully() {
            mockGetProductByCode("P100", "Computer", new BigDecimal("43"));
            String payload =
                    """
                            {
                              "items": [
                                {
                                  "code": "P100",
                                  "name": "Computer",
                                  "price": 43,
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
                                "addressLine2": "none",
                                "city": "Ho Chi Minh",
                                "state": "Thu Duc",
                                "zipCode": "70000",
                                "country": "Viet Nam"
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
