package ro.tuc.ds2020.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.SensorDto;
import ro.tuc.ds2020.services.SensorService;

import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;



@RestController
@CrossOrigin
@RequestMapping
public class SensorController {

    @Autowired
    private SensorService sensorService;

    @GetMapping("/allSensor")
    public ResponseEntity<List<SensorDto>> getAll(){
        List<SensorDto> dtos = sensorService.findAll();
        for(SensorDto dto: dtos){
            Link sensorLink = linkTo(methodOn(SensorController.class)
            .getOne(dto.getId())).withRel("sensorDetail");
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping("/allSensorFree")
    public ResponseEntity<List<SensorDto>> getAllFree(){
        List<SensorDto> dtos = sensorService.findFree();
        for(SensorDto dto: dtos){
            Link sensorLink = linkTo(methodOn(SensorController.class)
                    .getOne(dto.getId())).withRel("sensorDetail");
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping("/sensor/{id}")
    public ResponseEntity<SensorDto> getOne(@PathVariable("id") UUID sensorId){
        SensorDto sensorDto = sensorService.findById(sensorId);
        return new ResponseEntity<>(sensorDto, HttpStatus.OK);
    }

    @PostMapping(value = "/createSensor")
    public ResponseEntity<UUID> insertSensor(@RequestBody SensorDto sensorDto){
        System.out.println("SENSOR: " + sensorDto.toString());
        UUID sensorId = sensorService.createSesor(sensorDto);
        return new ResponseEntity<>(sensorId,HttpStatus.CREATED);
    }

    @GetMapping("/updateSensor")
    public ResponseEntity<SensorDto> updateSensor(@RequestParam("id") UUID sensorID){
        SensorDto sensorDto = sensorService.findById(sensorID);
        return new ResponseEntity<>(sensorDto,HttpStatus.OK);
    }

    @PostMapping("/updateSensor")
    public ResponseEntity<UUID> updateSensor(@RequestBody SensorDto sensorDto){
        if(sensorService.updateSensor(sensorDto)){
            return new ResponseEntity<>(sensorDto.getId(),HttpStatus.OK);
        }else {
            return new ResponseEntity<>(sensorDto.getId(),HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PostMapping("/deleteSensor")
    public ResponseEntity<UUID> deleteSensor(@RequestBody UUID id){
        if(sensorService.deleteSensor(id)){
            return new ResponseEntity<>(id ,HttpStatus.OK);
        }else {
            return new ResponseEntity<>(id ,HttpStatus.EXPECTATION_FAILED);
        }
    }
}
