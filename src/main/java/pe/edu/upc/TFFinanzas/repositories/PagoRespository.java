package pe.edu.upc.TFFinanzas.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.edu.upc.TFFinanzas.entities.Pago;

import java.util.List;

@Repository
public interface PagoRespository extends JpaRepository<Pago, Long>{

    //BUSCAR PAGO POR ID
    @Query("from pago pgo where pgo.idPago = :idPago")
    List<Pago> buscarPago(@Param("idPago") Long idPago);
    
}
