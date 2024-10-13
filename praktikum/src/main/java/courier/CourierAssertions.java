package courier;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static java.net.HttpURLConnection.*;
import static org.hamcrest.Matchers.*;

public class CourierAssertions {

    @Step("Проверка создания нового курьера")
    public void createSuccess(ValidatableResponse response) {
        response.assertThat()
                .statusCode(HTTP_CREATED)
                .body("ok", is(true));
    }

    @Step("Проверка логина курьера")
    public int loginSuccess(ValidatableResponse response) {
        return response.assertThat()
                .statusCode(HTTP_OK)
                .body("id", greaterThan(0))
                .extract()
                .path("id");
    }

    @Step("Проверка неуспешного создания курьера")
    public String createFail(ValidatableResponse response, int expectedCode) {
        return response.assertThat()
                .statusCode(expectedCode)
                .body("message", notNullValue())
                .extract()
                .path("message");
    }

    @Step("Проверка неуспешного логина курьера")
    public String loginFail(ValidatableResponse response, int expectedCode) {
        return response.assertThat()
                .statusCode(expectedCode)
                .body("message", notNullValue())
                .extract()
                .path("message");
    }

    @Step("Проверка удаления курьера")
    public void deleteSuccess(ValidatableResponse response) {
        response.assertThat()
                .statusCode(HTTP_OK)
                .body("ok", is(true));
    }
}
