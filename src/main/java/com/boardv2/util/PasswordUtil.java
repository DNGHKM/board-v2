package com.boardv2.util;


import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    public static String encode(String raw) {
        return BCrypt.hashpw(raw, BCrypt.gensalt());
    }

    public static boolean matches(String raw, String hashed) {
        return BCrypt.checkpw(raw, hashed);
    }
}
