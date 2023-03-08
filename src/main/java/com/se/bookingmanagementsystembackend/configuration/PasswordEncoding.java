package com.se.bookingmanagementsystembackend.configuration;
import org.mindrot.jbcrypt.BCrypt;

public abstract class PasswordEncoding {
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean verifyPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }

    public abstract String encode(String password);

    public abstract boolean matches(String password, String hashedPassword);
}
