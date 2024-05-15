import courier.*;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CreateCourierTest {
  int courierId;
  private CourierRandom courierRandom = new CourierRandom();
  private Courier courier;
  private CourierStep courierStep;
  private CourierAsserts courierAsserts;

  @Before
  @Step("Создание тестовых данных для курьера")
  public void setUp(){
    courierStep = new CourierStep();
    courier = courierRandom.createRandomCourier();
    courierAsserts = new CourierAsserts();
  }

  @After
  @Step("Удаление созданного курьера")
  public void cleanData(){
    courierStep.deleteCourier(courierId);
  }

  @Test
  @DisplayName("Создание курьера")
  @Description("Проверка успешного создания курьера с корректными данными")
  public void createCourierTest(){
    ValidatableResponse responseCreateCourier = courierStep.createCourier(courier);
    CourierLogIn courierLogIn = CourierLogIn.from(courier);
    courierId = courierStep.authorizationCourier(courierLogIn).extract().path("id");
    courierAsserts.createCourierOk(responseCreateCourier);
  }

  @Test
  @DisplayName("Создание курьера без логина")
  @Description("Проверка ошибки при создании курьера с пустым полем логина")
  public void createCourierWithoutLoginTest(){
    courier.setLogin(null);
    ValidatableResponse response = courierStep.createCourier(courier);
    courierAsserts.createCourierWithoutCorrectData(response);
  }

  @Test
  @DisplayName("Создание курьера без пароля")
  @Description("Проверка ошибки при создании курьера с пустым полем пароля")
  public void createCourierWithoutPasswordTest(){
    courier.setPassword(null);
    ValidatableResponse response = courierStep.createCourier(courier);
    courierAsserts.createCourierWithoutCorrectData(response);
  }

  @Test
  @DisplayName("Создание курьера без логина и пароля")
  @Description("Проверка ошибки при создании курьера с пустыми полями логина и пароля")
  public void createCourierWithoutPasswordAndLoginTest(){
    courier.setPassword(null);
    courier.setLogin(null);
    ValidatableResponse response = courierStep.createCourier(courier);
    courierAsserts.createCourierWithoutCorrectData(response);
  }

  @Test
  @DisplayName("Создание курьера с существующим логином")
  @Description("Проверка ошибки валидации при создании одинаковых курьеров")
  public void createCourierWithSameLoginTest(){
    courierStep.createCourier(courier);
    ValidatableResponse response = courierStep.createCourier(courier);
    courierAsserts.createIdenticalCouriers(response);
  }
}
