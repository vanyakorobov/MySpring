package org.example.operations;

import org.example.account.Account;
import org.example.account.AccountService;
import org.example.user.UserService;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class CreateAccountProcessor implements OperationCommandProcessor {

    private final Scanner scanner;
    private final UserService userService;
    private final AccountService accountService;

    public CreateAccountProcessor(Scanner scanner, UserService userService, AccountService accountService) {
        this.scanner = scanner;
        this.userService = userService;
        this.accountService = accountService;
    }

    @Override
    public void processOperation() {
        System.out.println("введите id");
        int userId = Integer.parseInt(scanner.nextLine());
        var user = userService.findUserById(userId)
                .orElseThrow(() -> new IllegalArgumentException("no such user id=%s"
                        .formatted(userId)));
        var account = accountService.createAccount(user);
        user.getAccountList().add(account);
        System.out.println("account created id=%s user=%s"
                .formatted(account.getId(), user.getLogin()));
    }

    @Override
    public ConsolOperationType getOperationType() {
        return ConsolOperationType.ACCOUNT_CREATE;
    }

}
