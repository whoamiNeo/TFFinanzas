package pe.edu.upc.TFFinanzas.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.edu.upc.TFFinanzas.entities.DetalleVenta;

@Repository
public interface DetalleVentaRespository  extends JpaRepository<DetalleVenta, Long>{
    
}
