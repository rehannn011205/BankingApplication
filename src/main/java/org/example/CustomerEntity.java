package org.example;
import java.sql.*;

public class CustomerEntity{
    public void addCustomer(String name, String email){
        try(Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement("INSERT INTO customer (customer_name, customer_email) VALUES (?, ?)")) {
               pst.setString(1, name);
            pst.setString(2, email);

            if(pst.executeUpdate()>0){
                System.out.println("Customer registered successfully!");
            }
        }catch(SQLException e) {
            System.out.println("Failed to add customer: " + e.getMessage());
        }
    }

    public void viewCustomers() {
          String sql = "SELECT * FROM customer";
        try(Connection conn = DBConnection.getConnection();
                Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            //**** AI Generated =>
            System.out.println("\n<- CUSTOMERS ->");
            System.out.printf("%-12s %-20s %-25s\n", "Customer ID", "Name", "Email");
            System.out.println("---------------------------------------------------------");
            while (rs.next()) {
                System.out.printf("%-12d %-20s %-25s\n",
                        rs.getInt("customer_id"),
                        rs.getString("customer_name"),
                        rs.getString("customer_email"));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
}