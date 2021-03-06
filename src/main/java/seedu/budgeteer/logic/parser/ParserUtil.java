package seedu.budgeteer.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.budgeteer.commons.core.Messages;
import seedu.budgeteer.commons.core.index.Index;
import seedu.budgeteer.commons.util.StringUtil;
import seedu.budgeteer.logic.parser.exceptions.ParseException;
import seedu.budgeteer.model.DirectoryPath;
import seedu.budgeteer.model.entry.CashFlow;
import seedu.budgeteer.model.entry.Date;
import seedu.budgeteer.model.entry.Name;
import seedu.budgeteer.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String date} into a {@code Date}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code date} is invalid.
     */
    public static Date parseDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        if (!Date.isValidDateFormat(trimmedDate)) {
            throw new ParseException(Date.MESSAGE_DATE_CONSTRAINTS);
        }
        return new Date(trimmedDate);
    }

    /**
     * Parses a {@code String cashFlow} into an {@code CashFlow}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code CashFlow} is invalid.
     */
    public static CashFlow parseCashFlow(String cashFlow) throws ParseException {
        requireNonNull(cashFlow);
        String trimmedCashFlow = cashFlow.trim();
        if (!CashFlow.isValidCashFlow(trimmedCashFlow)) {
            throw new ParseException(CashFlow.MESSAGE_CONSTRAINTS);
        }
        return CashFlow.getCashFlow(trimmedCashFlow);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }
    /**
     * Parses {@code Directory Path} into a {@code DirectoryPath}
     */
    public static String parseDirectoryString(String dirPath) throws ParseException {
        requireNonNull(dirPath);
        if (!DirectoryPath.isValidDirectory(dirPath)) {
            throw new ParseException(String.format(
                    Messages.MESSAGE_INVALID_COMMAND_FORMAT, Messages.MESSAGE_UNREALISTIC_DIRECTORY));
        }
        return dirPath;
    }
}
