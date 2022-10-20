package ro.tuc.ds2020.dtos;


import lombok.*;

import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DeviceClientLinkDto {
    private UUID clientId;
    private UUID deviceId;
}
