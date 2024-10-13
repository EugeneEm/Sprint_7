package order;

import constants.ScooterConst;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderClient {
    @Step("Создание заказа")
    public ValidatableResponse orderCreate(String json) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(ScooterConst.BASE_URL)
                .body(json)
                .when()
                .post(ScooterConst.ORDER)
                .then().log().all();
    }

    @Step("Получение списка заказов")
    public ValidatableResponse orderGetList() {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(ScooterConst.BASE_URL)
                .when()
                .get(ScooterConst.ORDER)
                .then().log().all();
    }

    @Step("Получение списка заказов курьера")
    public ValidatableResponse orderGetListCourier(int courierId) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(ScooterConst.BASE_URL)
                .queryParam("courierId", courierId)
                .when()
                .get(ScooterConst.ORDER)
                .then().log().all();
    }
}
