package dev.dandeac.data_api.dtos.builders;

import dev.dandeac.data_api.dtos.ClientDTO;
import dev.dandeac.data_api.entity.Client;

public class ClientBuilder {
    private ClientBuilder() {
    }

    public static ClientDTO toClientDTO(Client client) {
        return new ClientDTO(client.getClientId(), client.getFirmName(), client.getContactPerson(), client.getPhoneNumber(), client.getLocation(), client.getLatitude(), client.getLongitude(), client.getAddress(), client.getType());
    }

    public static Client toClient(ClientDTO clientDTO) {
        return new Client(clientDTO.getFirmName(), clientDTO.getContactPerson(), clientDTO.getPhoneNumber(), clientDTO.getLocation(), clientDTO.getLatitude(), clientDTO.getLongitude(), clientDTO.getAddress(), clientDTO.getType());
    }
}
