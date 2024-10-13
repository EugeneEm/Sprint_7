import io.restassured.response.ValidatableResponse;
import order.OrderAssertions;
import order.OrderClient;
import org.junit.Test;
import io.qameta.allure.Description;

import static org.junit.Assert.assertEquals;


public class OrdersGetListTest {
    int courierId;
    OrderClient orderClient = new OrderClient();
    OrderAssertions orderAssertions = new OrderAssertions();

    @Test
    @Description("Получение списка заказов")
    public void getOrders() {
        ValidatableResponse ordersResponse = orderClient.orderGetList();
        orderAssertions.getOrdersSuccess(ordersResponse);
    }

    @Test
    @Description("Получение списка заказов с несуществующим id курьера")
    public void getOrdersCourier() {
        courierId = 13102024;
        ValidatableResponse ordersResponse = orderClient.orderGetListCourier(courierId);
        String message = orderAssertions.getOrdersCourierFail(ordersResponse);
        assertEquals("Курьер с идентификатором " + courierId + " не найден", message);
    }

}
