package pl.coderslab.finalproject.mappers;

import org.springframework.stereotype.Component;
import pl.coderslab.finalproject.dtos.ClientDTO;
import pl.coderslab.finalproject.entities.Client;
import pl.coderslab.finalproject.entities.User;


@Component
public class ClientMapper {

    public Client toEntity(ClientDTO clientDTO, User user) {
        Client client = new Client();
        client.setId(clientDTO.getId());
        client.setName(clientDTO.getName());
        client.setCity(clientDTO.getCity());
        client.setPostalCode(clientDTO.getPostalCode());
        client.setStreet(clientDTO.getStreet());
        client.setNumber(clientDTO.getNumber());
        client.setApartmentNumber(clientDTO.getApartmentNumber());
        client.setNip(clientDTO.getNip());
        client.setRegon(clientDTO.getRegon());
        client.setUser(user);
        return client;
    }

    public ClientDTO toDto(Client client) {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setName(client.getName());
        clientDTO.setId(client.getId());
        clientDTO.setCity(client.getCity());
        clientDTO.setStreet(client.getStreet());
        clientDTO.setPostalCode(client.getPostalCode());
        clientDTO.setNumber(client.getNumber());
        clientDTO.setApartmentNumber(client.getApartmentNumber());
        clientDTO.setNip(client.getNip());
        clientDTO.setRegon(client.getRegon());
        return clientDTO;
    }
}
