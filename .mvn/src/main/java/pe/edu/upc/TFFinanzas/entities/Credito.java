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
    private  boolean estado;
    private  Integer cuotas;

    //RELACION credito-cliente
    @ManyToOne
    @JoinColumn(name = "idCliente", nullable = false)
    private Cliente cliente;

    //RELACION credito-tipoCredito
    @ManyToOne
    @JoinColumn(name = "idTipoCredito", nullable = false)
    private TipoCredito tipoCredito;

    //RELACION credito-tipoInteres
    @ManyToOne
    @JoinColumn(name = "idTipoInteres", nullable = false)
    private TipoTasaInteres tipoTasaInteres;

    //RELACION credito-ventaProducto
    @OneToMany(mappedBy = "credito", fetch = FetchType.LAZY)
    private List<Pago> pagos;
}
