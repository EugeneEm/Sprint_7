package courier;

import constants.ScooterConst;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class CourierClient {

    @Step("Создание курьера")
    public ValidatableResponse courierCreate(Courier courier) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(ScooterConst.BASE_URL)
                .body(courier)
                .when()
                .post(ScooterConst.COURIER)
                .then().log().all();
    }

    @Step("Создание курьера без обязательного параметра")
    public ValidatableResponse courierCreateWithoutParam(Map<String, String> courierWithoutParam) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(ScooterConst.BASE_URL)
                .body(courierWithoutParam)
                .when()
                .post(ScooterConst.COURIER)
                .then().log().all();
    }

    @Step("Логин курьера в системе")
    public ValidatableResponse courierLogin(Credentials credentials) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(ScooterConst.BASE_URL)
                .body(credentials)
                .when()
                .post(ScooterConst.COURIER+"/login")
                .then().log().all();
    }

    @Step("Логин курьера без обязательного параметра")
    public ValidatableResponse courierLoginWithoutParam(Map<String, String> courierWithoutParam) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(ScooterConst.BASE_URL)
                .body(courierWithoutParam)
                .when()
                .post(ScooterConst.COURIER+"/login")
                .then().log().all();
    }

    @Step("Удаление курьера")
    public ValidatableResponse courierDelete(int  courierId) {
        String json = String.format("{\"id\": \"%d\"}", courierId);
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(ScooterConst.BASE_URL)
                .body(json)
                .when()
                .delete(ScooterConst.COURIER+"/"+courierId)
                .then().log().all();
    }
}
