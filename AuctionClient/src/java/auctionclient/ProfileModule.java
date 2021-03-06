/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auctionclient;

import ejb.session.stateless.AddressEntityControllerRemote;
import ejb.session.stateless.AuctionEntityControllerRemote;
import ejb.session.stateless.BidEntityControllerRemote;
import ejb.session.stateless.CreditPackageEntityControllerRemote;
import ejb.session.stateless.CreditTransactionEntityControllerRemote;
import ejb.session.stateless.CustomerEntityControllerRemote;
import ejb.session.stateless.TimerSessionBeanRemote;
import entity.AddressEntity;
import entity.BidEntity;
import entity.CreditPackageEntity;
import entity.CreditTransactionEntity;
import entity.CustomerEntity;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import util.enumeration.TransactionTypeEnum;
import util.exception.AddressAlreadyExistsException;
import util.exception.AddressNotFoundException;
import util.exception.CreditPackageNotFoundException;
import util.exception.CustomerNotFoundException;
import util.exception.GeneralException;
import util.exception.IncorrectPasswordException;

/*
 *
 * @author Amber
 */
public class ProfileModule {

    private CustomerEntityControllerRemote customerEntityControllerRemote;
    private CreditPackageEntityControllerRemote creditPackageEntityControllerRemote;
    private BidEntityControllerRemote bidEntityControllerRemote;
    private AuctionEntityControllerRemote auctionEntityControllerRemote;
    private CreditTransactionEntityControllerRemote creditTransactionEntityControllerRemote;
    private AddressEntityControllerRemote addressEntityControllerRemote;
    private TimerSessionBeanRemote timerSessionBean;

    private CustomerEntity customer;

    public ProfileModule() {
    }

    public ProfileModule(CustomerEntityControllerRemote customerEntityControllerRemote, CreditPackageEntityControllerRemote creditPackageEntityControllerRemote, BidEntityControllerRemote bidEntityControllerRemote, AuctionEntityControllerRemote auctionEntityControllerRemote, CreditTransactionEntityControllerRemote creditTransactionEntityControllerRemote, AddressEntityControllerRemote addressEntityControllerRemote, TimerSessionBeanRemote timerSessionBean, CustomerEntity customer) {
        this.customerEntityControllerRemote = customerEntityControllerRemote;
        this.creditPackageEntityControllerRemote = creditPackageEntityControllerRemote;
        this.bidEntityControllerRemote = bidEntityControllerRemote;
        this.auctionEntityControllerRemote = auctionEntityControllerRemote;
        this.creditTransactionEntityControllerRemote = creditTransactionEntityControllerRemote;
        this.addressEntityControllerRemote = addressEntityControllerRemote;
        this.timerSessionBean = timerSessionBean;
        this.customer = customer;

    }

    public void viewProfile() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        System.out.println("");
        System.out.println("******* [Customer] Profile Details *******");
        System.out.println("Name: " + customer.getFirstName() + " " + customer.getLastName());
        System.out.println("Email: " + customer.getEmail());
        System.out.println("Contact Number: " + customer.getContactNumber());
        System.out.println("");
        System.out.println("1. Update your profile");
        System.out.println("2. Back");
        System.out.println("Enter number of the operation that you want to perform");

        while (response < 1 || response > 2) {
            try {
                System.out.print("->");
                response = scanner.nextInt();
                if (response == 1) {
                    updateProfile();

                } else if (response == 2) {
                    break;
                } else {
                    System.err.println("[Warning] Invalid Option! Please try again!");
                }
            } catch (InputMismatchException ex) {
                System.err.println("[Warning] Invalid Type!");
            }
        }

    }

    public void updateProfile() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        System.out.println("");
        System.out.println("******* [Customer] Profile Update *******");
        System.out.println("1. Update contact number");
        System.out.println("2. Change Password");
        System.out.println("3. Back");
        System.out.println("Enter number of the operation that you want to perform");
        while (response < 1 || response > 3) {
            System.out.print("->");
            response = scanner.nextInt();
            if (response == 1) {
                scanner.nextLine();
                System.out.println("Your current contact number is " + customer.getContactNumber());
                System.out.print("Enter new contact number\n->");
                customer.setContactNumber(scanner.nextLine().trim());
                System.out.println("[System] New contact number updated successfullly!\n");
                viewProfile();
            } else if (response == 2) {

                try {
                    scanner.nextLine();
                    System.out.print("Enter your email\n->");
                    String email = scanner.nextLine().trim();
                    System.out.print("Enter current password\n->");
                    String oldpassword = scanner.nextLine().trim();
                    System.out.print("Enter new password (more than 6 characters)\n->");
                    String newpassword = scanner.nextLine().trim();
                    System.out.print("Re-enter password\n->");
                    String reenterpassword = scanner.nextLine().trim();
                    if (newpassword.equals(reenterpassword)) {
                        Long id = customerEntityControllerRemote.retrieveCustomerByEmail(email).getId();
                        customerEntityControllerRemote.changePassword(oldpassword, newpassword, id);
                        System.out.println("[System] New password changed successfully!\n");
                        viewProfile();
                    } else {
                        System.err.println("[Warning] New passwords mismatched!");
                        updateProfile();
                    }
                } catch (IncorrectPasswordException | CustomerNotFoundException | GeneralException ex) {
                    System.err.println("[Warning] " + ex.getMessage() + "!\n");
                    viewProfile();
                } catch (InputMismatchException ex) {
                    System.err.println("[Warning] Invalid Type!");
                    viewProfile();
                }
            } else if (response == 3) {
                break;
            } else {
                System.err.println("[Warning] Invalid Option! Please try again!");
                viewProfile();
            }
        }
    }

    public void manageCreditPackage() {
        try {

            Scanner scanner = new Scanner(System.in);
            Integer response = 0;
            System.out.println("");
            System.out.println("******* [Customer] Credit Managing *******");
            System.out.println("1. View Credit Balance");
            System.out.println("2. Purchase New Credit Package");
            System.out.println("3. View Credit Transaction History");
            System.out.println("4. Back");
            System.out.println("Enter number of the operation that you want to perform");
            while (response < 1 || response > 4) {
                System.out.print("->");
                response = scanner.nextInt();
                if (response == 1) {
                    System.out.println("Your current credit balance is " + customer.getCreditBalance());
                    manageCreditPackage();
                } else if (response == 2) {
                    System.out.println("");
                    System.out.println("******** [Customer] View All Available Credit Pakcages *******");
                    System.out.printf("%5s%30s%15s%15s\n", "ID|", "Package name|", "Package Value|", "Package Price");
                    //list packages
                    //retrieve all package details and loop to list info
                    List<CreditPackageEntity> creditpackagelist = creditPackageEntityControllerRemote.viewAllCreditPackage();
                    for (CreditPackageEntity creditpackage : creditpackagelist) {
                        if (creditpackage.getIsDisabled()) {
                            continue;
                        }
                        System.out.printf("%5s%30s%15s%15s\n", creditpackage.getId(), creditpackage.getName(), creditpackage.getValue(), creditpackage.getPrice());

                    }
                    System.out.print("Enter id of the package that you want to purchase\n->");
                    Long id = scanner.nextLong();
                    creditPackageEntityControllerRemote.addCustomerToCreditPackage(id, customer);
                    
                    System.out.print("How many of this credit package that you would like to purchase?\n->");
                    Integer num = scanner.nextInt();

                    //add new customer entity in credit package entity list<customerentity>
                    //creditPackageEntityControllerRemote.addCustomerToCreditPackage(id, customer);
                    //update customer's credit balance
                    BigDecimal addvalue = creditPackageEntityControllerRemote.retrieveCreditPackageById(id).getValue().multiply(BigDecimal.valueOf(num));
                    BigDecimal currentvalue = customer.getCreditBalance();
                    System.out.println("Incremental of credit balance:" + currentvalue.add(addvalue));//debug

                    customer = customerEntityControllerRemote.updateCreditBalance(customer.getId(), currentvalue.add(addvalue));

                    //create CreditTransactionEntity
                    creditTransactionEntityControllerRemote.createNewTransaction(customer.getId(), id, num, TransactionTypeEnum.TOPUP);

                    System.out.println("[System] Your purchase is successful!");
                    System.out.println("Your current credit balance is " + customer.getCreditBalance() + " .");
                    manageCreditPackage();

                } else if (response == 3) {
                    System.out.println("");
                    System.out.println("******* [Customer] View My Transaction History *******");
                    List<CreditTransactionEntity> transactionlist = creditTransactionEntityControllerRemote.viewAllCreditTransactionEntity(customer);
                    if (!transactionlist.isEmpty()) {
                        System.out.printf("%5s%10s%15s\n", "ID|", "Type|", "Total Value");
                        for (CreditTransactionEntity transaction : transactionlist) {
                            System.out.printf("%5s%10s%15s\n", transaction.getId() + "|", transaction.getTransactionTypeEnum() + "|", transaction.getTotalValue());
                        }
                        manageCreditPackage();
                    } else {
                        System.out.println("You don't have any transaction history so far!\n");
                    }
                } else if (response == 4) {
                    break;
                } else {
                    System.err.println("[Warning] Invalid Option! Please try again!");
                    manageCreditPackage();
                }
            }
        } catch (GeneralException | CreditPackageNotFoundException | CustomerNotFoundException ex) {
            System.err.println("[Waring] An error has occured: " + ex.getMessage());
            manageCreditPackage();
        } catch (InputMismatchException ex) {
            System.err.println("[Warning] Invalid Type!");
            manageCreditPackage();
        }
    }

    public void manageAddress() {

        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        while (true) {
            try {
                System.out.println("");
                System.out.println("******* [Customer] Address Managing *******");
                System.out.println("1. Create address");
                System.out.println("2. Update address");
                System.out.println("3. Delete address");
                System.out.println("4. View All Addresses");
                System.out.println("5. Back");
                System.out.println("Enter number of the operation that you want to perform");

                System.out.print("->");
                response = scanner.nextInt();
                if (response == 1) {
                    doCreateAddress();
                } else if (response == 2) {
                    doUpdateAddress();
                } else if (response == 3) {
                    doDeleteAddress();
                } else if (response == 4) {
                    doViewAddress();
                    break;
                } else if (response == 5) {
                    break;
                } else {
                    System.err.println("[Warning] Invalid input! Please try again");
                }
            } catch (InputMismatchException ex) {
                System.err.println("[Warning] Invalid Type!");
            }
        }
    }

    private void doViewAddress() {
        System.out.println("");
        System.out.println("******* [Customer] View All Address *******");
        List<AddressEntity> addresslist = customerEntityControllerRemote.getAddressByCustomer(customer.getId());
        if (addresslist.isEmpty()) {
            System.out.println("You do not have an address yet!");
            System.out.println("Please create an address first!");
        } else {
            System.out.printf("%5s%35s%20s\n", "Id|", "Address Line|", "Postal Code");
            for (AddressEntity address : addresslist) {
                System.out.printf("%5s%35s%20s\n", address.getId() + "|", address.getAddressLine() + "|", address.getPostCode());
            }
        }

        viewAddressDetail();
    }

    private void viewAddressDetail() {
        System.out.print("Input the id of the address that you want to view detail\n->");
        Scanner sc = new Scanner(System.in);
        Long response;
        try {
            response = sc.nextLong();
            System.out.println("");
            System.out.println("******* [Customer] View Address Detail *******");
            AddressEntity address = addressEntityControllerRemote.getAddressById(response);
            if (address == null) {
                System.err.println("[Warning] No address with id " + response + " exists!");
            } else {
                System.out.printf("%5s%35s%15s%10s\n", "Id|", "Address Line|", "Postal Code|", "Status");
                System.out.printf("%5s%35s%15s%10s\n", address.getId() + "|", address.getAddressLine() + "|", address.getPostCode() + "|", ((address.isIsDisabled()) ? "DISABLED" : "ACTIVE"));
            }
        } catch (InputMismatchException ex) {
        }
    }

    public void doCreateAddress() {

        try {
            Scanner scanner = new Scanner(System.in);

            AddressEntity address = new AddressEntity();
            System.out.println("");
            System.out.println("******* [Customer] New Address Creation *******");
            System.out.print("Enter address line\n->");
            address.setAddressLine(scanner.nextLine().trim());
            System.out.print("Enter postal code\n->");
            address.setPostCode(scanner.nextLine().trim());
            address.setIsDisabled(false);
            address.setCustomerEntity(customer);

            address = addressEntityControllerRemote.createAddress(address);
            System.out.println("[System] Address created successfully!");
        } catch (GeneralException ex) {
            System.err.println("[Warning] An error has occured: " + ex.getMessage());
        } catch (InputMismatchException ex) {
            System.err.println("[Warning] Invalid Type!");
        }

    }

    public void doUpdateAddress() {
        try {
            Scanner scanner = new Scanner(System.in);
            Integer response = 0;

            List<AddressEntity> addresslist = addressEntityControllerRemote.viewAllAddress(customer);
            if (!addresslist.isEmpty()) {
                System.out.println("");
                System.out.println("******* [Customer] List of Address *******");
                System.out.printf("%5s%30s%10s\n", "ID|", "Address Line|", "PostCode");
                for (AddressEntity address : addresslist) {
                    System.out.printf("%5s%30s%10s\n", address.getId(), address.getAddressLine(), address.getPostCode());
                }
                System.out.println("");
                System.out.println("******* [Customer] Address Update *******");
                System.out.print("Enter id of the address to update\n->");
                Long aid = scanner.nextLong();
                AddressEntity address = addressEntityControllerRemote.getAddressById(aid);
                System.out.println("1. Update address line");
                System.out.println("2. Update postal code");
                System.out.println("Enter number of the operation that you want to perform");
                while (response < 1 || response > 2) {
                    System.out.print("->");
                    response = scanner.nextInt();
                    if (response == 1) {
                        scanner.nextLine();
                        System.out.print("Enter new address line\n->");
                        address = addressEntityControllerRemote.updateAddressLine(aid, scanner.nextLine().trim());
                        System.out.print("[System] New address line updated successfully!");
                        break;
                    } else if (response == 2) {
                        scanner.nextLine();
                        System.out.print("Enter new postal code\n->");
                        address = addressEntityControllerRemote.updateAddressCode(aid, scanner.nextLine().trim());
                        System.out.println("[System] New postal code updated successfully!");
                        break;
                    }
                }
            } else {
                System.out.println("You do not have any address yet!");
            }
        } catch (InputMismatchException ex) {
            System.err.println("[Warning] Invalid Type!");
        } catch (AddressNotFoundException | GeneralException ex) {
            System.err.println("[Warning] " + ex.getMessage());
        }

    }

    public void doDeleteAddress() {

        try {
            Scanner scanner = new Scanner(System.in);

            List<AddressEntity> addresslist = addressEntityControllerRemote.viewAllAddress(customer);
            if (addresslist != null) {
                System.out.println("");
                System.out.println("******* [Customer] List of Address *******");
                System.out.printf("%5s%30s%10s\n", "ID|", "Address Line|", "PostCode");
                for (AddressEntity address : addresslist) {
                    System.out.printf("%5s%30s%10s\n", address.getId(), address.getAddressLine(), address.getPostCode());
                }
                System.out.println("");
                System.out.println("******* [Customer] Address Deletion *******");
                System.out.print("Enter id of the address to delete\n->");
                Long addressid = scanner.nextLong();
                //check whether "address" is associated with a winning bid, if yes, disable this address, if not just delete the address
                if (addressEntityControllerRemote.deleteAddress(addressid)) {
                    System.out.println("[System] Address deleted successfully!");
                } else {
                    System.out.println("[Sysytem] This address is associated with your winning bid! You cannot use this address in the future!");
                }
            } else {
                System.err.println("[Warning] You have not created any address!");
            }

        } catch (AddressNotFoundException ex) {
            System.err.println("[Warning] An error has occured: " + ex.getMessage());

        } catch (InputMismatchException ex) {
            System.err.println("[Warning] Invalid Type!");
        }

    }
}
