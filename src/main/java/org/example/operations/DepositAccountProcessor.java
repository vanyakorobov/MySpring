package org.example.operations;

import org.example.account.AccountService;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class DepositAccountProcessor implements OperationCommandProcessor {

    private final Scanner scanner;
    private final AccountService accountService;

    public DepositAccountProcessor(Scanner scanner, AccountService accountService) {
        this.scanner = scanner;
        this.accountService = accountService;
    }

    @Override
    public void processOperation() {
        System.out.println("Enter account id");
        int accountId = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter amount to deposit");
        int amountToDeposit = Integer.parseInt(scanner.nextLine());
        accountService.depositAccount(accountId, amountToDeposit);
        System.out.println("Deposited " + amountToDeposit + " to " + accountId);
    }

    @Override
    public ConsolOperationType getOperationType() {
        return ConsolOperationType.ACCOUNT_DEPOSIT;
    }
}
