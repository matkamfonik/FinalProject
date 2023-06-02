package pl.coderslab.finalproject.services.interfaces;

import pl.coderslab.finalproject.dtos.ClientDTO;
import pl.coderslab.finalproject.entities.Client;
import pl.coderslab.finalproject.entities.User;
import pl.coderslab.finalproject.httpClients.BlockFirmy;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    List<Client> findClients(User user);

    Optional<Client> get(Long id);

    void add(Client client);

    void delete(Long clientId);

    ClientDTO extractClientDTO(BlockFirmy blockFirmy);


}
