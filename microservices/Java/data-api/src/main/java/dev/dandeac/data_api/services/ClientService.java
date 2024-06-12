package dev.dandeac.data_api.services;

import dev.dandeac.data_api.dtos.ClientDTO;
import dev.dandeac.data_api.dtos.builders.ClientBuilder;
import dev.dandeac.data_api.entity.Client;
import dev.dandeac.data_api.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ClientService {
    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    public List<ClientDTO> findClients() {
        List<Client> clientList = clientRepository.findAll();
        return clientList.stream()
                .map(ClientBuilder::toClientDTO)
                .collect(Collectors.toList());
    }

    public ClientDTO addClient(ClientDTO clientDTO) {

        if (clientRepository.existsByFirmName(clientDTO.getFirmName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Client with name " + clientDTO.getFirmName() + " already exists");
        }
        Client client = ClientBuilder.toClient(clientDTO);
        Client savedClient = clientRepository.save(client);
        return ClientBuilder.toClientDTO(savedClient);
    }

    public void deleteClient(String clientId) {
        if (!clientRepository.existsById(UUID.fromString(clientId))) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client with id " + clientId + " does not exist");
        }
        clientRepository.deleteById(UUID.fromString(clientId));
    }

    public ClientDTO updateClient(String clientId, ClientDTO clientDTO) {
        if (!clientRepository.existsById(UUID.fromString(clientId))) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client with id " + clientId + " does not exist");
        }

        if (clientRepository.existsByFirmName(clientDTO.getFirmName()) && !clientRepository.findByFirmName(clientDTO.getFirmName()).getClientId().equals(UUID.fromString(clientId) )){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Client with name " + clientDTO.getFirmName() + " already exists");
        }
        Client client = ClientBuilder.toClient(clientDTO);
        client.setClientId(UUID.fromString(clientId));
        Client updatedClient = clientRepository.save(client);
        return ClientBuilder.toClientDTO(updatedClient);
    }

    public ClientDTO findClientById(String clientId) {
        Client client = clientRepository.findById(UUID.fromString(clientId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client with id " + clientId + " does not exist"));
        return ClientBuilder.toClientDTO(client);
    }

    public void deleteAllClients() {
        clientRepository.deleteAll();
    }

    public String getWazeUrl(Client client) {
        double latitude = client.getLatitude();
        double longitude = client.getLongitude();
        return "https://waze.com/ul?ll=" + latitude + "," + longitude + "&navigate=yes";
    }

    public String getGoogleMapsUrl(Client client) {
        double latitude = client.getLatitude();
        double longitude = client.getLongitude();
        return "https://www.google.com/maps/?q=" + latitude + "," + longitude;
    }

    public Client findClientEntityById(String clientId) {
        return clientRepository.findById(UUID.fromString(clientId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client with id " + clientId + " does not exist"));
    }
}
