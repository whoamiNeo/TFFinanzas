package pe.edu.upc.TFFinanzas.entities;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "credito")
public class Credito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCredito;

    private Float monto;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private  Boolean estado;
    private  Integer cuotas;
    
    //enumeraciones 
    @Enumerated(EnumType.STRING)
    private TipoInteresEnum tipoInteres;
    @Enumerated(EnumType.STRING)
    private TipoCreditoEnum TipoCredito;

    //*RELACION credito-cliente
    @ManyToOne
    @JoinColumn(name = "idCliente", nullable = false)
    private Cliente cliente;

    //*RELACION credito-pago
    @OneToMany(mappedBy = "credito", fetch = FetchType.LAZY)
    private List<Pago> pagos;
}

