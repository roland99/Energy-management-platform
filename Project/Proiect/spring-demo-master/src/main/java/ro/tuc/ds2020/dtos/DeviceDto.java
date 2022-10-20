package ro.tuc.ds2020.dtos;

import lombok.*;
import net.bytebuddy.build.ToStringPlugin;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.hateoas.RepresentationModel;
import ro.tuc.ds2020.entities.Client;
import ro.tuc.ds2020.entities.Sensor;

import javax.persistence.*;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DeviceDto extends RepresentationModel<ClientDto> {
    private UUID id;
    private String description;
    private String address;
    private Double maxConsumption;
    private Double averageConsumption;
    private Client client;
    private Sensor sensor;
}
