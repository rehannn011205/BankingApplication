package org.example;
import java.sql.*;

public class AdminHandler {
    public void addAdmin(String adminName, int branchId, String email) {
        String sql = "INSERT into admin (admin_name, branch_id, email) VALUES (?,?,?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setString(1, adminName);
                pst.setInt(2, branchId);
            pst.setString(3, email);
            if(pst.executeUpdate() > 0){
                System.out.println("* Admin added to branch successfully!");
            }
            } catch (SQLException e) {
                System.out.println("* Failed to add admin: " + e.getMessage());
            }
    }

    public void viewAdmins() {
        String sql = "select a.admin_id, a.admin_name, b.branch_name, a.email from admin a JOIN branch b ON a.branch_id = b.branch_id";
        try (Connection conn = DBConnection.getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("\n--- BRANCH ADMINS ---");
            System.out.printf("%-10s %-20s %-20s %-25s\n", "Admin ID", "Admin Name", "Branch Name", "Email");
            System.out.println("-----------------------------------------------------------------------");
            while (rs.next()) {
                System.out.printf("%-10d %-20s %-20s %-25s\n",
                        rs.getInt("admin_id"),
                        rs.getString("admin_name"),
                        rs.getString("branch_name"),
                        rs.getString("email"));
            }
        } catch (SQLException e) {
            System.out.println("[Error] " + e.getMessage());
        }
    }
}
