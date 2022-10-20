package com.example.demo.hessian;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SensorReadDto implements Serializable {
    private static final long serialversionUID =
            129348938L;
    private UUID id;
    private UUID sensorId;
    private long timestamp;
    private Double measurement;

}