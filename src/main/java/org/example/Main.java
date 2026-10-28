package org.example;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        BankHandler bankDAO = new BankHandler();
        BranchHandler branchDAO = new BranchHandler();
        AdminHandler adminDAO = new AdminHandler();
        CustomerEntity customerDAO = new CustomerEntity();
        AccountHandler accountDAO = new AccountHandler();
        TransactionHandler transactionDAO = new TransactionHandler();
        LoanProcesses loanDAO = new LoanProcesses();

        // *** AI Generated ===>
        while (true) {
            System.out.println("\n========================================");
            System.out.println("   BANKING SYSTEM (POSTGRESQL CONSOLE)  ");
            System.out.println("========================================");
            System.out.println("--- BANK & BRANCH & ADMIN ---");
            System.out.println("1. Add Bank");
            System.out.println("2. View Banks");
            System.out.println("3. Add Branch");
            System.out.println("4. View Branches");
            System.out.println("5. Add Admin to Branch");
            System.out.println("6. View Admins");
            System.out.println("--- CUSTOMERS & ACCOUNTS ---");
            System.out.println("7. Register New Customer");
            System.out.println("8. View All Customers");
            System.out.println("9. Create Bank Account (for Customer)");
            System.out.println("10. View All Bank Accounts");
            System.out.println("11. Check Account Balance");
            System.out.println("--- TRANSACTIONS ---");
            System.out.println("12. Deposit Funds");
            System.out.println("13. Withdraw Funds");
            System.out.println("14. Transfer Funds");
            System.out.println("--- LOANS ---");
            System.out.println("15. Apply for Loan");
            System.out.println("16. View All Loans");
            System.out.println("17. Update Loan Status");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Bank Name: ");
                    bankDAO.addBank(scanner.nextLine());
                    break;
                case 2:
                    bankDAO.viewBanks();
                    break;
                case 3:
                    System.out.print("Enter Branch Name: ");
                    String bName = scanner.nextLine();
                    System.out.print("Enter Bank ID: ");
                    int bId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Address: ");
                    String addr = scanner.nextLine();
                    branchDAO.addBranch(bName, bId, addr);
                    break;
                case 4:
                    branchDAO.viewBranches();
                    break;
                case 5:
                    System.out.print("Enter Admin Name: ");
                    String adName = scanner.nextLine();
                    System.out.print("Enter Branch ID: ");
                    int adBranchId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Admin Email: ");
                    String adEmail = scanner.nextLine();
                    adminDAO.addAdmin(adName, adBranchId, adEmail);
                    break;
                case 6:
                    adminDAO.viewAdmins();
                    break;
                case 7:
                    System.out.print("Enter Customer Name: ");
                    String cName = scanner.nextLine();
                    System.out.print("Enter Customer Email: ");
                    String cEmail = scanner.nextLine();
                    customerDAO.addCustomer(cName, cEmail);
                    break;
                case 8:
                    customerDAO.viewCustomers();
                    break;
                case 9:
                    System.out.print("Enter Customer ID: ");
                    int accCustId = scanner.nextInt();
                    System.out.print("Enter Branch ID: ");
                    int accBranchId = scanner.nextInt();
                    System.out.print("Enter Admin ID: ");
                    int accAdminId = scanner.nextInt();
                    System.out.print("Enter Initial Deposit Amount: ");
                    double initialDeposit = scanner.nextDouble();
                    accountDAO.createAccount(accCustId, accBranchId, accAdminId, initialDeposit);
                    break;
                case 10:
                    accountDAO.viewAllAccounts();
                    break;
                case 11:
                    System.out.print("Enter Account ID: ");
                    accountDAO.checkBalance(scanner.nextInt());
                    break;
                case 12:
                    System.out.print("Enter Account ID: ");
                    int depAcc = scanner.nextInt();
                    System.out.print("Enter Deposit Amount: ");
                    double depAmt = scanner.nextDouble();
                    transactionDAO.deposit(depAcc, depAmt);
                    break;
                case 13:
                    System.out.print("Enter Account ID: ");
                    int witAcc = scanner.nextInt();
                    System.out.print("Enter Withdrawal Amount: ");
                    double witAmt = scanner.nextDouble();
                    transactionDAO.withdraw(witAcc, witAmt);
                    break;
                case 14:
                    System.out.print("Enter Sender Account ID: ");
                    int sender = scanner.nextInt();
                    System.out.print("Enter Receiver Account ID: ");
                    int receiver = scanner.nextInt();
                    System.out.print("Enter Transfer Amount: ");
                    double tAmt = scanner.nextDouble();
                    transactionDAO.transfer(sender, receiver, tAmt);
                    break;
                case 15:
                    System.out.print("Enter Account ID: ");
                    int lAcc = scanner.nextInt();
                    System.out.print("Enter Loan Amount: ");
                    double lAmt = scanner.nextDouble();
                    System.out.print("Enter Interest Rate (%): ");
                    double lRate = scanner.nextDouble();
                    loanDAO.applyForLoan(lAcc, lAmt, lRate);
                    break;
                case 16:
                    loanDAO.viewAllLoans();
                    break;
                case 17:
                    System.out.print("Enter Loan ID: ");
                    int loanId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Status (APPROVED / REJECTED): ");
                    loanDAO.updateLoanStatus(loanId, scanner.nextLine());
                    break;
                case 0:
                    System.out.println("Exiting application. Goodbye!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("[Warning] Invalid choice. Please try again.");
            }
        }
    }
}