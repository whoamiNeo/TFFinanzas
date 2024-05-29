package pe.edu.upc.TFFinanzas.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.edu.upc.TFFinanzas.entities.Credito;

import java.util.List;

@Repository
public interface CreditoRepository extends JpaRepository<Credito, Long>{

    @Query("from credito crd where crd.idCredito = :idCredito")
    List<Credito> buscarCredito(@Param("idCredito") Long idCredito);
} 
