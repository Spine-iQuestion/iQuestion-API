package org.spine.iquestionapi.util;

/**
 * The class for string utilities
 */
public class StringUtil {
    /**
     * Generate a random string of a given length
     * @param length the length of the string
     * @return the generated string
     */
    public static String generateRandomString(int length){
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = (int) (chars.length() * Math.random());
            sb.append(chars.charAt(index));
        }
        return sb.toString();
    }

    /**
     * Check if an email is valid
     * @param email the email to check
     * @return true if the email is valid, false otherwise
     */
    public static boolean isValidEmail(String email){
        return email.matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$");
    }

    /**
     * Check if the password meets the requirements
     * @param password the password to check
     * @return true if the password meets basic requirements, false otherwise
     */
    public static boolean isSafePassword(String password) {
        if (password.length() < 8) {
            return false;
        }

        return true;
    }
}
