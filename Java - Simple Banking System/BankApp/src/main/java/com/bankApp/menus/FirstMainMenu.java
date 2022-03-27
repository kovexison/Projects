package com.bankApp.menus;

public enum FirstMainMenu {
    MENU("\n1. Create an account" + "\n" +
            "2. Log into account" + "\n" +
            "0. Exit\n");

    private final String text;

    FirstMainMenu(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
