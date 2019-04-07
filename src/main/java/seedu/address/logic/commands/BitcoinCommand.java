package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ENTRYS;

import javafx.collections.ObservableList;
import seedu.address.commons.util.CryptoUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.entry.Entry;
import seedu.address.model.entry.ReportEntryList;


/**
 * Returns how many Bitcoin you can buy at the current market price.
 */
public class BitcoinCommand extends Command {

    public static final String COMMAND_WORD = "bitcoin";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays how much bitcoin you can buy.\n"
            + "Example: " + COMMAND_WORD;

    public String MESSAGE_SUCCESS = "You are able to buy ";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        double price = 0.0;
        CryptoUtil cryptoUtil = CryptoUtil.getInstance();
        price = cryptoUtil.getBtc();

        model.updateFilteredEntryList(PREDICATE_SHOW_ALL_ENTRYS);
        ObservableList<Entry> filteredList = model.getFilteredEntryList();
        ReportEntryList reportList = new ReportEntryList(filteredList);
        Double total = reportList.getTotal();

        Double amount = total / price;
        amount = (double) Math.round(amount * 100.0) / 100.0;

        MESSAGE_SUCCESS = MESSAGE_SUCCESS + amount.toString() + " BTC.";

        int roundOff = (int) Math.round(price);

        // This is where you divide the cashflow by the price of bitcoin, and add it to the message

        String currentPrice = " The current price of bitcoin is $" + roundOff + ".";
        MESSAGE_SUCCESS = MESSAGE_SUCCESS + currentPrice;
//        System.out.println("The current price of bitcoin is $" + roundOff ".");
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
