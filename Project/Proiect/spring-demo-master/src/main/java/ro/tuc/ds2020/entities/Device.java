package ro.tuc.ds2020.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Device {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-binary")
    private UUID id;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private Double maxConsumption;

    @Column(nullable = false)
    private Double averageConsumption;

    @JsonBackReference(value = "client")
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @JsonBackReference(value = "sensor")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sensor_id")
    private Sensor sensor;

}
