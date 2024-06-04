package pe.edu.upc.TFFinanzas.services;

import lombok.RequiredArgsConstructor;
import pe.edu.upc.TFFinanzas.dtos.ClienteDTO;
import pe.edu.upc.TFFinanzas.dtos.ClienteMorososDTO;
import pe.edu.upc.TFFinanzas.dtos.auth.ResponseDTO;
import pe.edu.upc.TFFinanzas.entities.Cliente;
import pe.edu.upc.TFFinanzas.entities.Credito;
import pe.edu.upc.TFFinanzas.entities.UserEntity;
import pe.edu.upc.TFFinanzas.repositories.ClienteRespositoy;
import pe.edu.upc.TFFinanzas.repositories.UserRepository;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRespositoy clienteRespositoy;
    private final UserRepository userRepository;

    private static final float INTERES_SIMPLE = 0.011f;




    /// REGISTRAR CLIENTE
    public ResponseDTO registrarCliente(ClienteDTO clienteDTO) {
        Optional<UserEntity> userExistente = userRepository.findById(clienteDTO.getIdUsuario());
        if(!userRepository.findById(clienteDTO.getIdUsuario()).isPresent()){
            return new ResponseDTO("Usuario no encontrado");
        }
        UserEntity objtUser=userExistente.get();
        //registrar cliente
        Cliente objEntity = new Cliente();
        objEntity.setNombre(clienteDTO.getNombre());
        objEntity.setApellido(clienteDTO.getApellido());
        objEntity.setCorreo(clienteDTO.getCorreo());
        objEntity.setNumeroDocumento(clienteDTO.getNumeroDocumento());
        objEntity.setTelefono(clienteDTO.getTelefono());
        objEntity.setLimiteCredito(clienteDTO.getLimiteCredito());
        objEntity.setDireccion(clienteDTO.getDireccion());
        objEntity.setUsers(objtUser);
        clienteRespositoy.save(objEntity);
        return new ResponseDTO("Cliente registrado correctamente");        
    }
    /// ACTUALIZAR CLIENTE
    public ResponseDTO actualizarCliente(Long idCliente, ClienteDTO clienteDTO){
        //verificar si existe el cliente
        Optional<Cliente> clienteExistente = clienteRespositoy.findById(idCliente);
        if(!clienteExistente.isPresent()){
            return new ResponseDTO("Cliente no encontrado");
        }
        //verificar si existe el usuario
        Optional<UserEntity> userExistente = userRepository.findById(clienteDTO.getIdUsuario());
        if(!userRepository.findById(clienteDTO.getIdUsuario()).isPresent()){
            return new ResponseDTO("Usuario no encontrado");
        }
        //obtener cliente y usuario
        UserEntity objtUser=userExistente.get();
        Cliente objEntity = clienteExistente.get();
        //actualizar cliente
        objEntity.setNombre(clienteDTO.getNombre());
        objEntity.setApellido(clienteDTO.getApellido());
        objEntity.setCorreo(clienteDTO.getCorreo());
        objEntity.setNumeroDocumento(clienteDTO.getNumeroDocumento());
        objEntity.setTelefono(clienteDTO.getTelefono());
        objEntity.setLimiteCredito(clienteDTO.getLimiteCredito());
        objEntity.setDireccion(clienteDTO.getDireccion());
        objEntity.setUsers(objtUser);
        clienteRespositoy.save(objEntity);
        return new ResponseDTO("Cliente actualizado correctamente");
    }

    /// LISTAR TODOS LOS CLIENTES
    public List<ClienteDTO> listarTodosLosClientes() {
        List<Cliente> clientes = clienteRespositoy.findAll();
        return clientes.stream()
                .map(cliente -> new ClienteDTO(
                        cliente.getNombre(),
                        cliente.getApellido(),
                        cliente.getCorreo(),
                        cliente.getNumeroDocumento(),
                        cliente.getTelefono(),
                        cliente.getLimiteCredito(),
                        cliente.getDireccion(),
                        cliente.getUsers().getId()))
                        
                .collect(Collectors.toList());
    }



    // Clientes Morosos

//    public List<ClienteMorososDTO> obtenerClientesConCreditosInactivos() {
//        return clienteRespositoy.findClientesMorosos();
//    }


//    public List<ClienteMorososDTO> obtenerClientesConCreditosInactivos() {
//        List<Object[]> resultados = clienteRespositoy.ObtenerClientesMorosos();
//
//        return resultados.stream().map(obj -> {
//            Long idCliente = ((Number) obj[0]).longValue();
//            String nombre = (String) obj[1];
//            String apellido = (String) obj[2];
//            Boolean estadoCredito = (Boolean) obj[3];
//            Float monto = ((Number) obj[4]).floatValue();
//            LocalDate fechaFin = ((java.sql.Date) obj[5]).toLocalDate();
//
//            long diasEntreInicioFin = Duration.between(fechaFin.atStartOfDay(), LocalDate.now().atStartOfDay()).toDays();
//            Float interesInicioFin = monto * 0.001f * diasEntreInicioFin;
//            Float montoIncioFin = monto + interesInicioFin;
//
//            long diasEntre = Duration.between(fechaFin.atStartOfDay(), LocalDate.now().atStartOfDay()).toDays();
//            Float interes = montoIncioFin * 0.001f * diasEntre;
//            Float mora = montoIncioFin + interes;
//
////            long diasEntre = Duration.between(fechaFin.atStartOfDay(), LocalDate.now().atStartOfDay()).toDays();
////            Float interes = monto * 0.001f * diasEntre;
////            Float mora = monto + interes;
//
//            return new ClienteMorososDTO(idCliente, nombre, apellido, estadoCredito, mora);
//        }).collect(Collectors.toList());
//    }

    public List<ClienteMorososDTO> ListarClientesMorosos() {
        List<Object[]> resultados = clienteRespositoy.findClientesConCreditoInactivo();

        return resultados.stream().map(obj -> {
            Long idCliente = ((Number) obj[0]).longValue();
            String nombre = (String) obj[1];
            String apellido = (String) obj[2];
            Boolean estadoCredito = (Boolean) obj[3];
            Float monto = ((Number) obj[4]).floatValue();
            LocalDate fechaInicio = ((java.sql.Date) obj[5]).toLocalDate();
            LocalDate fechaFin = ((java.sql.Date) obj[6]).toLocalDate();
            String tipoCredito = (String) obj[7];

            // Cálculo de los intereses entre fechaInicio y fechaFin
            long diasEntreInicioYFin = Duration.between(fechaInicio.atStartOfDay(), fechaFin.atStartOfDay()).toDays();
            Float interesesInicioFin;

            switch (tipoCredito) {
                case "SIMPLE":
                    interesesInicioFin = monto * 0.001f * diasEntreInicioYFin;
                    break;
                case "NOMINAL":
                    interesesInicioFin = monto * 0.002f * diasEntreInicioYFin;
                    break;
                case "EFECTIVA":
                    interesesInicioFin = monto * 0.003f * diasEntreInicioYFin;
                    break;
                case "ANUALIDAD_SIMPLE":
                    interesesInicioFin = monto * 0.004f * diasEntreInicioYFin;
                    break;
                default:
                    interesesInicioFin = 0f;
            }

            Float montoTotal = monto + interesesInicioFin;

            // Cálculo de los intereses entre fechaFin y la fecha actual
            long diasEntreFinYActual = Duration.between(fechaFin.atStartOfDay(), LocalDate.now().atStartOfDay()).toDays();
            Float interesesFinActual = montoTotal * 0.001f * diasEntreFinYActual;

            Float mora = montoTotal + interesesFinActual;

            return new ClienteMorososDTO(idCliente, nombre, apellido, estadoCredito, mora, tipoCredito);
        }).collect(Collectors.toList());
    }

    /// BUSCAR CLIENTE POR NOMBRE
    public List<ClienteDTO> buscarClientePorNombre(String nombre) {
        List<Cliente> clientes = clienteRespositoy.findByNombre(nombre);
        return clientes.stream()
                .map(cliente -> new ClienteDTO(
                        cliente.getNombre(),
                        cliente.getApellido(),
                        cliente.getCorreo(),
                        cliente.getNumeroDocumento(),
                        cliente.getTelefono(),
                        cliente.getLimiteCredito(),
                        cliente.getDireccion(),
                        cliente.getUsers().getId()))
                .collect(Collectors.toList());
    }
    ///ELIMINAR CLIENTE
    public ResponseDTO eliminarCliente(Long idCliente) {
        if(clienteRespositoy.existsById(idCliente)){
            clienteRespositoy.deleteById(idCliente);
            return new ResponseDTO("Cliente eliminado correctamente");
        }
        return new ResponseDTO("Cliente no encontrado");
    }
}