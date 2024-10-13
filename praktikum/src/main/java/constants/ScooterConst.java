package constants;

public class ScooterConst {
    public static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";
    public static final String COURIER = "/api/v1/courier";
    public static final String ORDER = "/api/v1/orders";

    //Courier
    public static String BAD_REQUEST_MSG = "Недостаточно данных для создания учетной записи";
    public static String CONFLICT_MSG = "Этот логин уже используется. Попробуйте другой.";
    public static String LOGIN_BAD_REQUEST_MSG = "Недостаточно данных для входа";
    public static String LOGIN_NOT_FOUND_MSG = "Учетная запись не найдена";
}
