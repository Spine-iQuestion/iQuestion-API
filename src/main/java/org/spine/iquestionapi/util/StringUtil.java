package org.spine.iquestionapi.util;

public class StringUtil {
    public static String generateRandomString(int length){
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = (int) (chars.length() * Math.random());
            sb.append(chars.charAt(index));
        }
        return sb.toString();
    }

    public static boolean isValidEmail(String email){
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    public static boolean isSafePassword(String password) {
        if (password.length() < 8) {
            return false;
        }

        return true;
    }
}
