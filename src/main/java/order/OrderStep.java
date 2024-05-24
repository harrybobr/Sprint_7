package order;

import static constans.ApiConstants.CANCEL_ORDER;
import static constans.ApiConstants.CREATE_ORDER;
import static constans.ApiConstants.ORDERS_LIST;
import static constans.ApiConstants.SCOOTER_URL;
import static io.restassured.RestAssured.given;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class OrderStep {
  public static RequestSpecification requestSpec(){
    return given()
        .log()
        .all()
        .contentType(ContentType.JSON)
        .baseUri(SCOOTER_URL);
  }

  @Step("Создание заказа")
  public ValidatableResponse createOrder(Order order){
    return requestSpec()
        .when()
        .post(CREATE_ORDER)
        .then();
  }

  @Step("Отмена заказа")
  public ValidatableResponse cancelOrder(int track){
    return requestSpec()
        .body(track)
        .when()
        .put(CANCEL_ORDER)
        .then();
  }

  @Step("Получение списка заказов")
  public ValidatableResponse getOrdersList(){
    return requestSpec()
        .when()
        .get(ORDERS_LIST)
        .then();
  }
}
