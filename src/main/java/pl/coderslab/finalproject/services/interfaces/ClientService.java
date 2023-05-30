package pl.coderslab.finalproject.services.interfaces;

import pl.coderslab.finalproject.CurrentUser;
import pl.coderslab.finalproject.dtos.ClientDTO;
import pl.coderslab.finalproject.entities.Business;
import pl.coderslab.finalproject.entities.Client;
import pl.coderslab.finalproject.httpClients.BlockFirmy;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    List<ClientDTO> findAllClients(CurrentUser currentUser);

    ClientDTO get(Long id);

    void add(ClientDTO clientDTO, CurrentUser currentUser);

    ClientDTO getClientDTO (BlockFirmy blockFirmy);


}
