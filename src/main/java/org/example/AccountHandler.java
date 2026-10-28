package org.example;
import java.sql.*;

public class AccountHandler {
    public void createAccount(int customerId, int branchId, int adminId, double initialDeposit) {

        try(Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("insert into account (customer_id, branch_id, admin_id, balance) values (?, ?, ?, ?)")) {
            pstmt.setInt(1, customerId);
               pstmt.setInt(2, branchId);
             pstmt.setInt(3, adminId);
                pstmt.setDouble(4, initialDeposit);

               int rowsAffected=pstmt.executeUpdate();
            if(rowsAffected >0){
                System.out.println("Bank Account created successfully for Customer ID: " + customerId);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void viewAllAccounts() {
        try(Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("select a.account_id, c.customer_name, b.branch_name, ad.admin_name, a.balance from account a JOIN customer c on a.customer_id = c.customer_id join branch b ON a.branch_id = b.branch_id left JOIN admin ad ON a.admin_id = ad.admin_id")) {

            // ***AI Generated ===>
            System.out.println("\n--- BANK ACCOUNTS ---");
            System.out.printf("%-12s %-18s %-18s %-18s %-12s\n", "Account ID", "Customer Name", "Branch Name", "Admin Name", "Balance");
            System.out.println("--------------------------------------------------------------------------------------------------");
            boolean hasRows = false;
            while (rs.next()) {
                hasRows = true;
                System.out.printf("%-12d %-18s %-18s %-18s %-12.2f\n",
                        rs.getInt("account_id"),
                        rs.getString("customer_name"),
                        rs.getString("branch_name"),
                        rs.getString("admin_name"),
                        rs.getDouble("balance"));
            }

            if(!hasRows){System.out.println("No bank accounts found in the database.");
            }

        }catch(SQLException e){
            System.out.println("[Error] Failed to retrieve accounts: " + e.getMessage());
        }
    }

    public void checkBalance(int accountId){
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("select c.customer_name, a.balance from account a join customer c ON a.customer_id = c.customer_id where a.account_id = ?")) {
            pstmt.setInt(1, accountId);
            ResultSet rs = pstmt.executeQuery();

            if(rs.next()){
                    System.out.println("\nAccount Holder: " + rs.getString("customer_name"));
                System.out.println("Current Balance: $" + rs.getDouble("balance"));}
            else{
                    System.out.println("Account ID not found.");
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
}