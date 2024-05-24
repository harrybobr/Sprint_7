package order;

import static org.hamcrest.CoreMatchers.notNullValue;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;

public class OrderAsserts {
  @Step("Получение списка заказов")
  public void getOrdersList(ValidatableResponse response){
    response.assertThat()
        .statusCode(HttpStatus.SC_OK)
        .body("orders", notNullValue());
  }
}
