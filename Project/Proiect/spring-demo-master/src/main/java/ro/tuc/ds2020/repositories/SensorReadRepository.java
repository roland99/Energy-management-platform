package ro.tuc.ds2020.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.tuc.ds2020.entities.SensorRead;

import java.util.List;
import java.util.UUID;

public interface SensorReadRepository extends JpaRepository<SensorRead, UUID> {

    List<SensorRead> findAllBySensorIdOrderByTimestampDesc(UUID sensorId);
    List<SensorRead> findAllBySensorIdOrderByTimestampAsc(UUID sensorId);

}
