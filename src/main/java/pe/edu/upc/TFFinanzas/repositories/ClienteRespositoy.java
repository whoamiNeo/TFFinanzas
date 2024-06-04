package pe.edu.upc.TFFinanzas.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.edu.upc.TFFinanzas.dtos.ClienteMorososDTO;
import pe.edu.upc.TFFinanzas.entities.Cliente;

@Repository
public interface ClienteRespositoy extends JpaRepository<Cliente, Long>{
    
    ///ACTUALIZAR CLIENTES 
    ///LISTAR TOODS LOS CLIENTES REGISTRADOS-> FindAll ya esta por defecto 
    ///BUSCAR POR NOMBRE DEL CLIENTE 
    List<Cliente> findByNombre(String nombre);


    // Clientes moroso lista
//    @Query(value = "SELECT c.* FROM Cliente c " +
//            "JOIN Credito cr ON c.id_cliente = cr.id_cliente " +
//            "WHERE cr.estado = false", nativeQuery = true)
//    List<Cliente> findClientesMorosos();

//    @Query(value = "SELECT new pe.edu.upc.TFFinanzas.dtos.ClienteMorososDTO(c.idCliente, c.nombre, c.apellido, cr.estado) " +
//            "FROM Cliente c " +
//            "JOIN Credito cr ON c.idCliente = cr.cliente.idCliente " +
//            "WHERE cr.estado = false")
//    List<ClienteMorososDTO> findClientesMorosos();

//    @Query(value = "SELECT c.id_cliente, c.nombre, c.apellido, cr.estado, cr.monto, cr.fecha_inicio, cr.fecha_fin " +
//            "FROM Cliente c " +
//            "JOIN Credito cr ON c.id_cliente = cr.id_cliente " +
//            "WHERE cr.estado = false", nativeQuery = true)
//    List<Object[]> findClientesConCreditoInactivo();


    @Query(value = "SELECT c.id_cliente, c.nombre, c.apellido, cr.estado, cr.monto, cr.fecha_inicio, cr.fecha_fin, cr.tipo_credito " +
            "FROM Cliente c " +
            "JOIN Credito cr ON c.id_cliente = cr.id_cliente " +
            "WHERE cr.estado = false", nativeQuery = true)
    List<Object[]> findClientesConCreditoInactivo();

}




