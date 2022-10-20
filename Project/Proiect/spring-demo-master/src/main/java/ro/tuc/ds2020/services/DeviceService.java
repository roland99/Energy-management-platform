package ro.tuc.ds2020.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.ResourceNotFoundException;
import ro.tuc.ds2020.dtos.ClientDto;
import ro.tuc.ds2020.dtos.DeviceClientLinkDto;
import ro.tuc.ds2020.dtos.DeviceDto;
import ro.tuc.ds2020.dtos.DeviceSensorLinkDto;
import ro.tuc.ds2020.dtos.builders.DeviceBuilder;
import ro.tuc.ds2020.entities.Client;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.entities.Sensor;
import ro.tuc.ds2020.repositories.DeviceRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DeviceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceService.class);

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private SensorService sensorService;

    @Autowired
    private ClientService clientService;

    public List<DeviceDto> findAll(){
        List<Device> deviceList = deviceRepository.findAll();
        return deviceList.stream()
                .map(DeviceBuilder::toDeciveDto)
                .collect(Collectors.toList());
    }

    public DeviceDto findById(UUID id){
        Optional<Device> device = deviceRepository.findById(id);
        if(!device.isPresent()){
            LOGGER.error("Device with id {} was not found", id);
            throw new ResourceNotFoundException(Device.class.getSimpleName() + "with id: " + id);
        }
        return DeviceBuilder.toDeciveDto(device.get());
    }

    public UUID createDevice(DeviceDto deviceDto){
        Device device = DeviceBuilder.toEntity(deviceDto);
        device.setAverageConsumption(0.0);
        device.setMaxConsumption(0.0);
        device = deviceRepository.save(device);
        LOGGER.info("Device with id: " + device.getId() + " was created");
        return device.getId();
    }
    public boolean updateDeviceDetails(DeviceDto deviceDto){
        try{
            Optional<Device> prosumerDevice = deviceRepository.findById(deviceDto.getId());
            if(prosumerDevice.isPresent()) {
                Device device = prosumerDevice.get();
                device.setAddress(deviceDto.getAddress());
                device.setDescription(deviceDto.getDescription());
                deviceRepository.save(device);
                LOGGER.info("Device description updated: " + device.getId());
                return true;
            }else{
                LOGGER.error("Device with id: " + deviceDto.getId() + " not found");
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteDevice(UUID id){
        try{
            deviceRepository.delete(deviceRepository.findById(id).get());
            LOGGER.info("Device deleted: " + id);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean addSensor(DeviceSensorLinkDto dto){
        try{
            Sensor sensor = sensorService.findByIdEntity(dto.getSensorId());
            Optional<Device> deviceOptional = deviceRepository.findById(dto.getDeviceId());
            if(!deviceOptional.isPresent()){
                LOGGER.error("Device with id {} was not found", dto.getDeviceId());
                throw new ResourceNotFoundException(Device.class.getSimpleName() + "with id: " + dto.getDeviceId());
            }
            Device device = deviceOptional.get();
            device.setSensor(sensor);
            deviceRepository.save(device);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean addClient(DeviceClientLinkDto dto){
        try{
            Client client = clientService.findByIdEntity(dto.getClientId());
            Optional<Device> deviceOptional = deviceRepository.findById(dto.getDeviceId());
            if(!deviceOptional.isPresent()){
                LOGGER.error("Device with id {} was not found", dto.getDeviceId());
                throw new ResourceNotFoundException(Device.class.getSimpleName() + "with id: " + dto.getDeviceId());
            }
            Device device = deviceOptional.get();
            device.setClient(client);
            deviceRepository.save(device);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


}
