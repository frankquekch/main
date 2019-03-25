package seedu.address.model.entry;

import static java.lang.Double.isFinite;
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents any form of cash flow in a record in the book
 * Guarantees: immutable; is valid as declared in {@link #isValidCashFlow(String)}
 */
public class CashFlow {
    public static final String MESSAGE_CASH_FLOW_CONSTRAINTS =
            "Any form of cash flow should consist of '+' or '-', "
                    + "followed by a sequence of characters consisting of only digits and/or decimal points ('.')."
                    + "It must be of the following form <number>.<number>:\n"
                    + "1. <number> cannot start from '0' unless it has only 1 digit. "
                    + "There must be at least 1 digit in this field.\n"
                    + "2. At most 1 decimal point can be present. Decimal point is optional."
                    + "If decimal point is present, it must have at least 1 digit and at most 2 digits after it.\n"
                    + "3. The maximum whole number allowed is 1e12 - 1. Anything more than this is not allowed. ";

    public static final String SIGN_REGEX = ("(?<sign>[-+])");
    public static final String CASHFLOW_NO_SIGN_REGEX = ("(?<cash>.*)");
    public static final String CURRENCY = "$";
    public static final String POSITIVE_SIGN = "+";
    public static final String NEGATIVE_SIGN = "-";
    public static final String REPRESENTATION_ZERO = "-0";
    public static final String FORMAT_STANDARD_CASH = "%.2f";
    public static final Double MAX_CASH = 99999999999.99;

    private static final String CASHFLOW_VALIDATION_REGEX = "^[+-](0|[1-9]\\d{0,11})(\\.\\d{1,2})?";

    public final String value;
    public final Double valueDouble;

    public CashFlow(String cashFlow) {
        requireNonNull(cashFlow);
        checkArgument(isValidCashFlow(cashFlow), MESSAGE_CASH_FLOW_CONSTRAINTS);
        this.value = cashFlow;
        valueDouble = Double.valueOf(cashFlow);
        checkArgument(isFinite(valueDouble), MESSAGE_CASH_FLOW_CONSTRAINTS);
    }

    public CashFlow(Double cashFlow) {
        requireNonNull(cashFlow);
        checkArgument(isValidCashFlow(cashFlow.toString()), MESSAGE_CASH_FLOW_CONSTRAINTS);
        this.valueDouble = cashFlow;
        value = cashFlow.toString();
    }
    /**
     * Returns if a given string is a valid cashflow parameter.
     */
    public static boolean isValidCashFlow(String test) {
        return test.matches(CASHFLOW_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        if (String.format(FORMAT_STANDARD_CASH, Math.abs(valueDouble)).equals("0.00")) {
            return CURRENCY + String.format(FORMAT_STANDARD_CASH, Math.abs(valueDouble));
        } else if (valueDouble > 0) {
            return POSITIVE_SIGN + CURRENCY + String.format(FORMAT_STANDARD_CASH, Math.abs(valueDouble));
        } else {
            return NEGATIVE_SIGN + CURRENCY + String.format(FORMAT_STANDARD_CASH, Math.abs(valueDouble));
        }
    }

    public double toDouble () {
        return valueDouble;
    }

    public boolean isNotLarger (double cashin) {
        return (cashin >= valueDouble);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CashFlow // instanceof handles nulls
                && value.equals(((CashFlow) other).value)); // state check
    }

    public int hashCode() {
        return value.hashCode();
    }
}