package pe.edu.upc.TFFinanzas.services;

import lombok.RequiredArgsConstructor;
import pe.edu.upc.TFFinanzas.dtos.ClienteDTO;
import pe.edu.upc.TFFinanzas.dtos.auth.ResponseDTO;
import pe.edu.upc.TFFinanzas.entities.Cliente;
import pe.edu.upc.TFFinanzas.entities.UserEntity;
import pe.edu.upc.TFFinanzas.repositories.ClienteRespositoy;
import pe.edu.upc.TFFinanzas.repositories.UserRepository;

import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRespositoy clienteRespositoy;
    private final UserRepository userRepository;

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