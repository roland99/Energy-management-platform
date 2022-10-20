package ro.tuc.ds2020.dtos;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WebSocketMessageDto {
    private String message;
}
