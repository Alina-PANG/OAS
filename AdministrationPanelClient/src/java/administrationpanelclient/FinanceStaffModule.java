/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package administrationpanelclient;

import ejb.session.stateless.CreditPackageEntityControllerRemote;
import entity.CreditPackageEntity;
import entity.CustomerEntity;
import entity.StaffEntity;
import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import util.exception.CreditPackageAlreadyExistException;
import util.exception.CreditPackageNotFoundException;
import util.exception.GeneralException;

/**
 *
 * @author alina
 */
public class FinanceStaffModule {

    // Define entity controllers
    private CreditPackageEntityControllerRemote creditPackageEntityController;

    // Define entities
    private StaffEntity currentStaffEntity;

    public FinanceStaffModule() {

    }

    public FinanceStaffModule(CreditPackageEntityControllerRemote creditPackageEntityController, StaffEntity currentStaffEntity) {
        this.creditPackageEntityController = creditPackageEntityController;
        this.currentStaffEntity = currentStaffEntity;
    }

    private void menu() {
        System.out.println("******* [Finance Staff] Homepage *******");
        System.out.println("1. Create New Credit Package");
        System.out.println("2. Update Credit Package");
        System.out.println("3. View Credit Package Details");
        System.out.println("4. Delete Credit Package");
        System.out.println("5. View All Credit Packages");
        System.out.println("6. Exit");
        System.out.println("Please enter number of the operation that you want to perform:");
        System.out.print("-> ");
    }

    protected void doMenu() {
        Scanner sc = new Scanner(System.in);
        int response = 0;

        try {
            while (true) {
                System.out.println("");
                menu();
                response = sc.nextInt();
                switch (response) {
                    case 1:
                        createCreditPackage();
                        break;
                    case 2:
                        System.out.println("");
                        updateCreditPackage();
                        break;
                    case 3:
                        System.out.println("");
                        System.out.println("******* [Finance Staff] View Credit Package Details *******");
                        viewCreditPackageDetails();
                        break;
                    case 4:
                        System.out.println("");
                        deleteCreditPackage();
                        break;
                    case 5:
                        System.out.println("");
                        viewAllCreditPackage();
                        break;
                    case 6:
                        break;
                    default:
                        System.err.println("[Warning] Invalid input! Please try again!");
                        break;
                }
                if (response == 6) {
                    break;
                }
            }
        } catch (InputMismatchException ex) {
            System.err.println("[Warning] Invalid Type!");
        }
    }

    private void createCreditPackage() {
        Scanner sc = new Scanner(System.in);
        BigDecimal value, price;
        String name;

        try {
            System.out.println("******* [Finance Staff] Create New Credit Package *******");
            System.out.print("Enter credit package name:\n-> ");
            name = sc.nextLine().trim();
            System.out.print("Enter value represented:\n-> ");
            value = sc.nextBigDecimal();
            System.out.print("Enter price:\n->");
            price = sc.nextBigDecimal();

            CreditPackageEntity cp = creditPackageEntityController.createNewCreditPackage(new CreditPackageEntity(value, price, name, false));
            System.out.println("[System] Credit Package with id = " + cp.getId() + ", name: '" + cp.getName() + "' has been created successfully!");
        } catch (CreditPackageAlreadyExistException | GeneralException ex) {
            System.err.println("[Warning] An error has occured while creating credit package: " + ex.getMessage());
        } catch (InputMismatchException ex) {
            System.err.println("[Warning] An error has occured while creating credit package: " + ex.getMessage());

        }
    }

    private void updateCreditPackage() {
        Scanner sc = new Scanner(System.in);
        int response = 0;

        System.out.println("******* [Finance Staff] Update Credit Package *******");

        try {
            CreditPackageEntity cp = findCreditPackage();
            viewCreditPackageDetails(cp);
            System.out.println("4. Finish");

            while (true) {
                System.out.print("Please enter number of the option that you want to change: \n-> ");
                response = sc.nextInt();
                sc.nextLine();

                switch (response) {
                    case 1:
                        System.out.print("Enter new name\n->");
                        cp.setName(sc.nextLine().trim());
                        break;
                    case 2:
                        System.out.print("Enter new value\n-> ");
                        cp.setValue(sc.nextBigDecimal());
                        break;
                    case 3:
                        System.out.print("Enter new price\n-> ");
                        cp.setPrice(sc.nextBigDecimal());
                        break;
                    case 4:
                        break;
                    default:
                        System.err.println("[Warning] Invalid input! Please try again!");
                        break;
                }
                if (response == 4) {
                    break;
                }
            }
            cp = creditPackageEntityController.updateCreditPackage(cp);
            System.out.println("[System] Credit Package with id = " + cp.getId() + ", name: '" + cp.getName() + "' has been updated successfully!");
        } catch (CreditPackageNotFoundException | GeneralException | CreditPackageAlreadyExistException ex) {
            System.err.println("[Warning] An error has occured while creating employee: " + ex.getMessage());
        } catch (InputMismatchException ex) {
            System.err.println("[Warning] Invalid Type!");
        }
    }

    private void viewCreditPackageDetails() {
        try {
            viewCreditPackageDetails(findCreditPackage());
        } catch (CreditPackageNotFoundException ex) {
            System.err.println("[Warning] An error has occured while creating employee: " + ex.getMessage());
        }

    }

    private void deleteCreditPackage() {
        System.out.println("******* [Finance Staff] Delete Credit Package*******");

        try {
            CreditPackageEntity cp = findCreditPackage();
            boolean result = creditPackageEntityController.deleteCreditPackage(cp.getId());
            if (result) {
                System.out.println("[System] Credit Package with id = " + cp.getId() + ", name: '" + cp.getName() + "' has been deleted successfully!");
            } else {
                System.out.println("[System] The Credit Package with id = " + cp.getId() + ", name: '" + cp.getName() + "' is in used, thus it can only be disabled.");
            }
        } catch (CreditPackageNotFoundException ex) {
            System.err.println("[Warning] An error has occured while creating employee: " + ex.getMessage());
        }
    }

    private void viewAllCreditPackage() {
        System.out.println("******* [Finance Staff] View All Credit Package *******");

        try {
            List<CreditPackageEntity> list = creditPackageEntityController.viewAllCreditPackage();
            showList(list);
        } catch (GeneralException ex) {
            System.err.println("[Warning] An error has occured while creating employee: " + ex.getMessage());
        }
    }

    private void showList(List<CreditPackageEntity> list) {
        System.out.println("******* [Finance Staff] View All Credit Packages *******");
        System.out.printf("%5s%20s%10s%10s\n", "ID|", "Name|", "Price|", "Value");
        for (CreditPackageEntity cp : list) {
            System.out.printf("%5s%20s%10s%10s\n", cp.getId() + "|", cp.getName() + "|", cp.getPrice() + "|", cp.getValue());
        }
    }

    private CreditPackageEntity findCreditPackage() throws CreditPackageNotFoundException {
        Scanner sc = new Scanner(System.in);
        String name;

        System.out.print("Enter the credit package name\n->");
        name = sc.nextLine().trim();

        List<CreditPackageEntity> list = creditPackageEntityController.retrieveCreditPackageByName(name);
        if(list.size() == 0)
            throw new CreditPackageNotFoundException("No Credit Package has name like "+name);
        else{
            showList(list);
            System.out.print("Enter id of the package that you want to retrieve\n->");
            return creditPackageEntityController.retrieveCreditPackageById(sc.nextLong());
        }
    }

    private void viewCreditPackageDetails(CreditPackageEntity cp) {
        System.out.println("");
        System.out.println("******* [Credit Package] id="+cp.getId()+" Content *******");
        System.out.println("1. Name: " + cp.getName());
        System.out.println("2. Value: " + cp.getValue());
        System.out.println("3. Price: " + cp.getPrice());
    }
}
