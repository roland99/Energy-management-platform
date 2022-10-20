package ro.tuc.ds2020.dtos;

import lombok.*;

import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DeviceSensorLinkDto {
    private UUID deviceId;
    private UUID sensorId;
}
