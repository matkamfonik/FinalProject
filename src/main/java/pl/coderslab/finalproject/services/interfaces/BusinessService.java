package pl.coderslab.finalproject.services.interfaces;

import pl.coderslab.finalproject.CurrentUser;
import pl.coderslab.finalproject.dtos.BusinessDTO;
import pl.coderslab.finalproject.entities.Business;

import java.util.List;
import java.util.Optional;

public interface BusinessService {

    List<BusinessDTO> findAllBusinesses(CurrentUser currentUser);

    BusinessDTO getDTO(Long id);

    Optional<Business> get(Long id);

    void add(BusinessDTO businessDTO, CurrentUser currentUser);

    void delete(Long id);


}
