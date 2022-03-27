package com.bankApp.userTypes;

public class User extends Person {

    public User(String firstName, String lastName, String cnp) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.cnp = cnp;
    }

    public String getName() {
        return this.firstName + " " + this.lastName;
    }
}
