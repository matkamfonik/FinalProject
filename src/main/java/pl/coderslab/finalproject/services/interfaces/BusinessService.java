package pl.coderslab.finalproject.services.interfaces;

import pl.coderslab.finalproject.CurrentUser;
import pl.coderslab.finalproject.dtos.BusinessDTO;
import pl.coderslab.finalproject.entities.Business;
import pl.coderslab.finalproject.entities.User;

import java.util.List;
import java.util.Optional;

public interface BusinessService {

    List<BusinessDTO> findAllBusinesses(CurrentUser currentUser);

    BusinessDTO get(Long id);

    void add(BusinessDTO businessDTO, CurrentUser currentUser);


}
