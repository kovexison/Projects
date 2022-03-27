package com.bankApp.bank.account;

import com.bankApp.card.Card;
import com.bankApp.userTypes.Person;

import java.util.Random;

public class BankAccount implements Account {

    private int balance = 0;
    private Person accountHolder;
    private Card card;

    public BankAccount(Person holder) {
        Random random = new Random();
        //generate pin
        int pin = random.nextInt(1000);
        this.card = new Card(this);
        this.card.setPinNumber(pin);
        //generate card number
        int cardNum = random.nextInt(1000000000);
        this.card.setCardNumber(cardNum);
        this.accountHolder = holder;
    }


    public int getBalance() {
        return balance;
    }

    public Card getCard() {
        return card;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void withDraw(int amount) {
        if (this.balance < amount) {
            System.out.println("Insufficient funds.");
        } else {
            this.balance -= amount;
        }
    }

    public Person getAccountHolder() {
        return accountHolder;
    }

    @Override
    public void printAccountInformation() {
        System.out.println("Your card number:");
        System.out.println(this.card.getCardNumber());
        System.out.println("Your card PIN:");
        System.out.println(this.card.getPinNumber());
    }
}
