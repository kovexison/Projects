package com.bankApp.database;

import java.sql.Connection;

@FunctionalInterface
public interface SQLiteDatabase {
    Connection connect();
}
