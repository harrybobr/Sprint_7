
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import order.*;
import org.junit.Before;
import org.junit.Test;

public class OrderListTest {
  private OrderStep orderStep;
  private OrderAsserts orderAsserts = new OrderAsserts();

  @Before
  public void setUp(){
    orderStep = new OrderStep();
  }

  @Test
  @DisplayName("Проверка получения списка заказов")
  public void testGetOrdersList(){
    ValidatableResponse response = orderStep.getOrdersList();
    orderAsserts.getOrdersList(response);
  }
}
