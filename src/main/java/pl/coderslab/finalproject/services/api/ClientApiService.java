package pl.coderslab.finalproject.services.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import pl.coderslab.finalproject.entities.Client;
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
    public List<Client> findAllClientNameByUserId(Long userId) {
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
}
