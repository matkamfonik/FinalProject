package pl.coderslab.finalproject.services.interfaces;

import pl.coderslab.finalproject.CurrentUser;
import pl.coderslab.finalproject.dtos.ClientDTO;
import pl.coderslab.finalproject.httpClients.BlockFirmy;

import java.util.List;

public interface ClientService {

    List<ClientDTO> findAllClients(CurrentUser currentUser);

    ClientDTO getDTO(Long id);

    void add(ClientDTO clientDTO, CurrentUser currentUser);

    ClientDTO extractClientDTO(BlockFirmy blockFirmy);


}
