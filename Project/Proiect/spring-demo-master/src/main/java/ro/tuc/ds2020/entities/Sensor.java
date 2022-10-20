package ro.tuc.ds2020.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
public class Sensor {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-binary")
    private UUID id;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Double maxValue;

    @JsonManagedReference(value = "sensor")
    @OneToOne(mappedBy = "sensor", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Device device;

}
