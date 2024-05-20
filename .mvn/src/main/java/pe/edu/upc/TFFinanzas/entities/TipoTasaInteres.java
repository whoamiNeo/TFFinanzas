package pe.edu.upc.TFFinanzas.entities;

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
@Table(name = "tipoTasaInteres")
public class TipoTasaInteres {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTipoTasaInteres;

    private String tipoTasaInteres;
    private String descripcion;

    //relacion tipoTasaInteres-Credito
    @OneToMany(mappedBy = "tipoTasaInteres", fetch = FetchType.LAZY)
    private List<Credito> creditos;
}
