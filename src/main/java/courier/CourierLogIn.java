package courier;

public class CourierLogIn {
  private String login;
  private String password;

  public CourierLogIn() {
  }

  public CourierLogIn(String login, String password) {
    this.login = login;
    this.password = password;
  }

  public static CourierLogIn from(Courier courier) {
    return new CourierLogIn(courier.getLogin(), courier.getPassword());
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
