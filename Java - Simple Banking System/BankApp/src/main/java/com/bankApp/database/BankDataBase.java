package com.bankApp.database;

import java.sql.*;

public class BankDataBase extends BankDBTable implements SQLiteDatabase {
    private static BankDataBase instance = null;

    private BankDataBase(String fileName) { //Singleton
        String URL = "jdbc:sqlite:";
        String DB_PATH = "./src/main/resources/bankDatabase/";
        this.sqlConn = URL + DB_PATH + fileName;
        try {
            Connection connection = DriverManager.getConnection(this.sqlConn);
            if (connection != null) {
                DatabaseMetaData meta = connection.getMetaData();
                createNewTable(this);
            }
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
        }
    }

    public static BankDataBase getInstance() {
        if (instance == null) {
            return new BankDataBase("database.s3db");
        }
        return instance;
    }

    @Override
    public void showTable() {
        String sqlString = "SELECT * FROM card";
        try {
            Connection conn = this.connect();
            PreparedStatement preparedStatement = conn.prepareStatement(sqlString);
            ResultSet rs = preparedStatement.executeQuery();
            System.out.println("\tTable: card");
            System.out.println("+----+-------------------------+------------------+------+---------------+-----------+");
            System.out.println("| ID | ACCOUNT HOLDER          |  CARD NUMBER     | PIN  | CNP           | BALANCE   |");
            System.out.println("+----+-------------------------+------------------+------+---------------+-----------+");
            while (rs.next()) {
                String id = String.format("| %-2d |",rs.getInt("id"));
                String accountHolder = String.format(" %-24s|" ,rs.getString("account_holder"));
                String cardNumber = String.format(" %16s |",rs.getString("number"));
                String pin = String.format(" %4s |",rs.getString("pin"));
                String cnp = String.format(" %13s |", rs.getString("cnp"));
                String balance = String.format(" %-9d |",rs.getInt("balance"));
                System.out.println(id + accountHolder + cardNumber + pin + cnp + balance);
                System.out.println("+----+-------------------------+------------------+------+---------------+-----------+");
            }
            rs.close();
            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getSqlConn() {
        return sqlConn;
    }

    @Override
    public Connection connect() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(this.sqlConn);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return connection;
    }

    public String getCardHolderName(String cardNum) {
        String sql = "SELECT account_holder FROM card WHERE number = ?";
        try {
            Connection connection = this.connect();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, cardNum);
            ResultSet rs = preparedStatement.executeQuery();
            String pulaMare = rs.getString("account_holder");

            rs.close();
            preparedStatement.close();
            connection.close();
            return pulaMare;
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
        }
        return null;
    }

    public boolean enoughMoney(String cardNum, int sum) {
        boolean enough = false;
        String sqlString = "SELECT balance FROM card WHERE number = ?";
        try (Connection connection = this.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlString)) {
            preparedStatement.setString(1, cardNum);
            ResultSet resultSet = preparedStatement.executeQuery();
            int balance = resultSet.getInt("balance");
            if (balance >= sum) {
                enough = true;
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return enough;
    }

    public boolean doTransfer(String senderNum, String receiverNum, int balance) {
        boolean done = false;
        StringBuilder balanceStr = new StringBuilder().append(balance);
        String sqlSenderString = "UPDATE card SET balance = balance - ? WHERE number = ?";
        String sqlReceiverString = "UPDATE card SET balance = balance + ? WHERE number = ?";
        Connection conn = null;
        try {
            conn = this.connect();
            PreparedStatement preparedStatement = conn.prepareStatement(sqlSenderString);
            conn.setAutoCommit(false);

            preparedStatement.setString(1, balanceStr.toString());
            preparedStatement.setString(2, senderNum);
            preparedStatement.executeUpdate();

            PreparedStatement pst1 = conn.prepareStatement(sqlReceiverString);
            pst1.setString(1, balanceStr.toString());
            pst1.setString(2, receiverNum);
            pst1.executeUpdate();

            conn.commit();

            done = true;
            pst1.close();
            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    System.err.print("An unknown error occurred. Transaction is being rolled back.\n Your balance wasn't affected.");
                    //e.printStackTrace();
                    conn.rollback();
                } catch (SQLException exception) {
                    e.printStackTrace();
                }
            }
        }
        return done;
    }

    public void printBalance(String num, String pin) {
        String balanceSQL = "SELECT balance FROM card WHERE number = ? AND pin = ?";
        try (Connection conn = this.connect();
             PreparedStatement prep = conn.prepareStatement(balanceSQL)) {
            prep.setString(1, num);
            prep.setString(2, pin);
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                System.out.println("Balance: " + rs.getInt("balance"));
            }
            rs.close();
            prep.close();
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void addMoney(int sum, String num, String pin) {
        String updBalanceSql = "UPDATE card SET balance = balance + ? WHERE number = ? AND pin = ?";
        Connection conn = null;
        try {
            conn = this.connect();
            PreparedStatement pst = conn.prepareStatement(updBalanceSql);
            conn.setAutoCommit(false);

            pst.setInt(1, sum);
            pst.setString(2, num);
            pst.setString(3, pin);
            pst.executeUpdate();

            conn.commit();
            System.out.println("Income was added!");
            pst.close();
            conn.close();
        } catch (SQLException exception) {
            if (conn != null) {
                try {
                    System.err.print("An unknown error occurred. Transaction is being rolled back.\n Your balance wasn't affected.");
                    exception.printStackTrace();
                    conn.rollback();
                } catch (SQLException excep) {
                    excep.printStackTrace();
                }
            }
        }
    }

}
