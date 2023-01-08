package org.spine.iquestionapi.util;

import org.spine.iquestionapi.model.EmailDomain;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.spine.iquestionapi.model.LoginCredentials.MIN_LENGTH_PASSWORD;

/**
 * The class for string utilities
 */
public class StringUtil {

    private static final String DOMAIN_REGEX = "^(?!\\-)[a-zA-Z0-9\\-]{1,63}(?<!\\-)\\.[a-zA-Z]{2,}$";

    /**
     * Generate a random string of a given length
     *
     * @param length the length of the string
     * @return the generated string
     */
    public static String generateRandomString(int length) {
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
     *
     * @param email the email to check
     * @return true if the email is valid, false otherwise
     */
    public static boolean isValidEmail(String email, List<EmailDomain> emailDomains) {
        String domain = email.substring(email.indexOf("@") + 1);

        boolean isEmailValid = email.matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$");
        boolean isEmailDomainValid = false;

        for (EmailDomain emailDomain : emailDomains) {
            String validDomain = emailDomain.getDomain();
            if (Objects.equals(domain, validDomain)) {
                isEmailDomainValid = true;
                break;
            }
        }

        return isEmailDomainValid && isEmailValid;
    }

    /**
     * Check if the password meets the requirements
     *
     * @param password the password to check
     * @return true if the password meets basic requirements, false otherwise
     */
    public static boolean isSafePassword(String password) {
        if (password.length() < MIN_LENGTH_PASSWORD) {
            return false;
        }

        return true;
    }

    public static boolean isValidDomain(String domain) {
        Pattern domainPattern = Pattern.compile(DOMAIN_REGEX);
        Matcher domainMatcher = domainPattern.matcher(domain);
        
        return domainMatcher.matches();
    }
}
