package ro.tuc.ds2020.hessian;


import ro.tuc.ds2020.dtos.SensorReadDto;

import java.util.List;
import java.util.UUID;

public interface Message {
    byte[] transferWithHessian(UUID id);
}
