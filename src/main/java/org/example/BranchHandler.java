package org.example;
import java.sql.*;

public class BranchHandler {
    public void addBranch(String branchName, int bankId, String address) {
        try(Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("insert INTO branch(branch_name, bank_id, address) values (?, ?, ?)")) {
                pstmt.setString(1, branchName);
                pstmt.setInt(2, bankId);
            pstmt.setString(3, address);
            if(pstmt.executeUpdate() >0){
                System.out.println("Branch added successfully!");
            }
        }catch(SQLException e){
            System.out.println("Failed to add branch: " + e.getMessage());
        }
    }

    public void viewBranches() {
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT b.branch_id, b.branch_name, bk.bank_name, b.address FROM branch b JOIN bank bk ON b.bank_id = bk.bank_id")) {

            // *** AI Generated ==>
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
            System.out.println(e.getMessage());
        }
    }
}
