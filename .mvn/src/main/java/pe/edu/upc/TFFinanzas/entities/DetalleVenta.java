package pe.edu.upc.TFFinanzas.entities;

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
@Table(name = "detalleVenta")
public class DetalleVenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetalleVenta;

    private Integer cantidad;
    private Float precio;

    //Relacion detalleVenta - producto
    @ManyToOne
    @JoinColumn(name = "idProducto", nullable = false)
    private Producto producto;

    //relacion detalleVenta - ventaProducto
    @ManyToOne
    @JoinColumn(name = "idVenta", nullable = false)
    private VentaProducto ventaProducto;
}
