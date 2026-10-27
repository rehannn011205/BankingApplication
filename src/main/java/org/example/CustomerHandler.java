package org.example;
import java.sql.*;

public class CustomerHandler {

    public void addCustomer(String name, String email, String phone, double balance, int branchId,int adminId) {
        String sql = "INSERT INTO customer (cust_name, email, phone, balance, branch_id,admin_id) VALUES (?, ?, ?, ?, ?,?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, phone);
            pstmt.setDouble(4, balance);
            pstmt.setInt(5, branchId);
            pstmt.setInt(6, adminId);

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("[Success] Customer added successfully!");
            }
        } catch (SQLException e) {
            System.out.println("[Error] Failed to add customer: " + e.getMessage());
        }
    }

    public void removeCustomer(int custId) {
        String sql = "DELETE FROM customer WHERE cust_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, custId);
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("[Success] Customer removed successfully!");
            } else {
                System.out.println("[Warning] Customer ID not found.");
            }
        } catch (SQLException e) {
            System.out.println("[Error] Failed to remove customer: " + e.getMessage());
        }
    }

    public void viewAllCustomers() {
        String sql = "SELECT cust_id, cust_name, email, phone, balance, branch_id FROM customer";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n--- CUSTOMER LIST ---");
            System.out.printf("%-10s %-20s %-25s %-15s %-12s %-8s\n", "ID", "Name", "Email", "Phone", "Balance", "Branch ID");
            System.out.println("-----------------------------------------------------------------------------------------");
            while (rs.next()) {
                System.out.printf("%-10d %-20s %-25s %-15s %-12.2f %-8d\n",
                        rs.getInt("cust_id"),
                        rs.getString("cust_name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getDouble("balance"),
                        rs.getInt("branch_id"));
            }
        } catch (SQLException e) {
            System.out.println("[Error] Could not retrieve customers: " + e.getMessage());
        }
    }

    public void checkBalance(int custId) {
        String sql = "SELECT cust_name, balance FROM customer WHERE cust_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, custId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println("\nAccount Holder: " + rs.getString("cust_name"));
                System.out.println("Current Balance: $" + rs.getDouble("balance"));
            } else {
                System.out.println("[Warning] Customer ID not found.");
            }
        } catch (SQLException e) {
            System.out.println("[Error] " + e.getMessage());
        }
    }
}