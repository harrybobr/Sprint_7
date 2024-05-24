import courier.*;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LoginCourierTest {
  int courierId;
  private CourierRandom courierRandom = new CourierRandom();
  private Courier courier;
  private CourierStep courierStep;
  private CourierAsserts courierAsserts;
  private CourierLogIn courierLogIn;

  @Before
  @Step("Создание тестовых данных для курьера")
  public void setUp(){
    courierStep = new CourierStep();
    courier = courierRandom.createRandomCourier();
    courierStep.createCourier(courier);
    courierLogIn = CourierLogIn.from(courier);
    courierAsserts = new CourierAsserts();
  }

  @After
  @Step("Удаление созданного курьера")
  public void cleanData(){
    courierStep.deleteCourier(courierId);
  }

  @Test
  @DisplayName("Успешный логин курьера")
  @Description("Проверка логина с валидными данными")
  public void loginCourierTest(){
    ValidatableResponse responseLoginCourier = courierStep.authorizationCourier(courierLogIn);
    courierId = responseLoginCourier.extract().path("id");
    courierAsserts.loginCourierAndTakeId(responseLoginCourier);

  }

  @Test
  @DisplayName("Вход под курьером без данных")
  @Description("Проверка ошибки входа с пустыми полями логина и пароля")
  public void loginCourierMissingDataTest(){
    ValidatableResponse response = courierStep.authorizationCourier(new CourierLogIn("", ""));
    courierAsserts.loginCourierWithoutCredentials(response);
  }

  @Test
  @DisplayName("Вход под курьером с несуществующей парой логин-пароль")
  @Description("Проверка ошибки входа с не существующими данными")
  public void loginCourierNotExistDataTest(){
    ValidatableResponse response =
        courierStep.authorizationCourier(new CourierLogIn("newHere", "newHere"));
    courierAsserts.loginCourierWithIncorrectCredentials(response);
  }

  @Test
  @DisplayName("Вход под курьером без логина")
  @Description("Проверка ошибки входа когда поле логина пустое")
  public void loginCourierEmptyLoginFieldTest(){
    ValidatableResponse response =
        courierStep.authorizationCourier(new CourierLogIn("", courier.getPassword()));
    courierAsserts.loginCourierWithoutCredentials(response);
  }

  @Test
  @DisplayName("Вход под курьером без пароля")
  @Description("Проверка ошибки входа когда поле пароля пустое")
  public void loginCourierEmptyPasswordFieldTest(){
    ValidatableResponse response =
        courierStep.authorizationCourier(new CourierLogIn(courier.getLogin(), ""));
    courierAsserts.loginCourierWithoutCredentials(response);
  }
}
