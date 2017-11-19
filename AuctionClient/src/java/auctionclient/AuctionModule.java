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
import entity.AuctionEntity;
import entity.BidEntity;
import entity.CustomerEntity;
import entity.ProxyBiddingEntity;
import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import util.enumeration.StatusEnum;
import util.exception.AuctionClosedException;
import util.exception.AuctionNotFoundException;
import util.exception.AuctionNotOpenException;
import util.exception.BidAlreadyExistException;
import util.exception.BidLessThanIncrementException;
import util.exception.BidNotFoundException;
import util.exception.CustomerNotFoundException;
import util.exception.GeneralException;
import util.exception.NotEnoughCreditException;

/**
 *
 * @author Amber
 */
public class AuctionModule {

    private CustomerEntityControllerRemote customerEntityControllerRemote;
    private CreditPackageEntityControllerRemote creditPackageEntityControllerRemote;
    private BidEntityControllerRemote bidEntityControllerRemote;
    private AuctionEntityControllerRemote auctionEntityControllerRemote;
    private CreditTransactionEntityControllerRemote creditTransactionEntityControllerRemote;
    private AddressEntityControllerRemote addressEntityControllerRemote;
    private TimerSessionBeanRemote timerSessionBean;
    private CustomerEntity customer;

    public AuctionModule() {
    }

    public AuctionModule(CustomerEntityControllerRemote customerEntityControllerRemote, CreditPackageEntityControllerRemote creditPackageEntityControllerRemote, BidEntityControllerRemote bidEntityControllerRemote, AuctionEntityControllerRemote auctionEntityControllerRemote, CreditTransactionEntityControllerRemote creditTransactionEntityControllerRemote, AddressEntityControllerRemote addressEntityControllerRemote, TimerSessionBeanRemote timerSessionBean, CustomerEntity customer) {
        this.customerEntityControllerRemote = customerEntityControllerRemote;
        this.creditPackageEntityControllerRemote = creditPackageEntityControllerRemote;
        this.bidEntityControllerRemote = bidEntityControllerRemote;
        this.auctionEntityControllerRemote = auctionEntityControllerRemote;
        this.creditTransactionEntityControllerRemote = creditTransactionEntityControllerRemote;
        this.addressEntityControllerRemote = addressEntityControllerRemote;
        this.timerSessionBean = timerSessionBean;
        this.customer = customer;

    }

    private void showList(List<AuctionEntity> list) {
        //  startDate, endDate, false, reservePrice, productName, productDes, new Long(-1), null));
        System.out.printf("%5s%35s%35s%10s%15s%20s\n", "ID|", "Start Date|", "End Date|", "Status|", "Reserve Price|", "Product Name");
        for (AuctionEntity al : list) {
            System.out.printf("%5s%35s%35s%10s%15s%20s\n", al.getId() + "|", al.getStartingTime() + "|", al.getEndingTime() + "|", al.getStatus() + "|", al.getReservePrice() + "|", al.getProductName());
        }
    }

    public void viewAuctionListing() {
        try {
            Scanner scanner = new Scanner(System.in);
            List<AuctionEntity> availableAuctionList = auctionEntityControllerRemote.viewAvailableAuctionEntity();
            System.out.println("");
            System.out.println("******* [Customer] View Auction Listing *******");
            showList(availableAuctionList);

            //ask whether want to view details of a specific auction item
            System.out.println("1. View details of auction item");
            System.out.println("2. Back to menu");
            System.out.println("Enter number of the operation that you want to perform");
            Integer response = 0;
            while (response < 1 || response > 2) {
                System.out.print("->");
                response = scanner.nextInt();
                if (response == 1) {
                    System.out.print("Enter ID of the auction item to view details\n->");
                    Long id = scanner.nextLong();
                    //if want to view details, direct to details of one specific item
                    viewAuctionEntityDetails(id);
                } else if (response == 2) {
                    break;
                } else {
                    System.err.println("[Warning] Invalid input! Please try again!");
                    viewAuctionListing();
                }
            }

        } catch (GeneralException ex) {
            System.err.println("[Warning] " + ex.getMessage());
            viewAuctionListing();
        } catch (InputMismatchException ex) {
            System.err.println("[Warning] Invalid Type!");
            viewAuctionListing();
        }

    }

    public void viewAuctionEntityDetails(Long productid) {
        try {
            AuctionEntity al = auctionEntityControllerRemote.retrieveAuctionById(productid);

            System.out.println("");
            try {
                System.out.println("******* [Auction Listing] id = " + al.getId() + " Content ******* ");
                System.out.println("0. Status: " + al.getStatus());
                System.out.println("1. Start Date: " + al.getStartingTime());
                System.out.println("2. End Date: " + al.getEndingTime());
                System.out.println("3. Reserve Price: " + al.getReservePrice());
                System.out.println("4. Product Name: " + al.getProductName());
                System.out.println("5. Product Description: " + al.getProductDescription());
                if (al.getStatus() == StatusEnum.ACTIVE) {
                    System.out.print("6. Current Highest Bid Amount: ");

                } else if (al.getStatus() == StatusEnum.CLOSED) {
                    System.out.print("6. Winning Bid Amount: ");
                }
                try {
                    BidEntity bid = auctionEntityControllerRemote.getCurrentWinningBidEntity(al.getId());

                    if (bid == null) {
                        System.out.println("No bid has been placed in this auction yet.");
                    } else {
                        System.out.println("" + bid.getAmount());
                    }
                } catch (AuctionNotFoundException ex) {
                }

                if (al.getStatus() != StatusEnum.PENDING && al.getStatus() != StatusEnum.CLOSED) {
                    System.out.print("Your Bid Amount: ");
                    try {
                        BidEntity b = bidEntityControllerRemote.viewMyBidInAuction(customer.getId(), al.getId());
                        System.out.println("" + b.getAmount());
                        System.out.print("Your Bid Type: ");
                        if (b instanceof ProxyBiddingEntity) {
                            System.out.println("Proxy Bid");
                        } else if (b.getAmount().equals(new BigDecimal(-101))) {
                            System.out.println("Snipping Bid");
                        } else {
                            System.out.println("Normal Bid");
                        }
                    } catch (BidNotFoundException ex) {
                        System.out.println("No bid has been placed by you in this auction yet.");
                    }

                    if (al.getStatus() == StatusEnum.DISABLED) {
                        System.out.println("The amount of your bid has been refund due to the auction is currently disabled.");
                    }
                }

            } catch (Exception ex) {
                System.err.println("[Warning] An error has occured: " + ex.getMessage());
            }
            /*
            System.out.println("Product ID:" + auctionentity.getId());
            System.out.println("Product Name:" + auctionentity.getProductName());
            System.out.println("Product Description:" + auctionentity.getProductDescription());
            System.out.println("Product Ending Time:" + auctionentity.getEndingTime());
            BidEntity winningbid = auctionEntityControllerRemote.
                    getCurrentWinningBidEntity(productid);
            if (winningbid == null) {
                System.out.println("There is no other bid for this item now!");
                System.out.println("Current minimal bid incremental (based on current highest price) is 0.05");
            } else {
                System.out.println("Current Highest Bid: " + winningbid.getAmount());
                System.out.println("Current minimal bid incremental (based on current highest price) is " + auctionEntityControllerRemote
                        .getCurrentBidIncremental(winningbid.getAmount()));
            }*/

            System.out.println("");
            System.out.println("1. Place new bid for this item");
            System.out.println("2. No thanks, I want to browse the auction list again.");
            System.out.println("Enter number of the operation that you want to perform");
            Scanner scanner = new Scanner(System.in);
            Integer response = 0;
            while (response < 1 || response > 2) {
                System.out.print("->");
                response = scanner.nextInt();
                if (response == 1) {
                    placeNewBid(productid);
                } else if (response == 2) {
                    viewAuctionListing();
                } else {
                    System.err.println("[Warning] Invalid input! Please try again!");
                }
            }
        } catch (AuctionNotFoundException ex) {
            System.err.println("[Warning] " + ex.getMessage());
            viewAuctionListing();
        } catch (InputMismatchException ex) {
            System.err.println("[Warning] Invalid Type!");
            viewAuctionListing();
        }
    }

    public void placeNewBid(Long productid) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("");
            System.out.println("******* [Customer] Place New Bid *******");
            System.out.print("Enter amount of your new bid(MUST be higher than the current highest bid plus current bid incremental)\n->");
            BidEntity bid = new BidEntity(scanner.nextBigDecimal());
            bid = bidEntityControllerRemote.createNewBid(bid, customer.getId(), productid);
            System.out.println("[System] Your new bid has been placed successfully!");
            System.out.println("");
            System.out.println("Your bid amount for product <" + bid.getAuctionEntity().getProductName() + "> is " + bid.getAmount());
            System.out.println("1. Refresh auction listing details");
            System.out.println("2. View auction listing again to browse for more good deals!");
            System.out.println("Enter number of the operation that you want to perform");

            Integer response = 0;
            while (response < 1 || response > 2) {
                System.out.print("->");
                response = scanner.nextInt();
                if (response == 1) {
                    viewAuctionEntityDetails(productid);
                } else if (response == 2) {
                    viewAuctionListing();
                } else {
                    System.err.println("[Warning] Invalid input! Please try again!");
                    placeNewBid(productid);
                }

            }
        } catch (AuctionNotFoundException | BidAlreadyExistException | GeneralException | AuctionClosedException | AuctionNotOpenException | BidLessThanIncrementException | CustomerNotFoundException | NotEnoughCreditException ex) {
            System.err.println("[Warning] " + ex.getMessage());
            viewAuctionListing();
        } catch (InputMismatchException ex) {
            System.err.println("[Warning] Invalid Type!");
            viewAuctionListing();
        }
    }

    public void viewBid() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("");
            System.out.println("******* [Customer] View My Bids *******");
            //view the failed bids, the one that does not win the auction, need to display the product info and amount of refund money

            List<BidEntity> bidlist = bidEntityControllerRemote.viewMyBidsInProcess(customer);
            if (!bidlist.isEmpty()) {
                System.out.println("Below are the auctions that you have placed bids but haven't reached auction ending time yet:");
                System.out.printf("%5s%25s%20s%35s\n", "ID|", "Auction Item Name|", "Your Bid Amount|", "Current Winning Bid Amount");
                for (BidEntity bid : bidlist) {
                    System.out.printf("%5s%25s%20s%35s\n", bid.getAuctionEntity().getId() + "|",
                            bid.getAuctionEntity().getProductName() + "|",
                            bid.getAmount() + "|",
                            auctionEntityControllerRemote.getWinningBidAmount(bid.getAuctionEntity().getId()));
                }
                System.out.print("Do you want to place new bid?('y' or press any key to cancel)\n->");
                if (scanner.nextLine().trim().toUpperCase().equals("Y")) {
                    BidEntity bid = new BidEntity();
                    System.out.print("Enter id of the auction item that you want to place new bids\n->");
                    Long bidid = scanner.nextLong();
                    System.out.print("Enter new amount\n->");
                    bid.setAmount(scanner.nextBigDecimal());
                    bid = bidEntityControllerRemote.createNewBid(bid, customer.getId(), bidid);
                    System.out.println("[System] Your new bid has been placed successfully!");
                    System.out.println("Your bid amount for product:" + bid.getAuctionEntity().getProductName() + " is " + bid.getAmount());
                }
                /*
                System.out.println("Back to menu?(Y/N)->");
                if (scanner.nextLine().trim().equals("Y")) {
                    mainapp = new MainApp(customerEntityControllerRemote, creditPackageEntityControllerRemote, bidEntityControllerRemote, auctionEntityControllerRemote, creditTransactionEntityControllerRemote, addressEntityControllerRemote, timerSessionBean);
                    mainapp.menuMain(customer);
                }
                while (!scanner.nextLine().trim().equals("Y")) {
                    scanner.nextLine();
                    System.out.println("->");
                    if (scanner.nextLine().trim().equals("Y")) {
                        mainapp = new MainApp(customerEntityControllerRemote, creditPackageEntityControllerRemote, bidEntityControllerRemote, auctionEntityControllerRemote, creditTransactionEntityControllerRemote, addressEntityControllerRemote, timerSessionBean);
                    }
                    mainapp.menuMain(customer);
                }
                 */
            } else {
                System.err.println("[Warning] You haven't placed any bid yet!\n");
            }

        } catch (GeneralException | AuctionNotFoundException | NotEnoughCreditException | AuctionClosedException | AuctionNotOpenException | BidAlreadyExistException | BidLessThanIncrementException | CustomerNotFoundException ex) {
            System.err.println("An error as occured while placing new bid: " + ex.getMessage());
            viewBid();
        } catch (InputMismatchException ex) {
            System.err.println("[Warning] Invalid Type!");
            viewBid();
        }
    }

    public void viewWonAuction() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("");
        System.out.println("******* [Customer] View Won Auction Listing *******");
        System.out.println("******* [Premium Customer] View Won Auction Listing *******");

        try {
            List<AuctionEntity> list = auctionEntityControllerRemote.viewWonAuction(customer.getId());
            showList(list);
        } catch (GeneralException ex) {
            System.err.println("[Warning] An error has occured while viewing winning auction listing: " + ex.getMessage());
        }
        /*
            List<BidEntity> winningbidlist = bidEntityControllerRemote.viewAllWinningBid(customer);
            if (!winningbidlist.isEmpty()) {
                Boolean nulladdressexists = false;
                System.out.printf("%10s%20s%15s%35s\n", "Bid ID|", "Product name|", "Bid amount|", "Delivery Address");
                for (BidEntity bid : winningbidlist) {
                    System.out.printf("%10s%20s%15s%35\n", bid.getId() + "|", bid.getAuctionEntity().getProductName() + "|",
                            bid.getAmount() + "|", ((bid.getAddressEntity() == null)?"NO ADDRESS":bid.getAddressEntity().getAddressLine()));
                }
                if (nulladdressexists) {
                    selectaddressforwinningbid();
                } else {
                    
                    System.out.println("Back to menu?(Y/N)->");
                    while (scanner.nextLine().trim().equals("N")) {
                        System.out.println("->");
                        if (scanner.nextLine().trim().equals("Y")) {
                            mainapp = new MainApp(customerEntityControllerRemote, creditPackageEntityControllerRemote, bidEntityControllerRemote, auctionEntityControllerRemote, creditTransactionEntityControllerRemote, addressEntityControllerRemote, timerSessionBean);
                        }
                        mainapp.menuMain(customer);
                    }
                     
                }
            } else {
                System.err.println("[Warning] There are no winning auctions now!");

            }
        } catch (GeneralException ex) {
            System.err.println("[Warning] An error has occured: " + ex.getMessage());
            viewWonAuction();
        } catch (InputMismatchException ex) {
            System.err.println("[Warning] Invalid Type!");
            viewWonAuction();
        }*/
    }

    public void selectaddressforwinningbid() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("");
            System.out.println("****** [Customer] Address Selection for Won Auction *******");

            System.out.print("Enter id of the won auction that you want to assign an address\n->");
            Long bidid = scanner.nextLong();
            System.out.println("Your current address list:");
            List<AddressEntity> addresslist = customerEntityControllerRemote.getAddressByCustomer(customer.getId());
            if (addresslist.isEmpty()) {
                System.out.println("You do not have an address yet!");
                System.out.println("Please create an address first!");
            } else {
                System.out.printf("%5s%35s%15s\n", "Id|", "Address Line|", "Postal Code|");
                for (AddressEntity address : addresslist) {
                    System.out.printf("%5s%35s%15s\n", address.getId() + "|", address.getAddressLine() + "|", address.getPostCode());
                }
                System.out.print("Enter id of the address selected for the won auction\n->");
                Long addressid = scanner.nextLong();
                BidEntity bid = bidEntityControllerRemote.setAddressForWinningBid(addressid, bidid);
                System.out.println("[System] Address updated successfully!");

                viewWonAuction();//refresh the list and check if there is still won auction that does not have an address
            }
        } catch (BidNotFoundException | GeneralException ex) {
            System.err.println("[Warning] An error has occured: " + ex.getMessage());
            viewWonAuction();
        } catch (InputMismatchException ex) {
            System.err.println("[Warning] Invalid Type!");
            viewWonAuction();
        }
    }

}
