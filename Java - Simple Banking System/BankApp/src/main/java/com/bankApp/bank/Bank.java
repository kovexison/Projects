package com.bankApp.bank;

import java.util.Arrays;

public abstract class Bank {
    private String bankName;

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public static class BankUtil {

        public static int[] stringToIntArray(String str) {
            int size = str.length();
            int[] arr = new int[size];
            String[] ary = str.split("");
            for (int i = 0; i < size; i++) {
                arr[i] = Integer.parseInt(ary[i]);
            }
            return arr;
        }

        public static boolean checkCardNum(int[] cardNumber) {
            int sum = 0;
            int checksum = 0;
            int[] temp = Arrays.copyOf(cardNumber, cardNumber.length);
            for (int i = 0; i < temp.length - 1; i++) {
                if (i % 2 == 0) {
                    temp[i] = temp[i] * 2;
                }
                if (temp[i] > 9) {
                    temp[i] = temp[i] - 9;
                }
            }
            sum = Arrays.stream(temp).sum() - temp[15];
            for (int i = 0; i < 10; i++) {
                if ((sum + i) % 10 == 0) {
                    checksum = i;
                    break;
                }
            }
            return cardNumber[cardNumber.length - 1] == checksum;
        }
    }
}
