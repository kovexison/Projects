package com.bankApp.card;

import com.bankApp.bank.Bank;
import com.bankApp.bank.MyBank;
import com.bankApp.bank.account.BankAccount;

public class Card {
    private String pinNumber;
    private String issuer = "400000";
    private int[] cardNumber = new int[16];
    private BankAccount parentAccount;

    public Card(BankAccount parentAccount) {
        this.parentAccount = parentAccount;
    }

    public String getPinNumber() {
        return pinNumber;
    }

    public void setPinNumber(int pinNumber) {
        this.pinNumber = String.format("%04d", pinNumber);
    }

    public String getIssuer() {
        return issuer;
    }

    public boolean pay(int amount) {
        if (this.parentAccount.getBalance() < amount) {
            System.out.println("Insufficient funds!");
            return false;
        } else {
            System.out.println("Payment successful!");
            this.parentAccount.setBalance(this.parentAccount.getBalance() - amount);
            return true;
        }
    }

    public String getCardNumber() {
        StringBuilder string = new StringBuilder();
        for (int i : this.cardNumber) {
            string.append(i);
        }
        return string.toString();
    }

    public void setCardNumber(int cardNumber) {
        issuer += cardNumber;
        for (int i = 0; i < issuer.length(); i++) {
            this.cardNumber[i] = Character.getNumericValue(issuer.toCharArray()[i]);
        }
        MyBank.determineCheckSum(this.cardNumber);
    }
}
