package com.danielagarcia.objectdetectiondemo.validator;

public class StringValidator {

    private static final String BASE_64_SET_REGEX = "^[A-z0-9/+]+[=]{0,2}$";

    /**
     * Check if basic requirements of a base64 encoded string are satisfied:
     *  * Length of string is a multiple of 4
     *  * Characters in this set are in A-z, 0-9, +, /, except for the padding character '='
     */
    public static boolean isBase64(String str) {
        if (!str.matches(BASE_64_SET_REGEX))
            return false;

        return true;
    }
}
