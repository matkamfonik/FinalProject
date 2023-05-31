package pl.coderslab.finalproject.services.api;

import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import pl.coderslab.finalproject.CurrentUser;
import pl.coderslab.finalproject.dtos.ClientDTO;
import pl.coderslab.finalproject.entities.Client;
import pl.coderslab.finalproject.httpClients.BlockFirmy;
import pl.coderslab.finalproject.httpClients.Firma;
import pl.coderslab.finalproject.mappers.ClientMapper;
import pl.coderslab.finalproject.repository.ClientRepository;
import pl.coderslab.finalproject.services.interfaces.ClientService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class ClientApiService implements ClientService {
    private final ClientRepository clientRepository;

    private final ClientMapper clientMapper;

    @Override
    public List<ClientDTO> findAllClients(CurrentUser currentUser) {
        Long userId = currentUser.getUser().getId();
        return clientRepository.findAllClientNameByUserId(userId).stream().map(clientMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public ClientDTO getDTO(Long id) {
        Client client = clientRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return clientMapper.toDto(client);
    }

    @Override
    public void add(ClientDTO clientDTO, CurrentUser currentUser) {
        Client client = clientMapper.toEntity(clientDTO,
                currentUser.getUser());
        clientRepository.save(client);
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
