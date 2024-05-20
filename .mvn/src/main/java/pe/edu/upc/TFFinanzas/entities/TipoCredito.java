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
@Table(name = "tipoCredito")
public class TipoCredito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTipoCredito;

    private String tipoCredito;
    private String descripcion;

    //relacion tipoCredito-credito
    @OneToMany(mappedBy = "tipoCredito", fetch = FetchType.LAZY)
    private List<Credito> creditos;
}
