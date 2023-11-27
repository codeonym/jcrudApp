package ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.filters;
import org.apache.commons.lang3.StringEscapeUtils;
import org.checkerframework.checker.regex.qual.Regex;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;

public class UserInputFilter {

    public static String testInput(String input) {

        // TRIM SPACES
        input = input.trim();

        // STRIP SLASHES
        input = input.replace("^\\\\+|\\\\+$","");

        // ESCAPE HTML SPECIAL CHARS TO PROTECT AGAINST XSS
        return StringEscapeUtils.escapeHtml4(input);
    }

    public static boolean testRegex(String input, String regex) {

        // TEST FOR SPECIFIC REGEX
        return input.matches(regex);
    }

    public static boolean isValidName(String input) {

        // CONTAINS ONLY LETTERS AND SPACES
        String regEx = "^[a-zA-Z ]{1,}$";
        return testRegex(input, regEx);
    }

    public static boolean isValidIdentifier(String input) {

        // CONTAINS ONE LETTER AND 9 DIGITS
        String regEx = "^[a-zA-Z]\\d{9}$";

        return testRegex(input, regEx);
    }

    public static boolean isValidAddress(String input) {

        // CONTAINS LETTERS, DIGITS, COMMAS, DOTS, SPACES, DASHES
        String regEx = "^[a-zA-Z0-9 ,.-]+$";

        return testRegex(input, regEx);
    }

    public static boolean isValidBirthDate(LocalDate localDate) {

        // CHECK IF THE GIVEN DATE IS IN THE PAST
        return localDate.isBefore((LocalDate.now()));
    }

    public static boolean isValidYear(LocalDateTime date, int year){
        return date.getYear() == year;
    }
    public static boolean isValidYear(LocalDateTime date){
        int currentYear = YearMonth.now().getYear();
            return isValidYear(date, currentYear);
    }
}
