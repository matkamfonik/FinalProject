package pl.coderslab.finalproject.mappers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Component;
import pl.coderslab.finalproject.CurrentUser;
import pl.coderslab.finalproject.dtos.BusinessDTO;
import pl.coderslab.finalproject.entities.Business;
import pl.coderslab.finalproject.entities.TaxationForm;
import pl.coderslab.finalproject.entities.User;

import java.util.ArrayList;

@Component
public class BusinessMapper {

    public Business toEntity(BusinessDTO businessDTO, User user, TaxationForm taxationForm){
        Business business = new Business();
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
}
