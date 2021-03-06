package seedu.budgeteer.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.budgeteer.commons.core.LogsCenter;
import seedu.budgeteer.commons.exceptions.DataConversionException;
import seedu.budgeteer.commons.exceptions.IllegalValueException;
import seedu.budgeteer.commons.util.FileUtil;
import seedu.budgeteer.commons.util.JsonUtil;
import seedu.budgeteer.model.ReadOnlyEntriesBook;

/**
 * A class to access EntriesBook data stored as a json file on the hard disk.
 */
public class JsonBudgeteerStorage implements BudgeteerStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonBudgeteerStorage.class);

    private Path filePath;

    public JsonBudgeteerStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getAddressBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyEntriesBook> readEntriesBook() throws DataConversionException {
        return readEntriesBook(filePath);
    }

    /**
     * Similar to {@link #readEntriesBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyEntriesBook> readEntriesBook(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableBudgeteer> jsonAddressBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableBudgeteer.class);
        if (!jsonAddressBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonAddressBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveAddressBook(ReadOnlyEntriesBook addressBook) throws IOException {
        saveAddressBook(addressBook, filePath);
    }

    /**
     * Similar to {@link #saveAddressBook(ReadOnlyEntriesBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveAddressBook(ReadOnlyEntriesBook addressBook, Path filePath) throws IOException {
        requireNonNull(addressBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableBudgeteer(addressBook), filePath);
    }

}
