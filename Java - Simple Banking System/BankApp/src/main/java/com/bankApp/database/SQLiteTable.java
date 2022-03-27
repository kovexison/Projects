package com.bankApp.database;

public interface SQLiteTable {
    void createNewTable(SQLiteTable table);

    void insertData(String name, String cnp, String cardNum, String pin);

    boolean containsRecord(String num, String pin);

    boolean containsRecord(String num);

    void deleteRecord(String cardNumber);

    void showTable();

    String getSqlConn();
}
