package ro.tuc.ds2020.dtos.builders;

import ro.tuc.ds2020.dtos.SensorDto;
import ro.tuc.ds2020.entities.Sensor;

public class SensorBuilder {

    public static SensorDto toSensorDto(Sensor sensor){
        return new SensorDto(sensor.getId(),
                sensor.getDescription(),
                sensor.getMaxValue(),
                sensor.getDevice());
    }

    public static Sensor toEntity(SensorDto sensorDto){
        return new Sensor(sensorDto.getId(),
                sensorDto.getDescription(),
                sensorDto.getMaxValue(),
                sensorDto.getDevice());
    }
}
