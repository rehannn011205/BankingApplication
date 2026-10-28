package org.example;
import java.sql.*;

public class TransactionHandler {
    public void deposit(int accountId, double amount) {
        Connection conn = null;
        try{
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);
            try(PreparedStatement p1 = conn.prepareStatement("update account SET balance = balance + ? WHERE account_id = ?");
                 PreparedStatement p2 = conn.prepareStatement("insert INTO transactions(account_id, txn_type, amount) VALUES (?, 'DEPOSIT', ?)")) {
                   p1.setDouble(1, amount);
                p1.setInt(2, accountId);
                if(p1.executeUpdate()==0){
                    System.out.println("[Error] Account not found.");
                    conn.rollback();
                    return;
                }
                p2.setInt(1, accountId);
                p2.setDouble(2, amount);
                p2.executeUpdate();
                conn.commit();
                System.out.println("deposit completed successfully!");
            }
        } catch (SQLException e) {
            if (conn != null) try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            System.out.println("Deposit failed: " + e.getMessage());
        } finally {
            if (conn != null) try { conn.setAutoCommit(true); conn.close(); } catch (SQLException ex) { ex.printStackTrace(); }
        }
    }

    public void withdraw(int accountId, double amount) {
        String updateBal = "update account SET balance = balance - ? WHERE account_id = ? AND balance >= ?";

        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            try (PreparedStatement p1 = conn.prepareStatement(updateBal)) {
                    p1.setDouble(1, amount);
                    p1.setInt(2, accountId);
                p1.setDouble(3, amount);
                if (p1.executeUpdate() == 0) {
                    System.out.println("Withdrawal failed. Insufficient balance or invalid account ID.");
                    conn.rollback();
                    return;
                }
                conn.commit();
                System.out.println("Withdrawal completed successfully!");
            }
        } catch (SQLException e) {
            if (conn != null) try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            System.out.println("Withdrawal failed: " + e.getMessage());
        } finally {
            if (conn != null) try { conn.setAutoCommit(true); conn.close(); } catch (SQLException ex) { ex.printStackTrace(); }
        }
    }

    public void transfer(int senderAccId, int receiverAccId, double amount) {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            try (PreparedStatement p1 = conn.prepareStatement("UPDATE account SET balance = balance - ? WHERE account_id = ? AND balance >= ?")) {
                p1.setDouble(1, amount);
                    p1.setInt(2, senderAccId);
                    p1.setDouble(3, amount);
                if (p1.executeUpdate() == 0) {
                    System.out.println("transfer failed: Insufficient funds or invalid sender account.");
                    conn.rollback();
                    return;
                }
            }
            try (PreparedStatement p2 = conn.prepareStatement("UPDATE account SET balance = balance + ? WHERE account_id = ?")) {
                   p2.setDouble(1, amount);
                p2.setInt(2, receiverAccId);
                if(p2.executeUpdate()==0){
                    System.out.println("transfer failed: Receiver account not found.");
                    conn.rollback();
                    return;
                }
            }
            conn.commit();
            System.out.println("transfer completed successfully!");

        } catch(SQLException e){
            if(conn!=null) try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            System.out.println("transfer failed: " + e.getMessage());
        } finally {
            if (conn != null) try { conn.setAutoCommit(true); conn.close(); } catch (SQLException ex) { ex.printStackTrace(); }
        }
    }
}