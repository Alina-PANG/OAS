/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AddressEntity;
import entity.CustomerEntity;
import java.math.BigDecimal;
import java.util.List;
import util.exception.AddressAlreadyExistsException;
import util.exception.AddressNotFoundException;
import util.exception.GeneralException;


/**
 *
 * @author alina
 */
public interface AddressEntityControllerLocal {

    public AddressEntity createAddress(AddressEntity address) throws GeneralException;

    public AddressEntity getAddressById(Long addressid);

    public List<AddressEntity> viewAllAddress(CustomerEntity customer);

    public boolean deleteAddress(Long id) throws AddressNotFoundException;

    public AddressEntity updateAddressLine(Long aid, String line) throws AddressNotFoundException, GeneralException;

    public AddressEntity updateAddressCode(Long aid, String code) throws AddressNotFoundException, GeneralException;

}
