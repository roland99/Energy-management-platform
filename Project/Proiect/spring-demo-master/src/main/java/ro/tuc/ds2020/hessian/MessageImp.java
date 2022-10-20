package ro.tuc.ds2020.hessian;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.dtos.SensorReadDto;
import ro.tuc.ds2020.entities.SensorRead;
import ro.tuc.ds2020.repositories.SensorReadRepository;
import ro.tuc.ds2020.services.QueueListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class MessageImp{


    @Autowired
    SensorReadRepository sensorReadRepository;


    public List<SensorReadDto> transferWithHessian(UUID id) {
        List<SensorRead> readList = sensorReadRepository.findAllBySensorIdOrderByTimestampDesc(id);
        if(readList.size()>0) {
            System.out.println("Gasite " + readList.size() + " intrari cu pt aces senosr");
            List<SensorReadDto> readDtoList = new ArrayList<>();
            for (SensorRead s : readList) {
                readDtoList.add(new SensorReadDto(s.getId(), s.getSensorId(), s.getTimestamp(), s.getMeasurement()));
            }
            return readDtoList;
        }
        System.out.println("=====SERVER=====");

        return new ArrayList<>();
    }
}
