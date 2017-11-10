
package ws.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ws.client package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _AuctionNotFoundException_QNAME = new QName("http://ws.session.ejb/", "AuctionNotFoundException");
    private final static QName _BidAlreadyExistException_QNAME = new QName("http://ws.session.ejb/", "BidAlreadyExistException");
    private final static QName _CustomerAlreadyPremiumException_QNAME = new QName("http://ws.session.ejb/", "CustomerAlreadyPremiumException");
    private final static QName _CustomerNotFoundException_QNAME = new QName("http://ws.session.ejb/", "CustomerNotFoundException");
    private final static QName _CustomerNotPremiumException_QNAME = new QName("http://ws.session.ejb/", "CustomerNotPremiumException");
    private final static QName _GeneralException_QNAME = new QName("http://ws.session.ejb/", "GeneralException");
    private final static QName _IncorrectPasswordException_QNAME = new QName("http://ws.session.ejb/", "IncorrectPasswordException");
    private final static QName _CustomerLogin_QNAME = new QName("http://ws.session.ejb/", "customerLogin");
    private final static QName _CustomerLoginResponse_QNAME = new QName("http://ws.session.ejb/", "customerLoginResponse");
    private final static QName _Persist_QNAME = new QName("http://ws.session.ejb/", "persist");
    private final static QName _PersistResponse_QNAME = new QName("http://ws.session.ejb/", "persistResponse");
    private final static QName _PlaceBid_QNAME = new QName("http://ws.session.ejb/", "placeBid");
    private final static QName _PlaceBidResponse_QNAME = new QName("http://ws.session.ejb/", "placeBidResponse");
    private final static QName _Registration_QNAME = new QName("http://ws.session.ejb/", "registration");
    private final static QName _RegistrationResponse_QNAME = new QName("http://ws.session.ejb/", "registrationResponse");
    private final static QName _ViewAllAuctionListings_QNAME = new QName("http://ws.session.ejb/", "viewAllAuctionListings");
    private final static QName _ViewAllAuctionListingsResponse_QNAME = new QName("http://ws.session.ejb/", "viewAllAuctionListingsResponse");
    private final static QName _ViewAuctionListByName_QNAME = new QName("http://ws.session.ejb/", "viewAuctionListByName");
    private final static QName _ViewAuctionListByNameResponse_QNAME = new QName("http://ws.session.ejb/", "viewAuctionListByNameResponse");
    private final static QName _ViewAuctionListDetails_QNAME = new QName("http://ws.session.ejb/", "viewAuctionListDetails");
    private final static QName _ViewAuctionListDetailsResponse_QNAME = new QName("http://ws.session.ejb/", "viewAuctionListDetailsResponse");
    private final static QName _ViewCreditBalance_QNAME = new QName("http://ws.session.ejb/", "viewCreditBalance");
    private final static QName _ViewCreditBalanceResponse_QNAME = new QName("http://ws.session.ejb/", "viewCreditBalanceResponse");
    private final static QName _ViewWonAuctionListings_QNAME = new QName("http://ws.session.ejb/", "viewWonAuctionListings");
    private final static QName _ViewWonAuctionListingsResponse_QNAME = new QName("http://ws.session.ejb/", "viewWonAuctionListingsResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ws.client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AuctionNotFoundException }
     * 
     */
    public AuctionNotFoundException createAuctionNotFoundException() {
        return new AuctionNotFoundException();
    }

    /**
     * Create an instance of {@link BidAlreadyExistException }
     * 
     */
    public BidAlreadyExistException createBidAlreadyExistException() {
        return new BidAlreadyExistException();
    }

    /**
     * Create an instance of {@link CustomerAlreadyPremiumException }
     * 
     */
    public CustomerAlreadyPremiumException createCustomerAlreadyPremiumException() {
        return new CustomerAlreadyPremiumException();
    }

    /**
     * Create an instance of {@link CustomerNotFoundException }
     * 
     */
    public CustomerNotFoundException createCustomerNotFoundException() {
        return new CustomerNotFoundException();
    }

    /**
     * Create an instance of {@link CustomerNotPremiumException }
     * 
     */
    public CustomerNotPremiumException createCustomerNotPremiumException() {
        return new CustomerNotPremiumException();
    }

    /**
     * Create an instance of {@link GeneralException }
     * 
     */
    public GeneralException createGeneralException() {
        return new GeneralException();
    }

    /**
     * Create an instance of {@link IncorrectPasswordException }
     * 
     */
    public IncorrectPasswordException createIncorrectPasswordException() {
        return new IncorrectPasswordException();
    }

    /**
     * Create an instance of {@link CustomerLogin }
     * 
     */
    public CustomerLogin createCustomerLogin() {
        return new CustomerLogin();
    }

    /**
     * Create an instance of {@link CustomerLoginResponse }
     * 
     */
    public CustomerLoginResponse createCustomerLoginResponse() {
        return new CustomerLoginResponse();
    }

    /**
     * Create an instance of {@link Persist }
     * 
     */
    public Persist createPersist() {
        return new Persist();
    }

    /**
     * Create an instance of {@link PersistResponse }
     * 
     */
    public PersistResponse createPersistResponse() {
        return new PersistResponse();
    }

    /**
     * Create an instance of {@link PlaceBid }
     * 
     */
    public PlaceBid createPlaceBid() {
        return new PlaceBid();
    }

    /**
     * Create an instance of {@link PlaceBidResponse }
     * 
     */
    public PlaceBidResponse createPlaceBidResponse() {
        return new PlaceBidResponse();
    }

    /**
     * Create an instance of {@link Registration }
     * 
     */
    public Registration createRegistration() {
        return new Registration();
    }

    /**
     * Create an instance of {@link RegistrationResponse }
     * 
     */
    public RegistrationResponse createRegistrationResponse() {
        return new RegistrationResponse();
    }

    /**
     * Create an instance of {@link ViewAllAuctionListings }
     * 
     */
    public ViewAllAuctionListings createViewAllAuctionListings() {
        return new ViewAllAuctionListings();
    }

    /**
     * Create an instance of {@link ViewAllAuctionListingsResponse }
     * 
     */
    public ViewAllAuctionListingsResponse createViewAllAuctionListingsResponse() {
        return new ViewAllAuctionListingsResponse();
    }

    /**
     * Create an instance of {@link ViewAuctionListByName }
     * 
     */
    public ViewAuctionListByName createViewAuctionListByName() {
        return new ViewAuctionListByName();
    }

    /**
     * Create an instance of {@link ViewAuctionListByNameResponse }
     * 
     */
    public ViewAuctionListByNameResponse createViewAuctionListByNameResponse() {
        return new ViewAuctionListByNameResponse();
    }

    /**
     * Create an instance of {@link ViewAuctionListDetails }
     * 
     */
    public ViewAuctionListDetails createViewAuctionListDetails() {
        return new ViewAuctionListDetails();
    }

    /**
     * Create an instance of {@link ViewAuctionListDetailsResponse }
     * 
     */
    public ViewAuctionListDetailsResponse createViewAuctionListDetailsResponse() {
        return new ViewAuctionListDetailsResponse();
    }

    /**
     * Create an instance of {@link ViewCreditBalance }
     * 
     */
    public ViewCreditBalance createViewCreditBalance() {
        return new ViewCreditBalance();
    }

    /**
     * Create an instance of {@link ViewCreditBalanceResponse }
     * 
     */
    public ViewCreditBalanceResponse createViewCreditBalanceResponse() {
        return new ViewCreditBalanceResponse();
    }

    /**
     * Create an instance of {@link ViewWonAuctionListings }
     * 
     */
    public ViewWonAuctionListings createViewWonAuctionListings() {
        return new ViewWonAuctionListings();
    }

    /**
     * Create an instance of {@link ViewWonAuctionListingsResponse }
     * 
     */
    public ViewWonAuctionListingsResponse createViewWonAuctionListingsResponse() {
        return new ViewWonAuctionListingsResponse();
    }

    /**
     * Create an instance of {@link CustomerEntity }
     * 
     */
    public CustomerEntity createCustomerEntity() {
        return new CustomerEntity();
    }

    /**
     * Create an instance of {@link AddressEntity }
     * 
     */
    public AddressEntity createAddressEntity() {
        return new AddressEntity();
    }

    /**
     * Create an instance of {@link BidEntity }
     * 
     */
    public BidEntity createBidEntity() {
        return new BidEntity();
    }

    /**
     * Create an instance of {@link AuctionEntity }
     * 
     */
    public AuctionEntity createAuctionEntity() {
        return new AuctionEntity();
    }

    /**
     * Create an instance of {@link CreditTransactionEntity }
     * 
     */
    public CreditTransactionEntity createCreditTransactionEntity() {
        return new CreditTransactionEntity();
    }

    /**
     * Create an instance of {@link CreditPackageEntity }
     * 
     */
    public CreditPackageEntity createCreditPackageEntity() {
        return new CreditPackageEntity();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AuctionNotFoundException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "AuctionNotFoundException")
    public JAXBElement<AuctionNotFoundException> createAuctionNotFoundException(AuctionNotFoundException value) {
        return new JAXBElement<AuctionNotFoundException>(_AuctionNotFoundException_QNAME, AuctionNotFoundException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BidAlreadyExistException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "BidAlreadyExistException")
    public JAXBElement<BidAlreadyExistException> createBidAlreadyExistException(BidAlreadyExistException value) {
        return new JAXBElement<BidAlreadyExistException>(_BidAlreadyExistException_QNAME, BidAlreadyExistException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CustomerAlreadyPremiumException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "CustomerAlreadyPremiumException")
    public JAXBElement<CustomerAlreadyPremiumException> createCustomerAlreadyPremiumException(CustomerAlreadyPremiumException value) {
        return new JAXBElement<CustomerAlreadyPremiumException>(_CustomerAlreadyPremiumException_QNAME, CustomerAlreadyPremiumException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CustomerNotFoundException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "CustomerNotFoundException")
    public JAXBElement<CustomerNotFoundException> createCustomerNotFoundException(CustomerNotFoundException value) {
        return new JAXBElement<CustomerNotFoundException>(_CustomerNotFoundException_QNAME, CustomerNotFoundException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CustomerNotPremiumException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "CustomerNotPremiumException")
    public JAXBElement<CustomerNotPremiumException> createCustomerNotPremiumException(CustomerNotPremiumException value) {
        return new JAXBElement<CustomerNotPremiumException>(_CustomerNotPremiumException_QNAME, CustomerNotPremiumException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GeneralException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "GeneralException")
    public JAXBElement<GeneralException> createGeneralException(GeneralException value) {
        return new JAXBElement<GeneralException>(_GeneralException_QNAME, GeneralException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IncorrectPasswordException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "IncorrectPasswordException")
    public JAXBElement<IncorrectPasswordException> createIncorrectPasswordException(IncorrectPasswordException value) {
        return new JAXBElement<IncorrectPasswordException>(_IncorrectPasswordException_QNAME, IncorrectPasswordException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CustomerLogin }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "customerLogin")
    public JAXBElement<CustomerLogin> createCustomerLogin(CustomerLogin value) {
        return new JAXBElement<CustomerLogin>(_CustomerLogin_QNAME, CustomerLogin.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CustomerLoginResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "customerLoginResponse")
    public JAXBElement<CustomerLoginResponse> createCustomerLoginResponse(CustomerLoginResponse value) {
        return new JAXBElement<CustomerLoginResponse>(_CustomerLoginResponse_QNAME, CustomerLoginResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Persist }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "persist")
    public JAXBElement<Persist> createPersist(Persist value) {
        return new JAXBElement<Persist>(_Persist_QNAME, Persist.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PersistResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "persistResponse")
    public JAXBElement<PersistResponse> createPersistResponse(PersistResponse value) {
        return new JAXBElement<PersistResponse>(_PersistResponse_QNAME, PersistResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PlaceBid }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "placeBid")
    public JAXBElement<PlaceBid> createPlaceBid(PlaceBid value) {
        return new JAXBElement<PlaceBid>(_PlaceBid_QNAME, PlaceBid.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PlaceBidResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "placeBidResponse")
    public JAXBElement<PlaceBidResponse> createPlaceBidResponse(PlaceBidResponse value) {
        return new JAXBElement<PlaceBidResponse>(_PlaceBidResponse_QNAME, PlaceBidResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Registration }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "registration")
    public JAXBElement<Registration> createRegistration(Registration value) {
        return new JAXBElement<Registration>(_Registration_QNAME, Registration.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RegistrationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "registrationResponse")
    public JAXBElement<RegistrationResponse> createRegistrationResponse(RegistrationResponse value) {
        return new JAXBElement<RegistrationResponse>(_RegistrationResponse_QNAME, RegistrationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ViewAllAuctionListings }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "viewAllAuctionListings")
    public JAXBElement<ViewAllAuctionListings> createViewAllAuctionListings(ViewAllAuctionListings value) {
        return new JAXBElement<ViewAllAuctionListings>(_ViewAllAuctionListings_QNAME, ViewAllAuctionListings.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ViewAllAuctionListingsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "viewAllAuctionListingsResponse")
    public JAXBElement<ViewAllAuctionListingsResponse> createViewAllAuctionListingsResponse(ViewAllAuctionListingsResponse value) {
        return new JAXBElement<ViewAllAuctionListingsResponse>(_ViewAllAuctionListingsResponse_QNAME, ViewAllAuctionListingsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ViewAuctionListByName }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "viewAuctionListByName")
    public JAXBElement<ViewAuctionListByName> createViewAuctionListByName(ViewAuctionListByName value) {
        return new JAXBElement<ViewAuctionListByName>(_ViewAuctionListByName_QNAME, ViewAuctionListByName.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ViewAuctionListByNameResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "viewAuctionListByNameResponse")
    public JAXBElement<ViewAuctionListByNameResponse> createViewAuctionListByNameResponse(ViewAuctionListByNameResponse value) {
        return new JAXBElement<ViewAuctionListByNameResponse>(_ViewAuctionListByNameResponse_QNAME, ViewAuctionListByNameResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ViewAuctionListDetails }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "viewAuctionListDetails")
    public JAXBElement<ViewAuctionListDetails> createViewAuctionListDetails(ViewAuctionListDetails value) {
        return new JAXBElement<ViewAuctionListDetails>(_ViewAuctionListDetails_QNAME, ViewAuctionListDetails.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ViewAuctionListDetailsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "viewAuctionListDetailsResponse")
    public JAXBElement<ViewAuctionListDetailsResponse> createViewAuctionListDetailsResponse(ViewAuctionListDetailsResponse value) {
        return new JAXBElement<ViewAuctionListDetailsResponse>(_ViewAuctionListDetailsResponse_QNAME, ViewAuctionListDetailsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ViewCreditBalance }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "viewCreditBalance")
    public JAXBElement<ViewCreditBalance> createViewCreditBalance(ViewCreditBalance value) {
        return new JAXBElement<ViewCreditBalance>(_ViewCreditBalance_QNAME, ViewCreditBalance.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ViewCreditBalanceResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "viewCreditBalanceResponse")
    public JAXBElement<ViewCreditBalanceResponse> createViewCreditBalanceResponse(ViewCreditBalanceResponse value) {
        return new JAXBElement<ViewCreditBalanceResponse>(_ViewCreditBalanceResponse_QNAME, ViewCreditBalanceResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ViewWonAuctionListings }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "viewWonAuctionListings")
    public JAXBElement<ViewWonAuctionListings> createViewWonAuctionListings(ViewWonAuctionListings value) {
        return new JAXBElement<ViewWonAuctionListings>(_ViewWonAuctionListings_QNAME, ViewWonAuctionListings.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ViewWonAuctionListingsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "viewWonAuctionListingsResponse")
    public JAXBElement<ViewWonAuctionListingsResponse> createViewWonAuctionListingsResponse(ViewWonAuctionListingsResponse value) {
        return new JAXBElement<ViewWonAuctionListingsResponse>(_ViewWonAuctionListingsResponse_QNAME, ViewWonAuctionListingsResponse.class, null, value);
    }

}