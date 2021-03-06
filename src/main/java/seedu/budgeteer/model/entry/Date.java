package seedu.budgeteer.model.entry;

import static java.util.Objects.requireNonNull;
import static seedu.budgeteer.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import seedu.budgeteer.commons.util.DateUtil;

/**
 * Represents a Record's date in the financial budgeteer.
 * Guarantees: immutable; is valid as declared in {@link #isValidDateFormat(String)}
 */
public class Date {
    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Date parameter should be in the format of dd-mm-yyyy "
                    + "with dd and mm being 1 or 2 digits, and yyyy being 4 digits.\n"
                    + "Please take note that inappropriate date will result in errors, for example: 30/02/2019";
    public static final String MESSAGE_DATE_LOGICAL_CONSTRAINTS =
            "Date should follow the modern calendar. "
                    + "Day parameter must fit within the constraints of each month. \n"
                    + "For e.g, February has only 28 days for the non-Leap year "
                    + "so the day parameter must be less than or equal to 28 if the month "
                    + "parameter is 2.";
    public static final String DATE_VALIDATION_REGEX = "[0-3]?\\d{1}-[0-1]?\\d{1}-\\d{4}";

    public static final String DATE_INPUT_TODAY = "today";
    public static final String DATE_INPUT_YESTERDAY = "ytd";

    private static DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public final String value;

    private int day;
    private int month;
    private int year;

    private LocalDate localDate;

    /**
     * Constructs a {@code Date}.
     * @param date A valid date.
     */
    public Date(String date) {
        requireNonNull(date);
        // Get Representation for today and yesterday
        if (date.equalsIgnoreCase(DATE_INPUT_TODAY)) {
            LocalDate todayDate = LocalDate.now();
            date = todayDate.format(dtFormat);
        } else if (date.equalsIgnoreCase(DATE_INPUT_YESTERDAY)) {
            LocalDate ytdDate = LocalDate.now().minusDays(1L);
            date = ytdDate.format(dtFormat);
        }

        checkArgument(isValidDateFormat(date), MESSAGE_DATE_CONSTRAINTS);
        splitDate(date);
        value = getStandardValue();
        checkArgument(DateUtil.isValidDate(day, month, year), MESSAGE_DATE_LOGICAL_CONSTRAINTS);
        localDate = LocalDate.of(year, month, day);
    }


    /**
     * Change the (String)value to some Standard Value (follow the format dd-mm-yyyy)
     * @return standard value Date
     */
    public String getStandardValue() {
        String standardDay;
        String standardMonth;
        String standardYear;
        if (day > 0 && day < 10 && String.valueOf(day).length() == 1) {
            standardDay = "0" + String.valueOf(day);
        } else {
            standardDay = String.valueOf(day);
        }
        if (month > 0 && month < 10 && String.valueOf(month).length() == 1) {
            standardMonth = "0" + String.valueOf(month);
        } else {
            standardMonth = String.valueOf(month);
        }
        standardYear = String.valueOf(year);
        String standardValue =
                String.format(standardDay + "-" + standardMonth + "-" + standardYear);
        return standardValue;
    }

    /**
     * Transform the Date into standard Date following the format dd-mm-yyyy
     * @return standard Date
     */
    public Date getStandardDate() {
        return new Date (getStandardValue());
    }
    /**
     * Splits a date into the different parameters and assigns them to day,month,year
     * Format specified: dd-mm-yyyy
     * @param date
     */
    private void splitDate(String date) {
        String[] dateParams = date.split("-");
        day = Integer.parseInt(dateParams[0]);
        month = Integer.parseInt(dateParams[1]);
        year = Integer.parseInt(dateParams[2]);

    }

    /**
     * Returns true if the string to be tested is a valid date format, false otherwise
     * @param test
     */
    public static boolean isValidDateFormat(String test) {
        return (test.matches(DATE_VALIDATION_REGEX) || test.equalsIgnoreCase(DATE_INPUT_YESTERDAY)
                || test.equalsIgnoreCase(DATE_INPUT_TODAY));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    public String getValue() {
        return value;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Date // instanceof handles nulls
                && day == ((Date) other).getDay()
                && month == ((Date) other).getMonth()
                && year == ((Date) other).getYear())
                || (other instanceof Date)
                && this.localDate.equals(((Date) other).getLocalDate()); // state check
    }

    public boolean isBefore(Date other) {
        return (this.localDate.isBefore(other.getLocalDate()));
    }

    public boolean isAfter(Date other) {
        return (this.localDate.isAfter(other.getLocalDate()));
    }

    public boolean isBetween(Date startDate, Date endDate) {
        return (this.localDate.isAfter(startDate.getLocalDate()) && this.localDate.isBefore(endDate.getLocalDate()));
    }
}
