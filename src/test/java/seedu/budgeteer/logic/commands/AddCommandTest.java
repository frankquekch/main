package seedu.budgeteer.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.budgeteer.commons.core.GuiSettings;
import seedu.budgeteer.logic.CommandHistory;
import seedu.budgeteer.model.EntriesBook;
import seedu.budgeteer.model.Model;
import seedu.budgeteer.model.ReadOnlyEntriesBook;
import seedu.budgeteer.model.ReadOnlyUserPrefs;
import seedu.budgeteer.model.entry.Entry;
import seedu.budgeteer.testutil.EntryBuilder;

public class AddCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullEntry_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddCommand(null);
    }

    @Test
    public void execute_entryAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingEntryAdded modelStub = new ModelStubAcceptingEntryAdded();
        Entry validEntry = new EntryBuilder().build();

        CommandResult commandResult = new AddCommand(validEntry).execute(modelStub, commandHistory);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validEntry), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validEntry), modelStub.entrysAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void equals() {
        Entry alice = new EntryBuilder().withName("Alice").build();
        Entry bob = new EntryBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different entry -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void sortFilteredEntryList(String category, Boolean reversed) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addEntry(Entry entry) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyEntriesBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyEntriesBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasEntry(Entry entry) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteEntry(Entry target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setEntry(Entry target, Entry editedEntry) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Entry> getFilteredEntryList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredEntryList(Predicate<Entry> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyProperty<Entry> selectedEntryProperty() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Entry getSelectedEntry() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSelectedEntry(Entry entry) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single entry.
     */
    private class ModelStubWithEntry extends ModelStub {
        private final Entry entry;

        ModelStubWithEntry(Entry entry) {
            requireNonNull(entry);
            this.entry = entry;
        }

        @Override
        public boolean hasEntry(Entry entry) {
            requireNonNull(entry);
            return this.entry.isSameEntry(entry);
        }
    }

    /**
     * A Model stub that always accept the entry being added.
     */
    private class ModelStubAcceptingEntryAdded extends ModelStub {
        final ArrayList<Entry> entrysAdded = new ArrayList<>();

        @Override
        public boolean hasEntry(Entry entry) {
            requireNonNull(entry);
            return entrysAdded.stream().anyMatch(entry::isSameEntry);
        }

        @Override
        public void addEntry(Entry entry) {
            requireNonNull(entry);
            entrysAdded.add(entry);
        }

        @Override
        public void commitAddressBook() {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public ReadOnlyEntriesBook getAddressBook() {
            return new EntriesBook();
        }
    }

}
