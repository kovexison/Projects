package com.bankApp.bank;

import com.bankApp.database.BankDataBase;

import java.util.Arrays;

public class MyBank extends Bank{
    private BankDataBase database;
    private static MyBank onlyBank;

    private MyBank(String bankName) {
        this.setBankName(bankName);
        this.database = BankDataBase.getInstance();
    }

    public static MyBank getInstance() { //Singleton
        if (onlyBank == null) {
            return new MyBank("National Bank of Gormania");
        } else {
            return onlyBank;
        }
    }

    public static void determineCheckSum(int[] cardNumber) {
        int sum = 0;
        int checksum = 0;
        int[] temp = Arrays.copyOf(cardNumber, cardNumber.length);
        for (int i = 0; i < temp.length; i++) {
            if (i % 2 == 0) {
                temp[i] = temp[i] * 2;
            }
            if (temp[i] > 9) {
                temp[i] = temp[i] - 9;
            }
        }
        sum = Arrays.stream(temp).sum();
        for (int i = 0; i < 10; i++) {
            if ((sum + i) % 10 == 0) {
                checksum = i;
                break;
            }
        }
        cardNumber[cardNumber.length - 1] = checksum;
    }


    public BankDataBase getDatabase() {
        return database;
    }
}
