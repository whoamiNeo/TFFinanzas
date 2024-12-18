package pe.edu.upc.TFFinanzas.dtos;

import lombok.Data;
import pe.edu.upc.TFFinanzas.entities.RoleEnum;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private RoleEnum role;
}
