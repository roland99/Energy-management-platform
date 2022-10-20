package ro.tuc.ds2020.dtos.builders;

import ro.tuc.ds2020.dtos.ClientDto;
import ro.tuc.ds2020.entities.Client;

public class ClientBuilder {

    private ClientBuilder(){

    }

    public static ClientDto toClientDto(Client client){
        return new ClientDto(client.getId(),
                client.getEmail(),
                "",
                client.getName(),
                client.getBirthDate(),
                client.getAddress(),
                client.getIsAdmin(),
                client.getDeviceList());
    }

    public static Client toEntity(ClientDto clientDto){
        return new Client(clientDto.getId(),
                clientDto.getEmail(),
                clientDto.getPassword(),
                clientDto.getName(),
                clientDto.getBirthDate(),
                clientDto.getAddress(),
                clientDto.getIsAdmin(),
                clientDto.getDeviceList());
    }
}
