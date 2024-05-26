package pe.edu.upc.TFFinanzas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.edu.upc.TFFinanzas.entities.TipoTasaInteres;

@Repository
public interface TipoTasaInteresRepository extends JpaRepository<TipoTasaInteres, Long>{

    
} 