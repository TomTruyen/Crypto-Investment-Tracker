package be.tomtruyen.cryptotracker.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static final Pattern UPPERCASE_REGEX = Pattern.compile("[A-Z ]");
    public static final Pattern LOWERCASE_REGEX = Pattern.compile("[a-z ]");
    public static final Pattern DIGITCASE_REGEX = Pattern.compile("[0-9 ]");
    public static final int MIN_PASSWORD_LENGTH = 8;

    public static boolean isValidPassword(String password) {
        if (password.length() < MIN_PASSWORD_LENGTH)
            return false;

        if (!UPPERCASE_REGEX.matcher(password).find())
            return false;

        if (!LOWERCASE_REGEX.matcher(password).find())
            return false;

        return DIGITCASE_REGEX.matcher(password).find();
    }

    public static String createErrorMessage(String path, String message) {
        return String.format("[%s] @%s - %s", getDateTime(), path.toUpperCase(), message);
    }

    public static Date getDateFromMillis(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }

    public static String hashPassword(String password) {
        return DigestUtils.sha512Hex(password);
    }

    public static String getDateTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        return dtf.format(now);
    }

    public static String priceWithDecimal (double price) {
        NumberFormat nf = NumberFormat.getInstance(Locale.GERMAN);
        DecimalFormat formatter = (DecimalFormat) nf;
        formatter.applyPattern("###,###,##0.00");
        return formatter.format(price);
    }
}
