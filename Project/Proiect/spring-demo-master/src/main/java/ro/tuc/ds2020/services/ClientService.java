package ro.tuc.ds2020.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.ResourceNotFoundException;
import ro.tuc.ds2020.dtos.ClientDto;
import ro.tuc.ds2020.dtos.LoginDto;
import ro.tuc.ds2020.dtos.builders.ClientBuilder;
import ro.tuc.ds2020.entities.Client;
import ro.tuc.ds2020.entities.Person;
import ro.tuc.ds2020.repositories.ClientRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ClientService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonService.class);

    @Autowired
    private ClientRepository clientRepository;


    @Bean   //to move to app security
    public BCryptPasswordEncoder bcryptPasswordEncoder(){return new BCryptPasswordEncoder();}

    @Autowired
    PasswordEncoder passwordEncoder;

    public List<ClientDto> findAll(){
        List<Client> clientList = clientRepository.findAll();
        return clientList.stream()
                .map(ClientBuilder::toClientDto)
                .collect(Collectors.toList());
    }

    public ClientDto findById(UUID id){
        Optional<Client> client = clientRepository.findById(id);
        if(!client.isPresent()){
            LOGGER.error("Client with id {} was not found in db", id);
            throw new ResourceNotFoundException(Client.class.getSimpleName() + " with id: " + id);
        }
        return ClientBuilder.toClientDto(client.get());
    }

    protected Client findByIdEntity(UUID id){
        Optional<Client> client = clientRepository.findById(id);
        if(!client.isPresent()){
            LOGGER.error("Client with id {} was not found in db", id);
            throw new ResourceNotFoundException(Client.class.getSimpleName() + " with id: " + id);
        }
        return client.get();
    }

    public UUID createClient(ClientDto clientDto){
        Client client = ClientBuilder.toEntity(clientDto);
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        client.setIsAdmin(false);
        client = clientRepository.save(client);
        LOGGER.info("Client with id {" + client.getId() + "} was inserted in db");
        return client.getId();
    }

    public boolean updateClient(ClientDto clientDto) {
        try {
            Optional<Client> prosumerClient = clientRepository.findById(clientDto.getId());

            if(prosumerClient.isPresent()) {
                Client client = prosumerClient.get();
                if (!StringUtils.hasText(clientDto.getPassword())) {
                    client.setPassword(passwordEncoder.encode(clientDto.getPassword()));
                }
                client.setName(clientDto.getName());
                client.setBirthDate(clientDto.getBirthDate());
                client.setAddress(clientDto.getAddress());
                clientRepository.save(client);
                LOGGER.info("Client updated: " + clientDto.getEmail());
                return true;
            }else {
                LOGGER.info("Clientul nu e prezent in baza de date pentru update");
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteClient(UUID clientId) {
        try {
            clientRepository.delete(clientRepository.findById(clientId).get());
            LOGGER.info("Client deleted: " + clientId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public LoginDto loginUser(LoginDto loginDto){
        LoginDto respone = new LoginDto();
        System.out.println("S:loginDto: "+ loginDto.toString());
        Client client = clientRepository.findByEmail(loginDto.getEmail());
        if(client != null){
            System.out.println("S2: A fost gasit");
            if(passwordEncoder.matches(loginDto.getPassword(), client.getPassword())){
                respone.setLoginSuccess(true);
                respone.setIsAdmin(client.getIsAdmin());
                respone.setEmail(client.getEmail());
                respone.setIdUser(client.getId());
            }else{
                respone.setLoginSuccess(false);
            }
        }else{
            System.out.println("S3: Este null");
            respone.setLoginSuccess(false);
        }

        return respone;
    }


}
