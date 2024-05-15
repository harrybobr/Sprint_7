package courier;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;

public class CourierAsserts {
  @Step("Создание курьера")
  public void createCourierOk(ValidatableResponse response){
    response
        .assertThat()
        .statusCode(HttpStatus.SC_CREATED)
        .body("ok", is(true));
  }
  @Step("Ошибка при создании двух одинаковых курьеров")
  public void createIdenticalCouriers(ValidatableResponse response){
    response
        .assertThat()
        .statusCode(HttpStatus.SC_CONFLICT)
        .body("message", equalTo("Этот логин уже используется"));
  }
  @Step("Создание курьера без обязательных полей")
  public void createCourierWithoutCorrectData(ValidatableResponse response){
    response
        .assertThat()
        .statusCode(HttpStatus.SC_BAD_REQUEST)
        .body("message", equalTo("Недостаточно данных для создания учетной записи"));
  }

  @Step("Логин курьера и получение его id")
  public void loginCourierAndTakeId(ValidatableResponse response){
    response
        .assertThat()
        .statusCode(HttpStatus.SC_OK)
        .body("id", greaterThan(0))
        .extract()
        .path("id");
  }

  @Step("Проверка авторизации под несуществующим пользователем")
  public void loginCourierWithIncorrectCredentials(ValidatableResponse response){
    response
        .assertThat()
        .statusCode(HttpStatus.SC_NOT_FOUND)
        .body("message", equalTo("Учетная запись не найдена"));
  }

  @Step("Проверка ответа сервера при попытке авторизации без логина и пароля")
  public void loginCourierWithoutCredentials(ValidatableResponse response){
    response
        .assertThat()
        .statusCode(HttpStatus.SC_BAD_REQUEST)
        .body("message", equalTo("Недостаточно данных для входа"));
  }
}
