package pl.coderslab.finalproject.services.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import pl.coderslab.finalproject.dtos.ClientDTO;
import pl.coderslab.finalproject.entities.Client;
import pl.coderslab.finalproject.entities.User;
import pl.coderslab.finalproject.httpClients.BlockFirmy;
import pl.coderslab.finalproject.httpClients.Firma;
import pl.coderslab.finalproject.repository.ClientRepository;
import pl.coderslab.finalproject.services.interfaces.ClientService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class ClientApiService implements ClientService {
    private final ClientRepository clientRepository;

    @Override
    public List<Client> findClients(User user) {
        Long userId = user.getId();
        return clientRepository.findAllClientNameByUserId(userId);
    }

    @Override
    public Optional<Client> get(Long id) {
        return clientRepository.findById(id);
    }

    @Override
    public void add(Client client) {
        clientRepository.save(client);
    }

    @Override
    public void delete(Long clientId) {
        clientRepository.deleteById(clientId);
    }

    public ClientDTO extractClientDTO(BlockFirmy blockFirmy) {
        Firma firma = blockFirmy.getFirma()[0];
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setName(firma.getNazwa());
        clientDTO.setCity(firma.getAdresKorespondencyjny().getMiasto());
        clientDTO.setPostalCode(firma.getAdresKorespondencyjny().getKod());
        clientDTO.setStreet(firma.getAdresKorespondencyjny().getUlica());
        clientDTO.setNumber(firma.getAdresKorespondencyjny().getBudynek());
        clientDTO.setApartmentNumber(firma.getAdresKorespondencyjny().getLokal());
        clientDTO.setNip(firma.getWlasciciel().getNip());
        clientDTO.setRegon(firma.getWlasciciel().getRegon());
        return clientDTO;
    }
}
