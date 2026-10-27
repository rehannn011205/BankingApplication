package org.example;

import java.sql.*;

public class BankHandler {
    public void addBank(String bankName) {
        String sql = "INSERT INTO bank (bank_name) VALUES (?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, bankName);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("[Success] Bank added successfully!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to view all existing banks in a formatted table
    public void viewBanks() {
        String sql = "SELECT bank_id, bank_name FROM bank";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n--- BANKS LIST ---");
            System.out.printf("%-10s %-25s\n", "Bank ID", "Bank Name");
            System.out.println("-----------------------------------");

            boolean hasRows = false;
            while (rs.next()) {
                hasRows = true;
                System.out.printf("%-10d %-25s\n",
                        rs.getInt("bank_id"),
                        rs.getString("bank_name"));
            }

            if (!hasRows) {
                System.out.println("No banks found in the database.");
            }

        } catch (SQLException e) {
            System.out.println("[Error] Failed to retrieve banks: " + e.getMessage());
        }
    }
    public void addBranch(String branchName, int bankId, String address) {
        String sql = "INSERT INTO branch (branch_name, bank_id, address) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, branchName);
            pstmt.setInt(2, bankId);
            pstmt.setString(3, address);
            if (pstmt.executeUpdate() > 0) {
                System.out.println("[Success] Branch added successfully!");
            }
        } catch (SQLException e) {
            System.out.println("[Error] Failed to add branch: " + e.getMessage());
        }
    }

    public void viewBranches() {
        String sql = "SELECT b.branch_id, b.branch_name, bk.bank_name, b.address FROM branch b JOIN bank bk ON b.bank_id = bk.bank_id";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("\n--- BRANCHES ---");
            System.out.printf("%-10s %-20s %-20s %-25s\n", "Branch ID", "Branch Name", "Bank Name", "Address");
            System.out.println("-----------------------------------------------------------------------");
            while (rs.next()) {
                System.out.printf("%-10d %-20s %-20s %-25s\n",
                        rs.getInt("branch_id"),
                        rs.getString("branch_name"),
                        rs.getString("bank_name"),
                        rs.getString("address"));
            }
        } catch (SQLException e) {
            System.out.println("[Error] " + e.getMessage());
        }
    }
}
