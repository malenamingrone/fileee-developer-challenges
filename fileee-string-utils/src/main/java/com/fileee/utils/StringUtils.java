package com.fileee.utils;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 *  This class consists of <code>static</code> utility methods for operating on strings.
 */
public class StringUtils {

    /**
     * Inserts a * between two consecutive equal characters.
     *
     * @param str string to be starred.
     * @param i   position of the current character to be evaluated.
     * @return The resultant string.
     */
    public static String pairStar(String str, int i) {

        if (i == str.length() - 1) {
            return str;
        }

        if (str.charAt(i) == str.charAt(++i)) {
            String beginning = str.substring(0, i);
            String end = str.substring(i);

            str = beginning.concat("*").concat(end);
        }

        return pairStar(str, i);
    }


    /**
     * Checks whether an email address is valid or not.
     *
     * @param email to be evaluated.
     * @return {@code true} if the email address provided is valid otherwise {@code false}
     */
    public static boolean isValidEmailAddress(String email) {
        Pattern pattern = Pattern.compile(".+@.+\\..*");
        return !isNullOrEmpty(email) && pattern.matcher(email).matches();
    }

    public static boolean isNullOrEmpty(String str) {
        return Objects.isNull(str) || str.isEmpty();
    }

}
