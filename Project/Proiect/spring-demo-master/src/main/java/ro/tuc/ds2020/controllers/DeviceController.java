package ro.tuc.ds2020.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.DeviceClientLinkDto;
import ro.tuc.ds2020.dtos.DeviceDto;
import ro.tuc.ds2020.dtos.DeviceSensorLinkDto;
import ro.tuc.ds2020.services.DeviceService;

import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@CrossOrigin
@RequestMapping
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @GetMapping("/allDevice")
    public ResponseEntity<List<DeviceDto>> getAll(){
        List<DeviceDto> dtos = deviceService.findAll();
        for(DeviceDto dto: dtos){
            Link deviceLink = linkTo(methodOn(DeviceController.class)
            .getOneDevice(dto.getId())).withRel("deviceDetail");
            dto.add(deviceLink);
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping(value = "device/{id}")
    public ResponseEntity<DeviceDto> getOneDevice(@PathVariable("id") UUID id){
        DeviceDto deviceDto = deviceService.findById(id);
        return new ResponseEntity<>(deviceDto, HttpStatus.OK);
    }

    @PostMapping("/createDevice")
    public ResponseEntity<UUID> insertDevice(@RequestBody DeviceDto deviceDto){
        UUID deviceId = deviceService.createDevice(deviceDto);
        return new ResponseEntity<>(deviceId,HttpStatus.OK);
    }


    @PostMapping("/updateDevice")
    public ResponseEntity<UUID> updateDevice(@RequestBody DeviceDto deviceDto){
        if(deviceService.updateDeviceDetails(deviceDto)){
            return new ResponseEntity<>(deviceDto.getId(),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(deviceDto.getId(), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PostMapping("/deleteDevice")
    public ResponseEntity<UUID> deleteDevice(@RequestBody UUID id){
        if(deviceService.deleteDevice(id)){
            return new ResponseEntity<>(id, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(id, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PostMapping("/addSensorToDevice")
    public ResponseEntity<UUID> addSensor(@RequestBody DeviceSensorLinkDto deviceSensorLinkDto){
        System.out.println("OOO: " + deviceSensorLinkDto.toString());
        if(deviceService.addSensor(deviceSensorLinkDto)){
            return new ResponseEntity<>(deviceSensorLinkDto.getDeviceId(), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(deviceSensorLinkDto.getDeviceId(), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PostMapping("/addClientToDevice")
    public ResponseEntity<UUID> addClient(@RequestBody DeviceClientLinkDto deviceClientLinkDto){
        System.out.println("OOO: " + deviceClientLinkDto.toString());
        if(deviceService.addClient(deviceClientLinkDto)){
            return new ResponseEntity<>(deviceClientLinkDto.getDeviceId(), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(deviceClientLinkDto.getDeviceId(), HttpStatus.EXPECTATION_FAILED);
        }
    }

}
