import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import java.util.List;
import order.*;
import order.OrderStep;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class ParametrizedOrderTest {
  private final List<String> colour;
  private OrderStep orderStep;
  int track;

  public ParametrizedOrderTest(List<String> colour) {
    this.colour = colour;
  }

  @Before
  public void setUp(){
    orderStep = new OrderStep();
  }

  @After
  public void cleanData(){
    orderStep.cancelOrder(track);
  }

  @Parameterized.Parameters
  public static Object[][] selectScooterColour(){
    return new Object[][]{
        {List.of("BLACK")},
        {List.of("BLACK","GREY")},
        {List.of("GREY")},
        {List.of()}
    };
  }

  @Test
  @DisplayName("Успешное создание нового заказа")
  @Description("Проверка создания заказов с разными цветами")
  public void createOrderDifferentColours(){
    Order order = new Order(colour);
    ValidatableResponse response = orderStep.createOrder(order);
    track = response.extract().path("track");
    response.assertThat().statusCode(201).body("track", is(notNullValue()));
  }
}
