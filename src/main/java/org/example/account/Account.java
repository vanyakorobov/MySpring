package org.example.account;

public class Account {

    private final int id;
    private final int userId;
    private int moneyAmmount;

    public Account(int id, int userId, int moneyAmmount) {
        this.id = id;
        this.userId = userId;
        this.moneyAmmount = moneyAmmount;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getMoneyAmmount() {
        return moneyAmmount;
    }

    public void setMoneyAmmount(int moneyAmmount) {
        this.moneyAmmount = moneyAmmount;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", userId=" + userId +
                ", moneyAmmount=" + moneyAmmount +
                '}';
    }
}
