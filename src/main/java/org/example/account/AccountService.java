package org.example.account;

import org.example.user.User;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AccountService {

    private final Map<Integer, Account> accountMap;
    private int idCounter;
    private final AccountProperties accountProperties;

    public AccountService(AccountProperties accountProperties) {
        this.accountMap = new HashMap<>();
        this.idCounter = 0;
        this.accountProperties = accountProperties;
    }

    public Account createAccount(User user) {
        idCounter++;
        Account account = new Account(idCounter, user.getId(), accountProperties.getDefaultAccountAmount());
        accountMap.put(account.getId(), account);
        return account;
    }

    public Optional<Account> findAccountById(int id) {
        return Optional.ofNullable(accountMap.get(id));
    }

    public List<Account> getAllAccounts(int userId) {
        return accountMap.values()
                .stream()
                .filter(account -> account.getUserId() == userId)
                .toList();
    }

    public void depositAccount(int accountId, int moneyToDeposit) {
        var account = findAccountById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("no such account^ id%s".formatted(accountId)));
if (moneyToDeposit <= 0) {
    throw new IllegalArgumentException("moneyToDeposit < 0: amount%s".formatted(moneyToDeposit));
}
        account.setMoneyAmmount(account.getMoneyAmmount() + moneyToDeposit);
    }

    public void withdrawFromAccount(int accountId, int amountToWithdraw) {
        var account = findAccountById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("no such account^ id%s".formatted(accountId)));
        if (amountToWithdraw <= 0) {
            throw new IllegalArgumentException("Cannot withdraw not positive amount".formatted(amountToWithdraw));
        }
        if (account.getMoneyAmmount() < amountToWithdraw) {
            throw new IllegalArgumentException("less money than you are trying to write off"
                    .formatted(amountToWithdraw));
        }
        account.setMoneyAmmount(account.getMoneyAmmount() - amountToWithdraw);
    }

    public  Account closeAccount(int accountId) {
        var accountToRemove = findAccountById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("no such account^ id%s".formatted(accountId)));
        List<Account> accountList = getAllAccounts(accountToRemove.getId());
        if(accountList.size() == 1) {
            throw new IllegalArgumentException(("no such account^ id%s".formatted(accountId)));
        }
        Account accountToDeposit = accountList.stream()
                .filter(it -> it.getId() != accountId)
                .findFirst()
                .orElseThrow();
        accountToDeposit.setMoneyAmmount(accountToDeposit.getMoneyAmmount() + accountToRemove.getMoneyAmmount());
        accountMap.remove(accountId);
        return accountToRemove;
    }

    public void transfer(int fromAccountId, int toAccountId, int amountToTransfer) {
        var accountFrom = findAccountById(fromAccountId)
                .orElseThrow(() -> new IllegalArgumentException("no such account^ id%s".formatted(fromAccountId)));
        var accountTo = findAccountById(toAccountId)
                .orElseThrow(() -> new IllegalArgumentException("no such account^ id%s".formatted(toAccountId)));
        if (amountToTransfer <= 0) {
            throw new IllegalArgumentException("Cannot transfer not positive amount".formatted(amountToTransfer));
        }
        if (accountFrom.getMoneyAmmount() < amountToTransfer) {
            throw new IllegalArgumentException("less money than you are trying to write off");
        }
        int totalAmountToDeposit = accountTo.getUserId() != accountFrom.getUserId()
               ? (int) (amountToTransfer - amountToTransfer * accountProperties.getTransferCommission())
                : amountToTransfer;
        accountFrom.setMoneyAmmount(accountFrom.getMoneyAmmount() - amountToTransfer);
        accountTo.setMoneyAmmount(accountTo.getMoneyAmmount() + totalAmountToDeposit);
    }
}
