/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proxyagent;

import ejb.session.stateless.AuctionEntityControllerRemote;
import ejb.session.stateless.BidEntityControllerRemote;
import ejb.session.stateless.CustomerEntityControllerRemote;
import ejb.session.stateless.TimerSessionBeanRemote;
import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import util.exception.NotEnoughCreditException;
import ws.client.AuctionClosedException_Exception;
import ws.client.AuctionNotFoundException_Exception;
import ws.client.AuctionNotOpenException_Exception;
import ws.client.BidAlreadyExistException_Exception;
import ws.client.BidEntity;
import ws.client.BidLessThanIncrementException_Exception;
import ws.client.CustomerAlreadyPremiumException_Exception;
import ws.client.CustomerEntity;
import ws.client.CustomerNotFoundException_Exception;
import ws.client.CustomerNotPremiumException_Exception;
import ws.client.GeneralException_Exception;
import ws.client.IncorrectPasswordException_Exception;
import ws.client.NotEnoughCreditException_Exception;
import ws.client.ProxyBiddingEntity;

/**
 *
 * @author alina
 */
public class MainApp {

    private CustomerEntityControllerRemote customerEntityController;
    private BidEntityControllerRemote bidEntityController;
    private AuctionEntityControllerRemote auctionEntityController;
    private TimerSessionBeanRemote timerSessionBean;

    private CustomerEntity currentCustomerEntity;

    public MainApp() {
    }

    public MainApp(CustomerEntityControllerRemote customerEntityController, BidEntityControllerRemote bidEntityController, AuctionEntityControllerRemote auctionEntityController, TimerSessionBeanRemote timerSessionBean) {
        this.customerEntityController = customerEntityController;
        this.bidEntityController = bidEntityController;
        this.auctionEntityController = auctionEntityController;
        this.timerSessionBean = timerSessionBean;
    }

    public void runApp() {
        Scanner sc = new Scanner(System.in);
        int response = 0;

        while (true) {
            System.out.println("");
            menu1();
            try {
                response = sc.nextInt();
                switch (response) {
                    case 1:
                        readUsernameAndPassword(1);

                        break;
                    case 2:
                        readUsernameAndPassword(2);

                        break;
                    case 3:
                        break;
                    default:
                        System.err.println("[Warning] Please input a valid number!");
                        break;
                }
                if (response == 3) {
                    break;
                }
            } catch (InputMismatchException ex) {
                System.err.println("[Warning] Please input a valid type!");
            }
        }
    }

    private void menu2() {
        System.out.println("******* [OAS System Web Service] Premium Customer Operations");
        System.out.println("Please choose an action: ");
        System.out.println("1. Browse all auction listings");
        System.out.println("2. View won auction listings");
        System.out.println("3. View Auction Listing Details");
        System.out.println("4. View Credit Balance");
        System.out.println("5. Configure Proxy Bidding for Auction Listing");
        System.out.println("6. Configure Sniping for Auction Listing");
        System.out.println("7. Logout");
        System.out.print("Please input your choice: ");
    }

    private void postLogin() {
        Scanner sc = new Scanner(System.in);
        int response = 0;

        while (true) {
            menu2();
            try {
                response = sc.nextInt();
                switch (response) {
                    case 1:
                        System.out.println("");
                        System.out.println("******* [Premium Customer] View All Auction Listing *******");
                        viewAllAuction();
                        break;
                    case 2:
                        System.out.println("");
                        viewWonAuction();
                        break;
                    case 3:
                        System.out.println("");
                        viewAuctionListingDetail();
                        break;
                    case 4:
                        System.out.println("");
                        viewCreditBalance();
                        break;
                    case 5:
                        System.out.println("");
                        configureProxyBidding();
                        break;
                    case 6:
                        System.out.println("");
                        configureSnippingBidding();
                        break;
                    case 7:
                        break;
                    default:
                        System.err.println("[Warning] Please input a valid number!");
                        break;
                }
            } catch (InputMismatchException ex) {
                System.err.println("[Warning] Please input a valid type!");
            }
            if (response == 7) {
                break;
            }
        }

    }

    private void configureProxyBidding() {
        Scanner sc = new Scanner(System.in);
        BigDecimal maxPrice;
        Long aid;

        System.out.println("******* [Premium Customer] Configure Proxy Bidding for Auction Listing *******");
        viewAllAuction();
        System.out.print("Please input the auction id that you want to put proxy bid: ");
        aid = sc.nextLong();
        System.out.print("Please input the max price that you want to pay: ");
        maxPrice = sc.nextBigDecimal();

        ProxyBiddingEntity bid = new ProxyBiddingEntity();
        bid.setMaxAmount(maxPrice);
        try {
            createProxyBid(bid, aid, currentCustomerEntity.getId());
            System.out.println("[System] Proxy bid has been created successfully!");
        } catch (NotEnoughCreditException_Exception |AuctionNotOpenException_Exception | GeneralException_Exception | BidAlreadyExistException_Exception | AuctionNotFoundException_Exception | BidLessThanIncrementException_Exception | CustomerNotFoundException_Exception | AuctionClosedException_Exception ex) {
            System.err.println("[Warning] An error has occured: " + ex.getMessage());
        }
    }

    private void configureSnippingBidding() {
        Scanner sc = new Scanner(System.in);
        BigDecimal maxPrice;
        Long aid;
        int timeDuration;

        System.out.println("******* [Premium Customer] Configure Sniping for Auction Listing *******");
        System.out.println("Please input the auction id that you want to put for snipping bid: ");
        aid = sc.nextLong();
        System.out.print("Please input the max price that you want to pay: ");
        maxPrice = sc.nextBigDecimal();
        System.out.println("Please input the time duration before the listing expired to place your bid (in minutes): ");
        timeDuration = sc.nextInt();

        BidEntity bid = new BidEntity();
        try {
            createSnippingBid(bid, maxPrice, timeDuration, aid, currentCustomerEntity.getId());
            System.out.println("[System] Proxy bid has been created successfully!");
        } catch (CustomerNotFoundException_Exception | AuctionNotFoundException_Exception | BidAlreadyExistException_Exception | GeneralException_Exception ex) {
            System.err.println("[Warning] and error has occured: " + ex.getMessage());
        }

    }

    private void viewCreditBalance() {
        System.out.println("******* [Premium Customer] View Credit Balance *******");
        try {
            BigDecimal value = viewCreditBalance(currentCustomerEntity.getId());
            System.out.println("Your Credit Balance is " + value);
        } catch (GeneralException_Exception | CustomerNotFoundException_Exception ex) {
            System.err.println("[Warning] an error has occured: " + ex.getMessage());
        }
    }

    private void viewAuctionListingDetail() {
        Scanner sc = new Scanner(System.in);
        System.out.println("******* [Premium Customer] View Auction Listing Detail *******");
        System.out.println("Please input the relevant auction / product name for searching:");
        System.out.print("> ");
        String input = sc.nextLine().trim();

        try {
            List<ws.client.AuctionEntity> list = viewAuctionListByName(input);
            showList(list);
            System.out.println("Please input the id of the Auction List:");
            ws.client.AuctionEntity al = viewAuctionListDetails(sc.nextLong());//???

            System.out.println("******* [Auction Listing] id = " + al.getId() + " Content ******* ");
            System.out.println("[Immutable] Status: " + al.getStatus());
            System.out.print("[Immutable] Current Highest Bid: ");

            if (!al.getWinningBidId().equals(new Long(-10))) {
                System.out.println("" + auctionEntityController.getCurrentWinningBidEntity(al.getId()).getAmount());
            } else {
                System.out.println("No bid yet.");
            }
            System.out.println("1. Start Date: " + al.getStartingTime());
            System.out.println("2. End Date: " + al.getEndingTime());
            System.out.println("3. Reserve Price: " + al.getReservePrice());
            System.out.println("4. Product Name: " + al.getProductName());
            System.out.println("5. Product Description: " + al.getProductDescription());

        } catch (Exception ex) {
            System.err.println("[Warning] An error has occured: " + ex.getMessage());
        }
    }

    private void viewAllAuction() {
        

        try {
            List<ws.client.AuctionEntity> list = viewAllAuctionListings();
            showList(list);
        } catch (Exception ex) {
            System.err.println("[Warning] An error has occured while creating employee: " + ex.getMessage());
        }
    }

    private void viewWonAuction() {
        System.out.println("******* [Premium Customer] View Won Auction Listing *******");

        try {
            List<ws.client.AuctionEntity> list = viewWonAuctionListings(currentCustomerEntity.getId());
            showList(list);
        } catch (Exception ex) {
            System.err.println("[Warning] An error has occured while creating employee: " + ex.getMessage());
        }
    }

    private void menu1() {
        System.out.println("******* [OAS System Web Service] Menu ******");
        System.out.println("Please choose an action: ");
        System.out.println("1. Registration");
        System.out.println("2. Login");
        System.out.println("3. Exit");
        System.out.print("Please input your choice: ");
    }

    private void readUsernameAndPassword(int input) {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.println("******* [OAS System Web Service] Login *******");
            System.out.print("Please input your username: ");
            String username = sc.nextLine().trim();
            System.out.print("Please input your password: ");
            String password = sc.nextLine().trim();

            if (input == 1) {
                currentCustomerEntity = registration(username, password);
            } else if (input == 2) {
                currentCustomerEntity = customerLogin(username, password);
            }

            postLogin();
        } catch (InputMismatchException ex) {
            System.err.println("[Warning] Please input a valid type!");
        } catch (CustomerNotFoundException_Exception | IncorrectPasswordException_Exception | CustomerAlreadyPremiumException_Exception | CustomerNotPremiumException_Exception ex) {
            System.err.println("[Warning] An error has occured: " + ex.getMessage());
        }
    }

    private void showList(List<ws.client.AuctionEntity> list) {
        //  startDate, endDate, false, reservePrice, productName, productDes, new Long(-1), null));
        System.out.printf("%5s%35s%35s%10s%15s%20s\n", "ID|", "Start Date|", "End Date|", "Status|", "Reserve Price|", "Product Name");
        for (ws.client.AuctionEntity al : list) {
            System.out.printf("%5s%35s%35s%10s%15s%20s\n", al.getId() + "|", al.getStartingTime() + "|", al.getEndingTime() + "|", al.getStatus() + "|", al.getReservePrice() + "|", al.getProductName());
        }
    }

    private static CustomerEntity registration(java.lang.String username, java.lang.String password) throws CustomerNotFoundException_Exception, IncorrectPasswordException_Exception, CustomerAlreadyPremiumException_Exception {
        ws.client.ProxyWebService_Service service = new ws.client.ProxyWebService_Service();
        ws.client.ProxyWebService port = service.getProxyWebServicePort();
        return port.registration(username, password);
    }

    private static CustomerEntity customerLogin(java.lang.String username, java.lang.String password) throws IncorrectPasswordException_Exception, CustomerNotFoundException_Exception, CustomerNotPremiumException_Exception {
        ws.client.ProxyWebService_Service service = new ws.client.ProxyWebService_Service();
        ws.client.ProxyWebService port = service.getProxyWebServicePort();
        return port.customerLogin(username, password);
    }

    private static BigDecimal viewCreditBalance(java.lang.Long id) throws GeneralException_Exception, CustomerNotFoundException_Exception {
        ws.client.ProxyWebService_Service service = new ws.client.ProxyWebService_Service();
        ws.client.ProxyWebService port = service.getProxyWebServicePort();
        return port.viewCreditBalance(id);
    }

    private static java.util.List<ws.client.AuctionEntity> viewAllAuctionListings() throws GeneralException_Exception {
        ws.client.ProxyWebService_Service service = new ws.client.ProxyWebService_Service();
        ws.client.ProxyWebService port = service.getProxyWebServicePort();
        return port.viewAllAuctionListings();
    }

    private static java.util.List<ws.client.AuctionEntity> viewWonAuctionListings(java.lang.Long id) throws GeneralException_Exception {
        ws.client.ProxyWebService_Service service = new ws.client.ProxyWebService_Service();
        ws.client.ProxyWebService port = service.getProxyWebServicePort();
        return port.viewWonAuctionListings(id);
    }

    private static java.util.List<ws.client.AuctionEntity> viewAuctionListByName(java.lang.String productName) throws AuctionNotFoundException_Exception {
        ws.client.ProxyWebService_Service service = new ws.client.ProxyWebService_Service();
        ws.client.ProxyWebService port = service.getProxyWebServicePort();
        return port.viewAuctionListByName(productName);
    }

    private static ws.client.AuctionEntity viewAuctionListDetails(java.lang.Long id) throws AuctionNotFoundException_Exception {
        ws.client.ProxyWebService_Service service = new ws.client.ProxyWebService_Service();
        ws.client.ProxyWebService port = service.getProxyWebServicePort();
        return port.viewAuctionListDetails(id);
    }

    private static void createSnippingBid(ws.client.BidEntity bid, java.math.BigDecimal maxPrice, int timeDuration, java.lang.Long aid, java.lang.Long cid) throws CustomerNotFoundException_Exception, AuctionNotFoundException_Exception, GeneralException_Exception, BidAlreadyExistException_Exception {
        ws.client.ProxyWebService_Service service = new ws.client.ProxyWebService_Service();
        ws.client.ProxyWebService port = service.getProxyWebServicePort();
        port.createSnippingBid(bid, maxPrice, timeDuration, aid, cid);
    }

    private static void createProxyBid(ws.client.ProxyBiddingEntity bid, java.lang.Long aid, java.lang.Long cid) throws NotEnoughCreditException_Exception, AuctionNotOpenException_Exception, GeneralException_Exception, BidAlreadyExistException_Exception, AuctionNotFoundException_Exception, BidLessThanIncrementException_Exception, CustomerNotFoundException_Exception, AuctionClosedException_Exception {
        ws.client.ProxyWebService_Service service = new ws.client.ProxyWebService_Service();
        ws.client.ProxyWebService port = service.getProxyWebServicePort();
        port.createProxyBid(bid, aid, cid);
    }

}
