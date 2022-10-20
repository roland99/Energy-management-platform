package ro.tuc.ds2020.services;

import com.caucho.hessian.io.HessianOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.ResourceNotFoundException;
import ro.tuc.ds2020.dtos.SensorDto;
import ro.tuc.ds2020.dtos.SensorReadDto;
import ro.tuc.ds2020.dtos.WebSocketMessageDto;
import ro.tuc.ds2020.dtos.builders.SensorBuilder;
import ro.tuc.ds2020.entities.Sensor;
import ro.tuc.ds2020.entities.SensorRead;
import ro.tuc.ds2020.hessian.Message;
import ro.tuc.ds2020.repositories.SensorReadRepository;
import ro.tuc.ds2020.repositories.SensorRepository;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SensorService implements Message {
    private static final Logger LOGGER = LoggerFactory.getLogger(SensorService.class);

    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    SimpMessagingTemplate template;

    @Autowired
    SensorReadRepository sensorReadRepository;

    public List<SensorDto> findAll(){
        List<Sensor> sensorList = sensorRepository.findAll();
        return sensorList.stream()
                .map(SensorBuilder::toSensorDto)
                .collect(Collectors.toList());
    }

    public List<SensorDto> findFree(){
        List<Sensor> sensorList = sensorRepository.findAllByDeviceNull();
        return sensorList.stream()
                .map(SensorBuilder::toSensorDto)
                .collect(Collectors.toList());
    }

    public SensorDto findById(UUID id){
        Optional<Sensor> sensor = sensorRepository.findById(id);
        if(!sensor.isPresent()) {
            LOGGER.error("Sensor with id {} was not found", id);
            throw new ResourceNotFoundException(Sensor.class.getSimpleName() + "with id: "+id);

        }
        return SensorBuilder.toSensorDto(sensor.get());
    }

    protected Sensor findByIdEntity(UUID id){
        Optional<Sensor> sensor = sensorRepository.findById(id);
        if(!sensor.isPresent()) {
            LOGGER.error("Sensor with id {} was not found", id);
            throw new ResourceNotFoundException(Sensor.class.getSimpleName() + "with id: "+id);

        }
        return sensor.get();
    }

    public UUID createSesor(SensorDto sensorDto){
        Sensor sensor = SensorBuilder.toEntity(sensorDto);
        if(sensorDto.getMaxValue() == null) {
            sensor.setMaxValue(0.0);
        }
        sensor = sensorRepository.save(sensor);
        LOGGER.info("Sensor with id {} was created", sensor.getId());
        return sensor.getId();
    }

    public boolean updateSensor(SensorDto sensorDto){
        try {
            Optional<Sensor> prosumerSensor = sensorRepository.findById(sensorDto.getId());

            if(prosumerSensor.isPresent()){
                Sensor sensor = prosumerSensor.get();

                sensor.setDescription(sensorDto.getDescription());
                sensor.setMaxValue(sensorDto.getMaxValue());
                sensorRepository.save(sensor);
                LOGGER.info("Sensor updated");
                return true;
            }else {
                LOGGER.error("Sensorul nu se afla in baza de date");
                return false;
            }

        }catch (Exception e){
            e.printStackTrace();
            LOGGER.error("Sensor update failed");
            return false;
        }
    }

    public boolean deleteSensor(UUID sensorId){
        try{
            sensorRepository.delete(sensorRepository.findById(sensorId).get());
            LOGGER.info("Sensor deleted: " + sensorId);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public void addMeasurement(SensorRead sensorRead, double peak, double average){
        try{
            Sensor sensor = sensorRepository.findById(sensorRead.getSensorId()).get();
            LOGGER.info("Update device: " + sensor.getDevice().getDescription() + " consumption to: " + sensorRead.getMeasurement());
            LOGGER.info("Peak: " + peak + " and avg: " + average);
            sensor.getDevice().setMaxConsumption(sensorRead.getMeasurement());
            sensor.getDevice().setAverageConsumption(average);
            if( peak > sensor.getMaxValue()){
                //send notification
                System.out.println("Peak detected !!!!");
                WebSocketMessageDto message = new WebSocketMessageDto("A fost depasit valoarea maxima al senzorului!");
                template.convertAndSend("/user", message);
            }
            sensorRepository.save(sensor);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public byte[] transferWithHessian(UUID id) {

        List<SensorRead> all = sensorReadRepository.findAll();

        List<SensorRead> readList = sensorReadRepository.findAllBySensorIdOrderByTimestampAsc(id);
        System.out.println("Primul: " + readList.get(2) + " " + id);
        System.out.println("Toti: " + all);
        if(readList.size()>0){
            System.out.println("Gasite " + readList.size() + " intrari cu pt aces senosr");
            List<SensorReadDto> readDtoList = new ArrayList<>();


            byte[] data = null;
            try {
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                HessianOutput output = new HessianOutput(os);

                for(SensorRead s: readList) {
                    readDtoList.add(new SensorReadDto(s.getId(),s.getSensorId(),s.getTimestamp(),s.getMeasurement()));
//
                }
                output.writeObject(readDtoList);
                data = os.toByteArray();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return data;

        }
        return null;


    }
}
