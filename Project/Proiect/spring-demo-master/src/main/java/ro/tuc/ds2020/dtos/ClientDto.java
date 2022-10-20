package ro.tuc.ds2020.dtos;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import ro.tuc.ds2020.entities.Client;
import ro.tuc.ds2020.entities.Device;

import javax.persistence.Column;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ClientDto extends RepresentationModel<ClientDto> {
    private UUID id;
    private String email;
    private String password;
    private String name;
    private Date birthDate;
    private String address;
    private Boolean isAdmin;
    private List<Device> deviceList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientDto clientDTO = (ClientDto) o;
        return email == clientDTO.email &&
                Objects.equals(name, clientDTO.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email);
    }
}
