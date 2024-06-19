package Utils;

import exception.NotFoundException;

public final class Validator {
    public static void isEmptyLogin(String parameter) {
        if(parameter.isEmpty()) {
            throw new NotFoundException("Логин не должен быть пустым");
        }
    }

    public static void equalsPassword(String password, String repeatPassword) {
        if (!password.equals(repeatPassword)) {
            throw new RuntimeException("Пароли не совпадают, попробуйте еще раз!");
        }
    }
}
