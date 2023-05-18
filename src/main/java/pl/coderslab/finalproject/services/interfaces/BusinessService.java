package pl.coderslab.finalproject.services.interfaces;

import pl.coderslab.finalproject.entities.Business;
import pl.coderslab.finalproject.entities.User;

import java.util.List;
import java.util.Optional;

public interface BusinessService {

    List<Business> findAllBusinessNameByUserId(Long userId);

    Optional<Business> get(Long id);

    void add(Business business);


}
