package org.example;

import java.sql.*;

public class TransactionHandler {

    public void deposit(int custId, double amount) {
        String updateBal = "UPDATE customer SET balance = balance + ? WHERE cust_id = ?";
        String logTxn = "INSERT INTO transactions (cust_id, txn_type, amount) VALUES (?, 'DEPOSIT', ?)";

        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            try (PreparedStatement p1 = conn.prepareStatement(updateBal);
                 PreparedStatement p2 = conn.prepareStatement(logTxn)) {

                p1.setDouble(1, amount);
                p1.setInt(2, custId);
                if (p1.executeUpdate() == 0) {
                    System.out.println("[Error] Customer not found.");
                    conn.rollback();
                    return;
                }

                p2.setInt(1, custId);
                p2.setDouble(2, amount);
                p2.executeUpdate();

                conn.commit();
                System.out.println("[Success] Deposit completed successfully!");
            }
        } catch (SQLException e) {
            rollbackQuietly(conn);
            System.out.println("[Error] Deposit failed: " + e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }

    public void withdraw(int custId, double amount) {
        String updateBal = "UPDATE customer SET balance = balance - ? WHERE cust_id = ? AND balance >= ?";
        String logTxn = "INSERT INTO transactions (cust_id, txn_type, amount) VALUES (?, 'WITHDRAWAL', ?)";

        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            try (PreparedStatement p1 = conn.prepareStatement(updateBal);
                 PreparedStatement p2 = conn.prepareStatement(logTxn)) {

                p1.setDouble(1, amount);
                p1.setInt(2, custId);
                p1.setDouble(3, amount);

                if (p1.executeUpdate() == 0) {
                    System.out.println("[Error] Withdrawal failed. Either account not found or insufficient balance.");
                    conn.rollback();
                    return;
                }

                p2.setInt(1, custId);
                p2.setDouble(2, amount);
                p2.executeUpdate();

                conn.commit();
                System.out.println("[Success] Withdrawal completed successfully!");
            }
        } catch (SQLException e) {
            rollbackQuietly(conn);
            System.out.println("[Error] Withdrawal failed: " + e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }

    public void transfer(int senderId, int receiverId, double amount) {
        String deductSender = "UPDATE customer SET balance = balance - ? WHERE cust_id = ? AND balance >= ?";
        String addReceiver = "UPDATE customer SET balance = balance + ? WHERE cust_id = ?";
        String logTxn = "INSERT INTO transactions (cust_id, txn_type, amount) VALUES (?, 'TRANSFER', ?)";

        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            // 1. Deduct from Sender
            try (PreparedStatement p1 = conn.prepareStatement(deductSender)) {
                p1.setDouble(1, amount);
                p1.setInt(2, senderId);
                p1.setDouble(3, amount);
                if (p1.executeUpdate() == 0) {
                    System.out.println("[Error] Transfer failed: Invalid sender ID or insufficient funds.");
                    conn.rollback();
                    return;
                }
            }

            // 2. Add to Receiver
            try (PreparedStatement p2 = conn.prepareStatement(addReceiver)) {
                p2.setDouble(1, amount);
                p2.setInt(2, receiverId);
                if (p2.executeUpdate() == 0) {
                    System.out.println("[Error] Transfer failed: Receiver ID not found. Rolling back.");
                    conn.rollback();
                    return;
                }
            }

            // 3. Log transaction for sender
            try (PreparedStatement p3 = conn.prepareStatement(logTxn)) {
                p3.setInt(1, senderId);
                p3.setDouble(2, amount);
                p3.executeUpdate();
            }

            conn.commit();
            System.out.println("[Success] Transfer completed successfully!");

        } catch (SQLException e) {
            rollbackQuietly(conn);
            System.out.println("[Error] Transfer failed: " + e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }

    private void rollbackQuietly(Connection conn) {
        if (conn != null) {
            try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
        }
    }

    private void closeConnection(Connection conn) {
        if (conn != null) {
            try { conn.setAutoCommit(true); conn.close(); } catch (SQLException ex) { ex.printStackTrace(); }
        }
    }
}