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
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
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
    private MainApp mainapp;
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

   
   
    
    public void viewAuctionListing()
    {
        try
        {    
        Scanner scanner = new Scanner(System.in);
        List<AuctionEntity> availableAuctionList = auctionEntityControllerRemote.viewAvailableAuctionEntity();
        System.out.println("******* [Customer] View Auction Listing *******");
        System.out.println("Product ID---Product name");
        
        for(AuctionEntity auctionentity: availableAuctionList)
        {
            System.out.print(auctionentity.getId()+"---");
            System.out.println(auctionentity.getProductName());
        }
        
        //ask whether want to view details of a specific auction item
        System.out.println("1. View details of auction item");
        System.out.println("2. Back to menu");
        System.out.println("Enter number of the operation that you want to perform");
        Integer response=0;
        while(response<1||response>2)
        {
            System.out.println("->");
            response = scanner.nextInt();
            if(response==1)
            {
                break;
            }
            else if(response==2)
            {
                this.mainapp=new MainApp(customerEntityControllerRemote, creditPackageEntityControllerRemote, bidEntityControllerRemote, auctionEntityControllerRemote, creditTransactionEntityControllerRemote, addressEntityControllerRemote, timerSessionBean);
                mainapp.menuMain(customer);
            }
            else
            {
                System.err.println("[Warning] Invalid input! Please try again!");
                viewAuctionListing();
            }
        }
        System.out.println("Enter ID of the auction item to view details->");
        Long id = scanner.nextLong();
        //if want to view details, direct to details of one specific item
        viewAuctionEntityDetails(id);
        }
        catch(GeneralException ex)
        {
            System.err.println("[Warning] "+ex.getMessage());
            viewAuctionListing();
        }
        catch(InputMismatchException ex){
            System.err.println("[Warning] Invalid Type!");
            viewAuctionListing();
        }
        
    }
    
    public void viewAuctionEntityDetails(Long productid)
    {
        try
        {
        AuctionEntity auctionentity = auctionEntityControllerRemote.retrieveAuctionById(productid);
       
        System.out.println("******* [Customer] View Auction Item Details *******\n");
        System.out.println("Product ID:"+auctionentity.getId());
        System.out.println("Product Name:"+auctionentity.getProductName());
        System.out.println("Product Description:"+auctionentity.getProductDescription());
        System.out.println("Product Ending Time:"+auctionentity.getEndingTime());
        BidEntity winningbid = auctionEntityControllerRemote.
                getCurrentWinningBidEntity(productid);
        if(winningbid==null){
            System.out.println("There is no other bid for this item now!");
            System.out.println("Current minimal bid incremental (based on current highest price) is 0.05");
        }
        else{
            System.out.println("Current Highest Bid: "+winningbid.getAmount());
            System.out.println("Current minimal bid incremental (based on current highest price) is "+auctionEntityControllerRemote
                .getCurrentBidIncremental(winningbid.getAmount()));
        }
        
        System.out.println("1. Place new bid for this item");
        System.out.println("2. No thanks, I want to browse the auction list again.");
        System.out.println("Enter number of the operation that you want to perform");
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        while(response<1||response>2)
        {
            System.out.println("->");
            response = scanner.nextInt();
            if(response==1)
            {
                placeNewBid(productid);
            }   
            else if(response==2)
            {
                viewAuctionListing();
            }
            else
                System.err.println("[Warning] Invalid input! Please try again!");
        }
        }
        catch(AuctionNotFoundException ex)
        {
            System.err.println("[Warning] "+ex.getMessage());
            viewAuctionListing();
        }
        catch(InputMismatchException ex){
            System.err.println("[Warning] Invalid Type!");
            viewAuctionListing();
        }
    }
    
    
    public void placeNewBid(Long productid)
    {
        try
        {
        Scanner scanner = new Scanner(System.in);
        System.out.println("******* [Customer] Place New Bid *******");
        System.out.println("Enter amount of your new bid(MUST be higher than the current highest bid plus current bid incremental)->");
        BidEntity bid = new BidEntity(scanner.nextBigDecimal());
        bid = bidEntityControllerRemote.createNewBid(bid,customer.getId(),productid);
        System.out.println("[System] Your new bid has been placed successfully!");
        System.out.println("Your bid amount for product:"+bid.getAuctionEntity().getProductName()+" is "+bid.getAmount());
        
        System.out.println("1. Refresh auction listing details");
        System.out.println("2. View auction listing again to browse for more good deals!");
        System.out.println("Enter number of the operation that you want to perform");
        
        Integer response = 0;
        while(response<1||response>2)
        {
            System.out.println("->");
            response = scanner.nextInt();
            if(response==1)
            {
                viewAuctionEntityDetails(productid);
            }
            else if(response==2)
            {
                viewAuctionListing();
            }
            else
            {
                System.err.println("[Warning] Invalid input! Please try again!");
                placeNewBid(productid);
            }

        }
        }
        catch(AuctionNotFoundException|BidAlreadyExistException|GeneralException |AuctionClosedException |AuctionNotOpenException|BidLessThanIncrementException|CustomerNotFoundException|NotEnoughCreditException ex)
        {
            System.err.println("[Warning] "+ex.getMessage());
            viewAuctionListing();
        }
        catch(InputMismatchException ex){
            System.err.println("[Warning] Invalid Type!");
            viewAuctionListing();
        }
    }
    

    public void viewBid()
    {
        try{
        Scanner scanner = new Scanner(System.in);
        System.out.println("******* [Customer] View My Bids *******");
        //view the failed bids, the one that does not win the auction, need to display the product info and amount of refund money
        
        List<BidEntity> bidlist = bidEntityControllerRemote.viewMyBidsInProcess(customer);
        if(!bidlist.isEmpty()){
        System.out.println("Below are the auctions that you have placed bids but haven't reached auction ending time yet:");
        System.out.println("Product ID---Product name---Your bid---Current Winning Bid");
        for(BidEntity bid :bidlist)
        {
            System.out.print(bid.getAuctionEntity().getId()+"---");
            System.out.print(bid.getAuctionEntity().getProductName()+"---");
            System.out.print(bid.getAmount()+"---");
            System.out.print(auctionEntityControllerRemote
                    .getCurrentWinningBidEntity(bid.getAuctionEntity().getId())
                    .getAmount()+"\n");
        }
        System.out.println("Do you want to place new bid?(Y/N)->");
        if(scanner.nextLine().trim().equals("Y"))
        {
            BidEntity bid = new BidEntity();
            System.out.println("Enter id of the auction item that you want to place new bids->");
            Long bidid = scanner.nextLong();
            System.out.println("Enter new amount->");
            bid.setAmount(scanner.nextBigDecimal());
            bid = bidEntityControllerRemote.createNewBid(bid,customer.getId(),bidid);
            System.out.println("[System] Your new bid has been placed successfully!");
            System.out.println("Your bid amount for product:"+bid.getAuctionEntity().getProductName()+" is "+bid.getAmount());
        }
        
        
            System.out.println("Back to menu?(Y/N)->");
                while(!scanner.nextLine().trim().equals("Y"))
                {
                    scanner.nextLine();
                    System.out.println("->");
                    if(scanner.nextLine().trim().equals("Y"))
                    mainapp = new MainApp(customerEntityControllerRemote, creditPackageEntityControllerRemote, bidEntityControllerRemote, auctionEntityControllerRemote, creditTransactionEntityControllerRemote, addressEntityControllerRemote, timerSessionBean);
                    mainapp.menuMain(customer);
                } 
        
        }
        else{
            System.err.println("[Warning] You haven't placed any bid yet!\n");
            mainapp = new MainApp(customerEntityControllerRemote, creditPackageEntityControllerRemote, bidEntityControllerRemote, auctionEntityControllerRemote, creditTransactionEntityControllerRemote, addressEntityControllerRemote, timerSessionBean);
            mainapp.menuMain(customer);
        }
        
        }
        catch(GeneralException|AuctionNotFoundException |NotEnoughCreditException |AuctionClosedException |AuctionNotOpenException |BidAlreadyExistException |BidLessThanIncrementException |CustomerNotFoundException ex)
        {
            System.out.println(ex.getMessage());
            viewBid();
        }
        catch(InputMismatchException ex){
            System.err.println("[Warning] Invalid Type!");
            viewBid();
        }
    }
    
    
    public void viewWonAuction()
    {
        try{
        Scanner scanner = new Scanner(System.in);
        System.out.println("******* [Customer] View Won Auction Listing *******\n");

        List<BidEntity> winningbidlist= bidEntityControllerRemote.viewAllWinningBid(customer);
        if(!winningbidlist.isEmpty())
        {
            System.out.println("Below are the auctions that you have won:");
            Boolean nulladdressexists=false;
            System.out.println("Bid ID---Product name---Bid amount---Delivery Address");
            for(BidEntity bid:winningbidlist)
            {
                System.out.print(bid.getId()+"---");
                System.out.print(bid.getAuctionEntity().getProductName()+"---");
                System.out.print(bid.getAmount()+"---");
                if(bid.getAddressEntity()!=null)
                {
                    System.out.println(bid.getAddressEntity().getAddressLine()+"---");
                    System.out.print(bid.getAddressEntity().getPostCode());
                    nulladdressexists=true;
                }
                    
                else
                    System.err.println("[Warning] No address linked to this winning bid yet!");
            }
            if(nulladdressexists)
            {
                selectaddressforwinningbid();
            }
            else
            {
                System.out.println("Back to menu?(Y/N)->");
                while(scanner.nextLine().trim().equals("N"))
                {
                    System.out.println("->");
                    if(scanner.nextLine().trim().equals("Y"))
                    mainapp = new MainApp(customerEntityControllerRemote, creditPackageEntityControllerRemote, bidEntityControllerRemote, auctionEntityControllerRemote, creditTransactionEntityControllerRemote, addressEntityControllerRemote, timerSessionBean);
                    mainapp.menuMain(customer);
                } 
               
            }
        }
        else
        {
            System.err.println("[Warning] There are no winning auctions now!");
            mainapp = new MainApp(customerEntityControllerRemote, creditPackageEntityControllerRemote, bidEntityControllerRemote, auctionEntityControllerRemote, creditTransactionEntityControllerRemote, addressEntityControllerRemote, timerSessionBean);
            mainapp.menuMain(customer);
            
        }
        }
        catch(GeneralException ex)
        {
            System.err.println("[Warning] An error has occured: "+ex.getMessage());
            viewWonAuction();
        }
        catch(InputMismatchException ex){
            System.err.println("[Warning] Invalid Type!");
            viewWonAuction();
        }
    }
    
    public void selectaddressforwinningbid()
    {
        try{
        Scanner scanner = new Scanner(System.in);
        System.out.println("****** [Customer] Address Selection for Won Auction *******");
        
        System.out.println("Enter id of the won auction that you want to assign an address->");
        Long bidid = scanner.nextLong();
        System.out.println("Your current address list:");
        List<AddressEntity> addresslist = customer.getAddressEntities();
        for(AddressEntity address:addresslist)
        {
            System.out.print(address.getId()+"---");
            System.out.print(address.getAddressLine()+"---");
            System.out.println(address.getPostCode());
        }
        System.out.print("Enter id of the address selected for the won auction->");
        Long addressid = scanner.nextLong();
        BidEntity bid = bidEntityControllerRemote.setAddressForWinningBid(addressid, bidid);
        System.out.println("[System] Address updated successfully!");
        
        viewWonAuction();//refresh the list and check if there is still won auction that does not have an address 
        }
        catch(BidNotFoundException|GeneralException ex)
        {
            System.err.println("[Warning] An error has occured: "+ex.getMessage());
            viewWonAuction();
        } 
        catch(InputMismatchException ex){
            System.err.println("[Warning] Invalid Type!");
            viewWonAuction();
        }
    }

}
