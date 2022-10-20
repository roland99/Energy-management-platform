package ro.tuc.ds2020.dtos;

import lombok.*;

import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginDto {
    private UUID idUser;
    private String email;
    private String password;
    private Boolean isAdmin;
    private Boolean loginSuccess;
}
