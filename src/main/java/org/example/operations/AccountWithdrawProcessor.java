package org.example.operations;

import org.example.account.AccountService;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class AccountWithdrawProcessor implements  OperationCommandProcessor{

    private final Scanner scanner;
    private final AccountService accountService;

    public AccountWithdrawProcessor(Scanner scanner, AccountService accountService) {
        this.scanner = scanner;
        this.accountService = accountService;
    }

    @Override
    public void processOperation() {
        System.out.println("Enter account id");
        int accountId = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter amount to withdraw");
        int amountToWithdrow = Integer.parseInt(scanner.nextLine());
        accountService.withdrawFromAccount(accountId, amountToWithdrow);
        System.out.println("Deposited " + amountToWithdrow + " to " + accountId);
    }

    @Override
    public ConsolOperationType getOperationType() {
        return ConsolOperationType.ACCOUNT_WITHDRAW;
    }
}
