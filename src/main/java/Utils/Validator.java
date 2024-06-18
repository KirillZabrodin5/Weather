package Utils;

public final class Validator {
    public static void equalsPassword(String password, String repeatPassword) {
        if (!password.equals(repeatPassword)) {
            throw new RuntimeException("Пароли не совпадают, попробуйте еще раз!");
        }
    }
}
