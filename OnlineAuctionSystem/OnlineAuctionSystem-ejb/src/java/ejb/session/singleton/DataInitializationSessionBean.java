/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.AuctionEntityControllerLocal;
import ejb.session.stateless.BidEntityControllerLocal;
import ejb.session.stateless.CreditPackageEntityControllerLocal;
import ejb.session.stateless.StaffEntityControllerLocal;
import entity.AuctionEntity;
import entity.CreditPackageEntity;
import entity.StaffEntity;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.enumeration.EmployeeAccessRightEnum;
import util.enumeration.StatusEnum;
import util.exception.AuctionAlreadyExistException;
import util.exception.CreditPackageAlreadyExistException;
import util.exception.DuplicateException;
import util.exception.GeneralException;
import util.exception.StaffAlreadyExistException;
import util.exception.StaffNotFoundException;

/**
 *
 * @author alina
 */
@Singleton
@LocalBean
@Startup
public class DataInitializationSessionBean {

    @EJB
    private CreditPackageEntityControllerLocal creditPackageEntityController;

    @EJB
    private BidEntityControllerLocal bidEntityController;

    @EJB
    private AuctionEntityControllerLocal auctionEntityController;

    @EJB
    private StaffEntityControllerLocal staffEntityController;

    @PersistenceContext(unitName = "OnlineAuctionSystem-ejbPU")
    private EntityManager em;

    @PostConstruct
    public void postConstruct() {
        try {
            staffEntityController.retrieveStaffByIdentificationNumber("01");
        } catch (StaffNotFoundException | DuplicateException ex) {
            initializeDataStaff();
        }
    }

    private void initializeDataStaff() {
        try {
            staffEntityController.createNewStaffEntity(new StaffEntity("Anthony", "Young", "01", "manager", "password", EmployeeAccessRightEnum.MANAGER));
            staffEntityController.createNewStaffEntity(new StaffEntity("Yue", "Ling", "02", "yueling", "000000", EmployeeAccessRightEnum.FINANCESTAFF));
            staffEntityController.createNewStaffEntity(new StaffEntity("Wei Liang", "Tan", "03", "weiliang", "000000", EmployeeAccessRightEnum.SALESSTAFF));

            creditPackageEntityController.createNewCreditPackage(new CreditPackageEntity(new BigDecimal(5), new BigDecimal(5), "5 for 5", false));
            creditPackageEntityController.createNewCreditPackage(new CreditPackageEntity(new BigDecimal(10), new BigDecimal(9), "9 for 10", false));
            creditPackageEntityController.createNewCreditPackage(new CreditPackageEntity(new BigDecimal(100), new BigDecimal(85), "85 for 100", false));

            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
            auctionEntityController.createNewAuction(new AuctionEntity(formatter.parse("23:59:59 28/10/2017"), formatter.parse("23:59:59 03/11/2017"), StatusEnum.CLOSED, new BigDecimal(77), "Totoro", "Cute Totoro!"));
            auctionEntityController.createNewAuction(new AuctionEntity(formatter.parse("23:59:59 28/10/2017"), formatter.parse("23:59:59 03/11/2018"), StatusEnum.CLOSED, new BigDecimal(10), "Cup", "Drink Water"));
            auctionEntityController.createNewAuction(new AuctionEntity(formatter.parse("23:59:59 28/11/2018"), formatter.parse("23:59:59 03/11/2019"), StatusEnum.CLOSED, new BigDecimal(92), "Apple", "Sweet Apple"));
        } catch (StaffAlreadyExistException | GeneralException | CreditPackageAlreadyExistException | ParseException | AuctionAlreadyExistException ex) {
            System.out.println("Error in Singleton");
        }
    }

}

//private void initializeDataAuction() {
//  AuctionEntity auction = new AuctionEntity(Long id, Date startingTime, Date endingTime, Boolean status, BigDecimal reservePrice, BigDecimal winningBid, String productName, String productDescription, Long winningCustomerId, List<BidEntity> bidEntities);
    //}
