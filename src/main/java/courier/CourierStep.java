package courier;

import static constans.ApiConstants.CREATE_COURIER;
import static constans.ApiConstants.DELETE_COURIER;
import static constans.ApiConstants.LOGIN_COURIER;
import static constans.ApiConstants.SCOOTER_URL;
import static io.restassured.RestAssured.given;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class CourierStep {
  public static RequestSpecification requestSpec() {
    return given()
        .log()
        .all()
        .contentType(ContentType.JSON)
        .baseUri(SCOOTER_URL);
  }

  @Step("Регистрация нового курьера")
  public ValidatableResponse createCourier(Courier courier) {
    return requestSpec()
        .body(courier)
        .when()
        .post(CREATE_COURIER)
        .then();
  }
  @Step("Логин курьера")
  public ValidatableResponse authorizationCourier(CourierLogIn courier){
    return requestSpec()
        .body(courier)
        .when()
        .post(LOGIN_COURIER)
        .then();
  }
  @Step("Удаление курьера")
  public ValidatableResponse deleteCourier(int courierId){
    return requestSpec()
        .when()
        .delete(DELETE_COURIER + courierId)
        .then();
  }
}
