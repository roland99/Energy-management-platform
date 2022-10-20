package ro.tuc.ds2020.dtos;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import ro.tuc.ds2020.entities.Device;

import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SensorDto extends RepresentationModel<SensorDto> {
    private UUID id;
    private String description;
    private Double maxValue;
    private Device device;
}
