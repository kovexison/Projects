package com;

import com.bankApp.BankApp;

public class Main {
    public static void main(String[] args) {
        while(BankApp.iWantToRun) {
            try {
                new BankApp();
            } catch (Exception e) {
                System.err.println("You typed something incorrect, don't you?");
            }
        }
    }
}
