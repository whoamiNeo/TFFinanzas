package pe.edu.upc.TFFinanzas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.edu.upc.TFFinanzas.entities.TipoCredito;

@Repository
public interface TipoCreditoRepository extends JpaRepository<TipoCredito, Long>{
    
}
