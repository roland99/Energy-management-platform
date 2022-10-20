package ro.tuc.ds2020.dtos.builders;

import ro.tuc.ds2020.dtos.DeviceDto;
import ro.tuc.ds2020.entities.Device;

public class DeviceBuilder {

    public static DeviceDto toDeciveDto(Device device){

        return new DeviceDto(device.getId(),
                device.getDescription(),
                device.getAddress(),
                device.getMaxConsumption(),
                device.getAverageConsumption(),
                device.getClient(),
                device.getSensor());
    }

    public static Device toEntity(DeviceDto deviceDto){
        return new Device(deviceDto.getId(),
                deviceDto.getDescription(),
                deviceDto.getAddress(),
                deviceDto.getMaxConsumption(),
                deviceDto.getAverageConsumption(),
                deviceDto.getClient(),
                deviceDto.getSensor());
    }
}
