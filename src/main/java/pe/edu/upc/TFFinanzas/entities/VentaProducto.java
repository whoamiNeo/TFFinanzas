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
@Table(name = "ventaProducto")
public class VentaProducto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVentaProducto;

    private Integer totalVenta;
    private LocalDate fechaVenta;
    
    //relacion ventaProducto-cliente
    @ManyToOne
    @JoinColumn(name = "idCliente", nullable = false)
    private Cliente cliente;

    //relacion ventaProducto-usuario
    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    private UserEntity users;

    //relacion ventaProducto-detalleVenta
    @OneToMany(mappedBy = "ventaProducto", fetch = FetchType.LAZY)
    private List<DetalleVenta> detalleVentas;
}
