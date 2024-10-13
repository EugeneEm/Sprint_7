import constants.ScooterConst;
import courier.*;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;

import java.util.Map;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_NOT_FOUND;
import static org.junit.Assert.assertEquals;

public class CourierLoginTest {

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
    @Description("Успешный логин нового курьера")
    public void courierCreate() {
        Courier courier = new CourierGenerator().random();
        ValidatableResponse creationResponse = courierClient.courierCreate(courier);
        courierAssertions.createSuccess(creationResponse);

        Credentials credentials = new Credentials(courier);
        ValidatableResponse loginResponse = courierClient.courierLogin(credentials);
        courierId = courierAssertions.loginSuccess(loginResponse);
    }

    @Test
    @Description("Логин с пустым паролем (400 Bad Request)")
    // Тест падает, тк сервер возвращает 504, хотя по документации должен 400
    public void courierLoginWithNullPass() {
        Courier courier = new CourierGenerator().generic();
        courier.setPassword(null);
        Credentials credentials = new Credentials(courier);
        ValidatableResponse loginResponse = courierClient.courierLogin(credentials);
        String message = courierAssertions.loginFail(loginResponse, HTTP_BAD_REQUEST);
        assertEquals(ScooterConst.LOGIN_BAD_REQUEST_MSG, message);
    }

    @Test
    @Description("Логин без пароля (400 Bad Request)")
    // Тест падает, тк сервер возвращает 504, хотя по документации должен 400
    public void courierLoginWithoutPass() {
        Courier courier = new CourierGenerator().generic();
        ValidatableResponse loginResponse = courierClient.courierLoginWithoutParam(Map.of("login", courier.getLogin()));
        String message = courierAssertions.loginFail(loginResponse, HTTP_BAD_REQUEST);
        assertEquals(ScooterConst.LOGIN_BAD_REQUEST_MSG, message);
    }

    @Test
    @Description("Логин с несуществующей парой логин-пароль (404 Not Found)")
    public void courierLoginNotExisting() {
        Courier courier = new CourierGenerator().random();
        Credentials credentials = new Credentials(courier);
        ValidatableResponse loginResponse = courierClient.courierLogin(credentials);
        String message = courierAssertions.loginFail(loginResponse, HTTP_NOT_FOUND);
        assertEquals(ScooterConst.LOGIN_NOT_FOUND_MSG, message);
    }
}
