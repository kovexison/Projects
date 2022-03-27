package com.bankApp;

import com.bankApp.bank.Bank;
import com.bankApp.bank.MyBank;
import com.bankApp.bank.account.BankAccount;
import com.bankApp.menus.FirstMainMenu;
import com.bankApp.menus.SubMenu;
import com.bankApp.userTypes.DBAdministrator;
import com.bankApp.userTypes.User;

import java.util.Objects;
import java.util.Scanner;

public class BankApp {
    private final Scanner SCN = new Scanner(System.in);
    private MyBank bank;
    private BankAccount currentAccount;
    public static boolean iWantToRun = true;

    public BankApp() {
        runTheApp();
    }

    public void runTheApp() {
        this.bank = MyBank.getInstance();
        System.out.println("Welcome to " + bank.getBankName());
        int choice;
        do {
            System.out.println(FirstMainMenu.MENU.getText());
            choice = SCN.nextInt();
            switch (choice) {
                case 1 -> {
                    //start account creation
                    System.out.println("Your first name: ");
                    String firstName = SCN.next();
                    System.out.println("Your last name: ");
                    String lastName = SCN.next();
                    System.out.println("Your Personal Identification Number (CNP): - note, only the first 13 numbers are considered ");
                    String cnp = SCN.next();
                    if (cnp.length() > 13) {
                        cnp = cnp.substring(0, 12);
                    }
                    if (bank.getDatabase().containsRecord(cnp)) {
                        System.out.println("You already have an account in our bank!");
                        System.out.println("You can log in with your card number and pin.");
                    } else {
                        User user = new User(firstName, lastName, cnp);
                        currentAccount = new BankAccount(user);
                        bank.getDatabase()
                                .insertData(user.getName(), user.getCnp(), currentAccount.getCard().getCardNumber(), currentAccount.getCard().getPinNumber());
                        System.out.println("\nYour card has been created");
                        currentAccount.printAccountInformation();
                    }
                }
                //end account creation
                case 2 -> {
                    //start login dialog
                    System.out.println("Enter your card number:");
                    String cardNumber = SCN.next();
                    System.out.println("Enter your PIN:");
                    String pin = SCN.next();
                    if (Objects.equals(DBAdministrator.getUserName(), cardNumber) && Objects.equals(DBAdministrator.getPassword(), pin)) {
                        DBAdministrator.adminLogin();
                    } else if (bank.getDatabase().containsRecord(cardNumber, pin)) {
                        int sub = 0;
                        System.out.println("You have successfully logged in!");
                        System.out.println("\nHello, " + bank.getDatabase().getCardHolderName(cardNumber));
                        do {

                            System.out.println(SubMenu.MENU.getText());
                            try {
                                sub = SCN.nextInt();
                            } catch (Exception e) {
                                //System.err.println(e.getMessage());
                                System.err.println("You can give only numeric values between 0 and 5 inclusive.");
                            }

                            switch (sub) {
                                case 1 -> bank.getDatabase().printBalance(cardNumber, pin);
                                case 2 -> {
                                    try {
                                        System.out.println("Enter income: ");
                                        int income = SCN.nextInt();
                                        bank.getDatabase().addMoney(income, cardNumber, pin);
                                    } catch (Exception e) {
                                        e.getCause();
                                        System.err.println("You have to enter numeric value!");
                                    }
                                }
                                case 3 -> { //do transfer
                                    try {
                                        System.out.println("\nTransfer");
                                        System.out.println("Enter card number:");
                                        String otherNum = SCN.next();
                                        if (otherNum.equals(cardNumber)) {
                                            System.out.println("You can't transfer money to the same account!");
                                            break;
                                        }
                                        if (otherNum.length() == 16) {
                                            int[] cardNumberInt = Bank.BankUtil.stringToIntArray(otherNum);

                                            if (Bank.BankUtil.checkCardNum(cardNumberInt)) {
                                                if (bank.getDatabase().containsRecord(otherNum)) {
                                                    System.out.println("Enter how much money you want to transfer:");
                                                    int toTransfer = SCN.nextInt();
                                                    if (bank.getDatabase().enoughMoney(cardNumber, toTransfer)) {
                                                        bank.getDatabase().doTransfer(cardNumber, otherNum, toTransfer);
                                                        System.out.println("Success!");
                                                    } else {
                                                        System.out.println("Not enough money!");
                                                    }
                                                } else {
                                                    System.out.println("Such a card does not exist.");
                                                }
                                            } else {
                                                System.out.println("\nProbably you made a mistake in the card number. Please try again!");
                                            }
                                        } else {
                                            System.out.println("\nProbably you made a mistake in the card number. Please try again!");
                                        }
                                    } catch (Exception e) {
                                        System.err.println("You typed something wrong, don't you?\n You wanted me to crash, don;t you?");
                                    }
                                }
                                case 4 -> {
                                    bank.getDatabase().deleteRecord(cardNumber);
                                    sub = 5;
                                }
                                case 5 -> System.out.println("\nYou have successfully logged out!");
                                case 0 -> {
                                    System.out.println("\nBye!");
                                    iWantToRun = false;
                                    return;
                                }
                            }
                        } while (sub != 5);

                    } else {
                        System.out.println("Wrong card number or PIN!");
                    }
                }
                //end login dialog
                case 0 -> {
                    System.out.println("Bye!");
                    iWantToRun = false;
                    return;
                }
            }

        } while (true);
    }

}
