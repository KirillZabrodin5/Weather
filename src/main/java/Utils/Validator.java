package Utils;

import exception.InvalidParameterException;
import exception.NotFoundException;
import service.Encryption;

public final class Validator {
    public static void isEmptyLogin(String parameter) {
        if (parameter.isEmpty()) {
            throw new NotFoundException("Логин не должен быть пустым");
        }
    }

    //todo 2 метода - один для шифрованного пароля, другой для обычного
    public static void passwordIsValid(String password, String hashedPassword) {
        Encryption encryption = new Encryption();
        if (password.length() < 3) {
            throw new InvalidParameterException("Пароль слишком короткий, добавьте еще символов!");
        }
        if (!password.equals(hashedPassword)) {
            throw new InvalidParameterException("Пароли не совпадают, попробуйте еще раз!");
        }
    }

    public static void passwordIsValidWithHash(String password, String hashedPassword) {
        Encryption encryption = new Encryption();
        if (password.length() < 3) {
            throw new InvalidParameterException("Пароль слишком короткий, добавьте еще символов!");
        }
        if (!encryption.checkPassword(password, hashedPassword)) {
            throw new InvalidParameterException("Пароли не совпадают, попробуйте еще раз!");
        }
    }
}
