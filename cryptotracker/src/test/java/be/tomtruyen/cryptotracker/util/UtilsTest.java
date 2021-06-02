package be.tomtruyen.cryptotracker.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Locale;

public class UtilsTest {
    @Test
    void isValidPasswordReturnsFalseWhenPasswordIsTooShort() {
        String password = "";

        Assertions.assertFalse(Utils.isValidPassword(password));
    }

    @Test
    void isValidPasswordReturnsFalseWhenPasswordDoesNotContainUppercase() {
        String password = "password";

        Assertions.assertFalse(Utils.isValidPassword(password));
    }

    @Test
    void isValidPasswordReturnsFalseWhenPasswordDoesNotContainLowercase() {
        String password = "PASSWORD";

        Assertions.assertFalse(Utils.isValidPassword(password));
    }

    @Test
    void isValidPasswordReturnsFalseWhenPasswordDoesNotContainNumber() {
        String password = "Password";

        Assertions.assertFalse(Utils.isValidPassword(password));
    }

    @Test
    void isValidPasswordReturnsTrueWhenPasswordIsValid() {
        String password = "Password1";

        Assertions.assertTrue(Utils.isValidPassword(password));
    }

    @Test
    void createErrorMessageShouldReturnFormattedErrorMessage() {
        String path = "test";
        String message = "Error Message";

        String errorMessage = Utils.createErrorMessage(path, message);

        Assertions.assertNotNull(errorMessage);
        Assertions.assertTrue(errorMessage.length() > 0);
        Assertions.assertTrue(errorMessage.toLowerCase().contains(path.toLowerCase()) && errorMessage.toLowerCase().contains(message.toLowerCase()));
    }

    @Test
    void getDateFromMillisShouldReturnDateFromMillis() {
        Date now = new Date();

        long millis = now.getTime();

        Date result = Utils.getDateFromMillis(millis);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(now, result);
    }

    @Test
    void hashPasswordShouldReturnHashedPasswordFromPlainText() {
        String password = "password";

        String resultPassword = Utils.hashPassword(password);

        Assertions.assertNotNull(resultPassword);
        Assertions.assertTrue(resultPassword.length() > 0);
    }

    @Test
    void priceWithDecimalShouldReturnDoubleFormattedAsPriceString() {
        double price = 1234.99;

        String priceString = Utils.priceWithDecimal(price);

        Assertions.assertNotNull(priceString);
        Assertions.assertEquals("1.234,99", priceString);
    }
}
