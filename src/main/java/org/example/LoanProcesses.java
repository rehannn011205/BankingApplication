package org.example;
import java.sql.*;

public class LoanProcesses {
    public void applyForLoan(int accountId, double loanAmount, double interestRate) {
        try(Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("insert INTO loan (account_id, loan_amount, interest_rate, status) VALUES (?, ?, ?, 'PENDING')")) {
                pstmt.setInt(1, accountId);
                pstmt.setDouble(2, loanAmount);
            pstmt.setDouble(3, interestRate);
            if(pstmt.executeUpdate()>0){
                System.out.println("loan application submitted successfully !");
            }
        }catch(SQLException e){System.out.println(e.getMessage());
        }
    }

    public void updateLoanStatus(int loanId, String status){
        String sql= "update loan SET status = ? WHERE loan_id = ?";
        try(Connection conn =DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, status.toUpperCase());
                pstmt.setInt(2, loanId);
                int rows=pstmt.executeUpdate();
            if(rows>0){
                System.out.println("loan status updated to -" + status.toUpperCase() + ".");}
            else{
                   System.out.println("loan Id not found.");
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void viewAllLoans(){
        try(Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
                   ResultSet rs = stmt.executeQuery("select loan_id, account_id, loan_amount, interest_rate, status FROM loan")) {

            // ***AI Generated ===>
            System.out.println("\n--- LOAN APPLICATIONS ---");
            System.out.printf("%-10s %-12s %-15s %-15s %-12s\n", "Loan ID", "Account ID", "Amount", "Interest Rate", "Status");
            System.out.println("-------------------------------------------------------------------");
            while (rs.next()) {
                System.out.printf("%-10d %-12d %-15.2f %-15.2f %-12s\n",
                        rs.getInt("loan_id"),
                        rs.getInt("account_id"),
                        rs.getDouble("loan_amount"),
                        rs.getDouble("interest_rate"),
                        rs.getString("status"));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
