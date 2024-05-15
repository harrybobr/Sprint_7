package courier;

import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;

public class CourierRandom {
  @Step("Создание курьера с рандомными данными")
  public Courier createRandomCourier(){
    String login = RandomStringUtils.randomAlphabetic(10);
    String password = RandomStringUtils.randomAlphabetic(8);
    String firstName = RandomStringUtils.randomAlphabetic(8);

    return new Courier(login, password, firstName);
  }
}
