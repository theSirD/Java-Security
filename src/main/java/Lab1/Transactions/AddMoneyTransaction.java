package Lab1.Transactions;

import Lab1.BankAccounts.BaseBankAccount;
import Lab1.Utilities.CustomTuple;

public class AddMoneyTransaction extends BaseTransaction {
    private final BaseBankAccount _receiver;

    private final Double _amountOfMoneyToAdd;

    public AddMoneyTransaction(BaseBankAccount receiver, Double amountOfMoneyToAdd) {
        _receiver = receiver;
        _amountOfMoneyToAdd = amountOfMoneyToAdd;
    }

    public void execute() {
        _receiver.setBalance(_receiver.getBalance() + _amountOfMoneyToAdd);
        CustomTuple<Long, BaseTransaction> transactionHistoryEntry = new CustomTuple<>(Long.valueOf(_receiver.transactionsHistory.size() + 1), this);
        _receiver.transactionsHistory.push(transactionHistoryEntry);
    }


    public void undo() {
        _receiver.setBalance(_receiver.getBalance() - _amountOfMoneyToAdd);
        _receiver.transactionsHistory.pop();
    }
}