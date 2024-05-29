package pe.edu.upc.TFFinanzas.entities;
import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "pago")
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPago;

    private Float monto;
    private LocalDate fechaPago;
    
    //enumeracion de tipo de pago
    @Enumerated(EnumType.STRING)
    private TipoPagoEnum tipoPago;
    

    //*relacion pago-credito
    @ManyToOne
    @JoinColumn(name = "idCredito", nullable = false)
    private Credito credito;
}
