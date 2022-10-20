package ro.tuc.ds2020.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.ClientDto;
import ro.tuc.ds2020.dtos.LoginDto;
import ro.tuc.ds2020.dtos.WebSocketMessageDto;
import ro.tuc.ds2020.services.ClientService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@CrossOrigin
@RequestMapping
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/allClient")
    public ResponseEntity<List<ClientDto>> getAll(){
        List<ClientDto> dtos = clientService.findAll();
        for(ClientDto dto: dtos){
            Link clientLink = linkTo(methodOn(ClientController.class)
                .getOne(dto.getId())).withRel("clientDetails");
            dto.add(clientLink);
        }
        return new ResponseEntity<>(dtos,HttpStatus.OK);
    }

    @GetMapping(value = "/client/{id}")
    public ResponseEntity<ClientDto> getOne(@PathVariable("id") UUID clientId){
        ClientDto dto = clientService.findById(clientId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }



    @PostMapping("/createClient")
    public ResponseEntity<UUID> insertClient(@Valid @RequestBody ClientDto clientDto){
        UUID clientID = clientService.createClient(clientDto);
        return new ResponseEntity<>(clientID,HttpStatus.CREATED);
    }

    @PostMapping("/updateClient")
    public ResponseEntity<UUID> updateClient( @RequestBody ClientDto clientDto){
        System.out.println("Intrare: " + clientDto.toString());
        if(clientService.updateClient(clientDto)){
            return new ResponseEntity<>(clientDto.getId(),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(clientDto.getId(), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PostMapping("/deleteClient")
    public ResponseEntity<UUID> deleteClient(@RequestBody UUID id){
        System.out.println("ID: " + id.toString());
        if(clientService.deleteClient(id)){
            return new ResponseEntity<>(id, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(id, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginDto> loginClient(@RequestBody LoginDto loginDto){
        System.out.println("C:loginDto: " + loginDto);
        LoginDto response = clientService.loginUser(loginDto);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @SendTo("/user")
    public WebSocketMessageDto sendToWebSocket(@Payload WebSocketMessageDto message){
        System.out.println("Controller Socket: " + message.getMessage());
        return message;
    }
}
