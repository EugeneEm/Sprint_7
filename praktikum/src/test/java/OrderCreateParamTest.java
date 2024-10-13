import com.google.gson.Gson;
import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import order.Order;
import order.OrderAssertions;
import order.OrderClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;


@RunWith(Parameterized.class)
public class OrderCreateParamTest {

    private Order order;

    OrderClient orderClient = new OrderClient();
    OrderAssertions orderAssertions = new OrderAssertions();
    Gson gson = new Gson();


    public OrderCreateParamTest(Order order){
       this.order = order;
   }

    @Parameterized.Parameters
    public static List<Object[]> testData() {
        return Arrays.asList(new Object[][] {
                { new Order("Luke", "Skywalker", "123 Main St", "Central", "+1234567890", "5", "2024-10-20", "Please deliver ASAP", Arrays.asList("BLACK", "GREY")) },
                { new Order("Obi-Wan", "Kenobi", "456 Elm St", "West End", "+0987654321", "3", "2024-10-22", "Leave at the door", Arrays.asList("GREY")) },
                { new Order("Padme", "Amidala", "789 Pine St", "East Side", "+1112223333", "7", "2024-10-25", "Ring the bell", null) }
        });
    }

    @Test
    @Description("Параметризовнный тест создания заказа")
    public void createOrder() {
        String json = gson.toJson(order);
        ValidatableResponse createOrderResponse = orderClient.orderCreate(json);
        int orderTrack = orderAssertions.createSuccess(createOrderResponse);
        assertTrue(orderTrack>0);
    }
}
