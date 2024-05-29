package pe.edu.upc.TFFinanzas.dtos;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.edu.upc.TFFinanzas.entities.TipoCreditoEnum;
import pe.edu.upc.TFFinanzas.entities.TipoInteresEnum;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreditoTDO {
    private Long idCredito;
    private Float monto;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Boolean estado;
    private Integer cuotas;
    private TipoInteresEnum tipoInteres;
    private TipoCreditoEnum tipoCredito;
    private Long idCliente;
}
