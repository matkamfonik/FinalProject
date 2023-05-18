package pl.coderslab.finalproject.services.interfaces;

import pl.coderslab.finalproject.entities.Business;
import pl.coderslab.finalproject.entities.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    List<Client> findAllClientNameByUserId(Long userId);

    Optional<Client> get(Long id);

    void add(Client client);


}
