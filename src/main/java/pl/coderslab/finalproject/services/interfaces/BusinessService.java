package pl.coderslab.finalproject.services.interfaces;

import pl.coderslab.finalproject.CurrentUser;
import pl.coderslab.finalproject.entities.Business;

import java.util.List;
import java.util.Optional;

public interface BusinessService {

    List<Business> findAllBusinesses(CurrentUser currentUser);

    Optional<Business> get(Long id);

    void add(Business business);

    void delete(Long id);


}
