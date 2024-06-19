package service;

import org.mindrot.jbcrypt.BCrypt;

public class Encryption {

    /**
     * Генерация хеша пароля
     * @param password
     * @return хэш пароля
     */
    public String encrypt(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    /**
     * Проверка, что введенный пароль совпадает с тем, что лежит в database
     * @param password
     * @param hashedPassword
     * @return результат сравнения - true or false
     */
    public boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}
