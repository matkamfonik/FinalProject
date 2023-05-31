package pl.coderslab.finalproject.mappers;

import org.springframework.stereotype.Component;
import pl.coderslab.finalproject.dtos.BusinessDTO;
import pl.coderslab.finalproject.entities.Business;
import pl.coderslab.finalproject.entities.TaxationForm;
import pl.coderslab.finalproject.entities.User;


@Component
public class BusinessMapper {

    public Business toEntity(BusinessDTO businessDTO, User user, TaxationForm taxationForm) {
        Business business = new Business();
        business.setId(businessDTO.getId());
        business.setName(businessDTO.getName());
        business.setCity(businessDTO.getCity());
        business.setPostalCode(businessDTO.getPostalCode());
        business.setStreet(businessDTO.getStreet());
        business.setNumber(businessDTO.getNumber());
        business.setApartmentNumber(businessDTO.getApartmentNumber());
        business.setNip(businessDTO.getNip());
        business.setRegon(businessDTO.getRegon());
        business.setUser(user);
        business.setTaxationForm(taxationForm);
        return business;
    }

    public BusinessDTO toDto(Business business) {
        BusinessDTO businessDTO = new BusinessDTO();
        businessDTO.setName(business.getName());
        businessDTO.setId(business.getId());
        businessDTO.setCity(business.getCity());
        businessDTO.setStreet(business.getStreet());
        businessDTO.setPostalCode(business.getPostalCode());
        businessDTO.setNumber(business.getNumber());
        businessDTO.setApartmentNumber(business.getApartmentNumber());
        businessDTO.setNip(business.getNip());
        businessDTO.setRegon(business.getRegon());
        businessDTO.setTaxationFormId(business.getTaxationForm().getId());
        businessDTO.setTaxationFormName(business.getTaxationForm().getName());
        return businessDTO;
    }
}
