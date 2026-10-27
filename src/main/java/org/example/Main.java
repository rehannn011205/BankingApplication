package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BankHandler bankDAO = new BankHandler();
        BranchHandler branchDAO = new BranchHandler();
        AdminHandler adminDAO = new AdminHandler();
        CustomerHandler customerDAO = new CustomerHandler();
        TransactionHandler transactionDAO = new TransactionHandler();
        LoanProcesses loanDAO = new LoanProcesses();

        while (true) {
            System.out.println("\n");
            System.out.println("   BANKING SYSTEM ===>     ");
            System.out.println("1 - Add Bank");
            System.out.println("2 - View Banks");
            System.out.println("3 - Add Branch");
            System.out.println("4 - View Branches");
            System.out.println("5 - Add Admin to Branch");
            System.out.println("6 - View Admins");
            System.out.println("7 - Add Customer");
            System.out.println("8 - View All Customers");
            System.out.println("9 - Remove Customer");
            System.out.println("10 - Check Account Balance");
            System.out.println("11 - Deposit Funds");
            System.out.println("12 - Withdraw Funds");
            System.out.println("13 - Transfer Funds");
            System.out.println("14 - Apply for Loan");
            System.out.println("15 - View All Loans");
            System.out.println("16 - Update Loan Status");
            System.out.println("0 - Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

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
                        System.out.print("Enter Email: ");
                    String cEmail = scanner.nextLine();
                        System.out.print("Enter Phone: ");
                        String cPhone = scanner.nextLine();
                    System.out.print("Enter Initial Balance: ");
                        double cBal = scanner.nextDouble();
                        System.out.print("Enter Branch ID: ");
                        int cBranchId = scanner.nextInt();
                    System.out.print("Enter Assigned Admin ID: ");
                            int cAdminId = scanner.nextInt();
                            customerDAO.addCustomer(cName, cEmail, cPhone, cBal, cBranchId, cAdminId);
                            break;
                case 8:
                            customerDAO.viewAllCustomers();
                    break;
                case 9:
                    System.out.print("Enter Customer ID to remove: ");
                        customerDAO.removeCustomer(scanner.nextInt());
                        break;
                case 10:
                            System.out.print("Enter Customer ID: ");
                            customerDAO.checkBalance(scanner.nextInt());
                            break;
                case 11:
                            System.out.print("Enter Customer ID: ");
                            int depCust = scanner.nextInt();
                            System.out.print("Enter Deposit Amount: ");
                      double depAmt = scanner.nextDouble();
                         transactionDAO.deposit(depCust, depAmt);
                    break;
                case 12:
                    System.out.print("Enter Customer ID: ");
                    int witCust = scanner.nextInt();
                    System.out.print("Enter Withdrawal Amount: ");
                    double witAmt = scanner.nextDouble();
                    transactionDAO.withdraw(witCust, witAmt);
                    break;
                case 13:
                    System.out.print("Enter Sender Customer ID: ");
                        int sender = scanner.nextInt();
                        System.out.print("Enter Receiver Customer ID: ");
                        int receiver = scanner.nextInt();
                    System.out.print("Enter Transfer Amount: ");
                            double tAmt = scanner.nextDouble();
                            transactionDAO.transfer(sender, receiver, tAmt);
                     break;
                case 14:
                        System.out.print("Enter Customer ID: ");
                        int lCust = scanner.nextInt();
                        System.out.print("Enter Loan Amount: ");
                            double lAmt = scanner.nextDouble();
                            System.out.print("Enter Interest Rate (%): ");
                            double lRate = scanner.nextDouble();
                            loanDAO.applyForLoan(lCust, lAmt, lRate);
                    break;
                case 15:
                    loanDAO.viewAllLoans();
                    break;
                case 16:
                    System.out.print("Enter Loan ID: ");
                        int loanId = scanner.nextInt();
                        scanner.nextLine();
                    System.out.print("Enter Status (APPROVED / REJECTED): ");
                       loanDAO.updateLoanStatus(loanId, scanner.nextLine());
                    break;
                case 0:
                           System.out.println("Exiting application.");
                        scanner.close();
                    System.exit(0);
                default:
                    System.out.println("! Invalid choice !");
            }
        }
    }
}