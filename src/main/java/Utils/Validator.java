package Utils;

import exception.InvalidParameterException;
import exception.NotFoundException;

public final class Validator {
    public static void isEmptyLogin(String parameter) {
        if(parameter.isEmpty()) {
            throw new NotFoundException("Логин не должен быть пустым");
        }
    }

    public static void passwordIsValid(String password, String repeatPassword) {
        if (password.length() < 3) {
            throw new InvalidParameterException("Пароли слишком короткий, добавьте еще символов!");
        }
        if (!password.equals(repeatPassword)) {
            throw new InvalidParameterException("Пароли не совпадают, попробуйте еще раз!");
        }
    }
}
