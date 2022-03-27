package com.bankApp.menus;

public enum AdminMainMenu {
    MENU("\n1. Show database\n" +
            "2. Log out\n" +
            "0. Exit\n");

    private final String text;

    AdminMainMenu(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
