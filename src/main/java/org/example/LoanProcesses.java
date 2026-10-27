package org.example;
import java.sql.*;

public class LoanProcesses {

    public void applyForLoan(int custId, double loanAmount, double interestRate) {
        String sql = "INSERT INTO loan (cust_id, loan_amount, interest_rate, status) VALUES (?, ?, ?, 'PENDING')";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, custId);
            pstmt.setDouble(2, loanAmount);
            pstmt.setDouble(3, interestRate);

            if (pstmt.executeUpdate() > 0) {
                System.out.println("[Success] Loan application submitted successfully (Status: PENDING).");
            }
        } catch (SQLException e) {
            System.out.println("[Error] Could not apply for loan: " + e.getMessage());
        }
    }

    public void updateLoanStatus(int loanId, String status) {
        String sql = "UPDATE loan SET status = ? WHERE loan_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, status.toUpperCase());
            pstmt.setInt(2, loanId);

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("[Success] Loan status updated to " + status.toUpperCase() + ".");
            } else {
                System.out.println("[Warning] Loan ID not found.");
            }
        } catch (SQLException e) {
            System.out.println("[Error] Failed to update loan status: " + e.getMessage());
        }
    }

    public void viewAllLoans() {
        String sql = "SELECT loan_id, cust_id, loan_amount, interest_rate, status FROM loan";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n--- LOAN APPLICATIONS ---");
            System.out.printf("%-10s %-12s %-15s %-15s %-12s\n", "Loan ID", "Cust ID", "Amount", "Interest Rate", "Status");
            System.out.println("-------------------------------------------------------------------");
            while (rs.next()) {
                System.out.printf("%-10d %-12d %-15.2f %-15.2f %-12s\n",
                        rs.getInt("loan_id"),
                        rs.getInt("cust_id"),
                        rs.getDouble("loan_amount"),
                        rs.getDouble("interest_rate"),
                        rs.getString("status"));
            }
        } catch (SQLException e) {
            System.out.println("[Error] " + e.getMessage());
        }
    }
}
