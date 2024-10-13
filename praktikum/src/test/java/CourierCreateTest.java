import constants.ScooterConst;
import courier.*;
import io.qameta.allure.Step;
import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;

import java.util.Map;

import static java.net.HttpURLConnection.*;
import static org.junit.Assert.assertEquals;

public class CourierCreateTest {
    CourierClient courierClient = new CourierClient();
    CourierAssertions courierAssertions =  new CourierAssertions();
    private int courierId;

    @After
    @Step("Удаление созданного курьера")
    public void deleteCourier() {
        if (courierId > 0) {
            ValidatableResponse response = courierClient.courierDelete(courierId);
            courierAssertions.deleteSuccess(response);
        }
    }

    @Test
    @Description("Создание нового курьера")
    public void courierCreate() {
        Courier courier = new CourierGenerator().random();
        ValidatableResponse creationResponse = courierClient.courierCreate(courier);
        courierAssertions.createSuccess(creationResponse);

        Credentials credentials = new Credentials(courier);
        ValidatableResponse loginResponse = courierClient.courierLogin(credentials);
        courierId = courierAssertions.loginSuccess(loginResponse);
    }

    @Test
    @Description("Создание курьера без пароля (400 Bad Request)")
    public void  courierCreateWithoutPass() {
      Courier courier = new CourierGenerator().random();
      var courierWithoutPass = Map.of("login", courier.getLogin(), "firstName", courier.getFirstName());
      ValidatableResponse creationResponse = courierClient.courierCreateWithoutParam(courierWithoutPass);
      String message = courierAssertions.createFail(creationResponse, HTTP_BAD_REQUEST);
      assertEquals(ScooterConst.BAD_REQUEST_MSG, message);
    }

    @Test
    @Description("Создание курьера c пустым паролем (400 Bad Request)")
    public void courierCreateWithNullPass() {
        Courier courier = new CourierGenerator().random();
        courier.setPassword(null);
        ValidatableResponse creationResponse = courierClient.courierCreate(courier);
        String message = courierAssertions.createFail(creationResponse, HTTP_BAD_REQUEST);
        assertEquals(ScooterConst.BAD_REQUEST_MSG, message);
    }

    @Test
    @Description("Создание существующего курьера (409 Сonflict)")
    public void  courierCreateExisting() {
        Courier courier = new CourierGenerator().random();
        ValidatableResponse creationResponse = courierClient.courierCreate(courier);
        courierAssertions.createSuccess(creationResponse);
        ValidatableResponse creationDoubleResponse = courierClient.courierCreate(courier);
        String message = courierAssertions.createFail(creationDoubleResponse, HTTP_CONFLICT);
        assertEquals(ScooterConst.CONFLICT_MSG, message);

        Credentials credentials = new Credentials(courier);
        ValidatableResponse loginResponse = courierClient.courierLogin(credentials);
        courierId = courierAssertions.loginSuccess(loginResponse);
    }
}
