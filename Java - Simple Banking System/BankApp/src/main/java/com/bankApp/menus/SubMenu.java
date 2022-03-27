package com.bankApp.menus;

public enum SubMenu {
    MENU("""

            1. Balance
            2. Add money
            3. Make a transfer
            4. Close your account
            5. Log out
            0. Exit Application
            """);

    private final String text;

    SubMenu(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
