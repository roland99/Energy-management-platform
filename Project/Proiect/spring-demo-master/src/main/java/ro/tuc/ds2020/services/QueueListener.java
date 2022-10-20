package ro.tuc.ds2020.services;

import com.google.gson.Gson;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.dtos.SensorReadDto;
import ro.tuc.ds2020.dtos.builders.SensorBuilder;
import ro.tuc.ds2020.entities.Sensor;
import ro.tuc.ds2020.entities.SensorRead;
import ro.tuc.ds2020.hessian.Message;
import ro.tuc.ds2020.repositories.SensorReadRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@EnableRabbit
@Service
public class QueueListener {

    @Autowired
    SensorService sensorService;

    @Autowired
    SensorReadRepository sensorReadRepository;


    @RabbitListener(queues = "SD")
    public void receive(@Payload String message) {
        //System.out.println(message);
        SensorRead read = new Gson().fromJson(message, SensorRead.class);
        System.out.println("Mesaj: " + read);

        //get the sensor of the measurement
        List<SensorRead> allReads = sensorReadRepository.findAllBySensorIdOrderByTimestampDesc(read.getSensorId());
        double average = 0L;
        double peak = 0L;
        if (allReads.size() > 0) {
            peak = (read.getMeasurement() - allReads.get(0).getMeasurement()) / ((double) (read.getTimestamp() - allReads.get(0).getTimestamp()) / 100000); // 1000 for normal user, sensible for value greater than 1
            for (SensorRead r : allReads) {
                if (r.getMeasurement() > 0) {
                    average = average + r.getMeasurement();
                }
            }
            average = (average + read.getMeasurement()) / allReads.size();

        }

        sensorService.addMeasurement(read, peak, average);
        sensorReadRepository.save(read);
    }
}