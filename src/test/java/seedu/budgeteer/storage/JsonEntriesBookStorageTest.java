package seedu.budgeteer.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.budgeteer.testutil.TypicalEntrys.CAIFAN;
import static seedu.budgeteer.testutil.TypicalEntrys.IDA;
import static seedu.budgeteer.testutil.TypicalEntrys.MALA;
import static seedu.budgeteer.testutil.TypicalEntrys.getTypicalAddressBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.budgeteer.commons.exceptions.DataConversionException;
import seedu.budgeteer.model.EntriesBook;
import seedu.budgeteer.model.ReadOnlyEntriesBook;

public class JsonEntriesBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonEntriesBookStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readAddressBook_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readAddressBook(null);
    }

    private java.util.Optional<ReadOnlyEntriesBook> readAddressBook(String filePath) throws Exception {
        return new JsonBudgeteerStorage(Paths.get(filePath)).readEntriesBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readAddressBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readAddressBook("notJsonFormatAddressBook.json");

        // IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
        // That means you should not have more than one exception test in one method
    }

    @Test
    public void readAddressBook_invalidEntryAddressBook_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readAddressBook("invalidEntryAddressBook.json");
    }

    @Test
    public void readAddressBook_invalidAndValidEntryAddressBook_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readAddressBook("invalidAndValidEntryAddressBook.json");
    }

    @Test
    public void readAndSaveAddressBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempAddressBook.json");
        EntriesBook original = getTypicalAddressBook();
        JsonBudgeteerStorage jsonAddressBookStorage = new JsonBudgeteerStorage(filePath);

        // Save in new file and read back
        jsonAddressBookStorage.saveAddressBook(original, filePath);
        ReadOnlyEntriesBook readBack = jsonAddressBookStorage.readEntriesBook(filePath).get();
        assertEquals(original, new EntriesBook(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addEntry(CAIFAN);
        original.removeEntry(MALA);
        jsonAddressBookStorage.saveAddressBook(original, filePath);
        readBack = jsonAddressBookStorage.readEntriesBook(filePath).get();
        assertEquals(original, new EntriesBook(readBack));

        // Save and read without specifying file path
        original.addEntry(IDA);
        jsonAddressBookStorage.saveAddressBook(original); // file path not specified
        readBack = jsonAddressBookStorage.readEntriesBook().get(); // file path not specified
        assertEquals(original, new EntriesBook(readBack));

    }

    @Test
    public void saveAddressBook_nullAddressBook_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveAddressBook(null, "SomeFile.json");
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveAddressBook(ReadOnlyEntriesBook addressBook, String filePath) {
        try {
            new JsonBudgeteerStorage(Paths.get(filePath))
                    .saveAddressBook(addressBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveAddressBook_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveAddressBook(new EntriesBook(), null);
    }
}
