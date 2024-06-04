package pe.edu.upc.TFFinanzas.dtos;


//15.	Poner un limite de crédito que tiene una persona
//16.	Poder agregar a un cliente y poder designarle el crédito que desea tener
//17.	Poder ver una lista de  clientes morosos y aplicarle de manera automática la mora correspondiente

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteMorososDTO {
    private Long idCliente;
    private String nombre;
    private String apellido;
    private Boolean estadoCredito;

    // INTERES MOROSO
    private Float mora;
    private String tipoCredito;
}
