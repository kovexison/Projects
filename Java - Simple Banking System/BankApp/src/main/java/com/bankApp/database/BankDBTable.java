package com.bankApp.database;

import java.sql.*;

public abstract class BankDBTable implements SQLiteTable {
    protected String sqlConn;

    @Override
    public void createNewTable(SQLiteTable table) {
        //SQL Statement for creating new table
        String statement = "CREATE TABLE IF NOT EXISTS card (\n" +
                " id INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                " account_holder TEXT, \n" +
                " cnp TEXT, \n" +
                " number TEXT, \n" +
                " pin TEXT, \n" +
                " balance INTEGER DEFAULT 0\n" +
                ");";

        try (Connection connection = DriverManager.getConnection(table.getSqlConn());
             Statement statement1 = connection.createStatement()) {
            statement1.execute(statement); //create a new table if doesn't exists
            statement1.close();
            connection.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void insertData(String name, String cnp, String cardNum, String pin) {
        String insertSQL = "INSERT INTO card (account_holder, cnp,  number, pin) VALUES (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(getSqlConn());
             PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
            pstmt.setString(1, name);
            pstmt.setString(2, cnp);
            pstmt.setString(3, cardNum);
            pstmt.setString(4, pin);
            pstmt.executeUpdate();

            pstmt.close();
            connection.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public boolean containsCNP(String CNP) {
        boolean found = false;
        String sql = "SELECT * FROM card WHERE cnp = ?";

        try (Connection conn = BankDataBase.getInstance().connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, CNP);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                found = true;
            }
            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return found;
    }

    @Override
    public boolean containsRecord(String num, String pin) {
        boolean found = false;
        String sql = "SELECT * FROM card WHERE number = ? AND pin = ?";

        try (Connection conn = BankDataBase.getInstance().connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, num);
            pstmt.setString(2, pin);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                found = true;
            }
            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return found;
    }

    @Override
    public boolean containsRecord(String num) {
        boolean found = false;
        String sql = "SELECT * FROM card WHERE number = ?";

        try (Connection conn = BankDataBase.getInstance().connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, num);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                found = true;
            }
            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return found;
    }

    @Override
    public void deleteRecord(String cardNumber) {
        boolean done = false;
        String closeSqlStr = "DELETE FROM card WHERE number = ?";
        try {
            Connection connection = BankDataBase.getInstance().connect();
            PreparedStatement preparedStatement = connection.prepareStatement(closeSqlStr);
            preparedStatement.setString(1, cardNumber);
            preparedStatement.executeUpdate();
            done = true;
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (done) {
            System.out.println("The account has been closed!");
        }
    }

    @Override
    public String getSqlConn() {
        return sqlConn;
    }
}
