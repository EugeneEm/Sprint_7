package order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static java.net.HttpURLConnection.*;
import static org.hamcrest.Matchers.notNullValue;

public class OrderAssertions {

    @Step("Проверка успешного создания заказа")
    public int createSuccess(ValidatableResponse response) {
        return response.assertThat()
                .statusCode(HTTP_CREATED)
                .body("track", notNullValue())
                .extract()
                .path("track");
    }

    @Step("Проверка получения списка заказов")
    public void getOrdersSuccess(ValidatableResponse response) {
         response.assertThat()
                .statusCode(HTTP_OK)
                .body("orders", notNullValue());
    }

    @Step("Проверка неуспешного получения списка заказов курьера")
    public String getOrdersCourierFail(ValidatableResponse response) {
        return response.assertThat()
                .statusCode(HTTP_NOT_FOUND)
                .body("message", notNullValue())
                .extract()
                .path("message");
    }
}
