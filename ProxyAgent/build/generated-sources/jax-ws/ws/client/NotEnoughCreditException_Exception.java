
package ws.client;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.11-b150120.1832
 * Generated source version: 2.2
 * 
 */
@WebFault(name = "NotEnoughCreditException", targetNamespace = "http://ws.session.ejb/")
public class NotEnoughCreditException_Exception
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private NotEnoughCreditException faultInfo;

    /**
     * 
     * @param faultInfo
     * @param message
     */
    public NotEnoughCreditException_Exception(String message, NotEnoughCreditException faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param faultInfo
     * @param cause
     * @param message
     */
    public NotEnoughCreditException_Exception(String message, NotEnoughCreditException faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: ws.client.NotEnoughCreditException
     */
    public NotEnoughCreditException getFaultInfo() {
        return faultInfo;
    }

}