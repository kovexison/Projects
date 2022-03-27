package com.bankApp.userTypes;

import com.bankApp.BankApp;
import com.bankApp.bank.MyBank;
import com.bankApp.menus.AdminMainMenu;

import java.util.Scanner;

public class DBAdministrator extends Person {
    private static String userName = "admin";
    private static String password = "admin";
    private static final Scanner SCAN = new Scanner(System.in);

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        DBAdministrator.userName = userName;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        DBAdministrator.password = password;
    }

    public static void adminLogin() {

        int choice;
        do {
            System.out.println(AdminMainMenu.MENU.getText());
            choice = SCAN.nextInt();
            try {
                switch (choice) {
                    case 1 -> MyBank.getInstance().getDatabase().showTable();
                    case 0 -> {
                        System.exit(1);
                        BankApp.iWantToRun = false;
                    }
                }
            } catch (Exception e) {
                System.err.println("You typed a non-numeric value! Please give only numeric values.");
            }
        } while (choice != 2) ;
    }
}
