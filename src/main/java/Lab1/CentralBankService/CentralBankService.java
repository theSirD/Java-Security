package Lab1.CentralBankService;

import Lab1.BankAccounts.BaseBankAccount;
import Lab1.Transactions.BaseTransaction;
import Lab1.Transactions.TransferMoneyBetweenAccountsTransaction;
import Lab1.BankService.BankService;
import Lab1.Transactions.TypeOfRegularTransaction;

import java.util.*;

public class CentralBankService {
    private Map<Long, BankService> _banksList = new HashMap<>();

    private BaseTransaction _currentTransaction;

    public void addBank(BankService bank) {
        registerBank(bank);
    }

    public void notifyBanksOfRegularTransactions(TypeOfRegularTransaction type) {
        for (Map.Entry<Long, BankService> bank : _banksList.entrySet()) {
            bank.getValue().update(type);
        }
    }

    public void registerBank(BankService bank) {
        _banksList.put(Long.valueOf(_banksList.size() + 1), bank);
    }

    public void transferMoneyBetweenAccountsOfDifferentBanks(BaseBankAccount senderAccount, Long receiverBankId, Long receiverAccountId, Double amountOfMoney) {
        Optional<Map.Entry<Long, BankService>> receiverBankFromStorage = _banksList
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey().equals(receiverBankId))
                .findFirst();

        BankService receiverBank = receiverBankFromStorage.get().getValue();

        Optional<Map.Entry<Long, BaseBankAccount>> receiverAccountFromStorage = receiverBank.bankAccountsList
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey().equals(receiverAccountId))
                .findFirst();

        BaseBankAccount receiverAccount = receiverAccountFromStorage.get().getValue();

        setTransaction(new TransferMoneyBetweenAccountsTransaction(receiverAccount, senderAccount, amountOfMoney));

        executeTransaction();
    }

    private void setTransaction(BaseTransaction transaction) {
        _currentTransaction = transaction;
    }

    private void executeTransaction() {
        _currentTransaction.execute();
    }
}