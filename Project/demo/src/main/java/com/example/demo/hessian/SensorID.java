package com.example.demo.hessian;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.UUID;

//@Setter
//@Getter
//@Service
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
@Component
public class SensorID {

    @Autowired
    Environment environment;

    @Value("${sensorId}")
    public UUID myVariable;


    private UUID sensorId;

    public UUID getSensorIdFromEnv(){
        return myVariable;
    }
}
