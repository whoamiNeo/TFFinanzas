package pe.edu.upc.TFFinanzas.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.upc.TFFinanzas.dtos.CreditoTDO;
import pe.edu.upc.TFFinanzas.dtos.auth.ResponseDTO;
import pe.edu.upc.TFFinanzas.entities.Credito;
import pe.edu.upc.TFFinanzas.entities.Cliente;
import pe.edu.upc.TFFinanzas.repositories.CreditoRepository;
import pe.edu.upc.TFFinanzas.repositories.ClienteRespositoy;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



@Service
@RequiredArgsConstructor
public class CreditoService {
    private final CreditoRepository creditoRepository;
    private final ClienteRespositoy clienteRepository;

    //! REGISTRAR CREDITO
    public ResponseDTO registrarCredito(CreditoTDO creditoDTO) {
        Optional<Cliente> clienteOpt = clienteRepository.findById(creditoDTO.getIdCliente());
        if (!clienteOpt.isPresent()) {
            return new ResponseDTO("Cliente no encontrado");
        }
        Cliente cliente = clienteOpt.get();

        Credito credito = new Credito();
        credito.setMonto(creditoDTO.getMonto());
        credito.setFechaInicio(creditoDTO.getFechaInicio());
        credito.setFechaFin(creditoDTO.getFechaFin());
        credito.setEstado(creditoDTO.getEstado());
        credito.setCuotas(creditoDTO.getCuotas());
        credito.setTipoInteres(creditoDTO.getTipoInteres());
        credito.setTipoCredito(creditoDTO.getTipoCredito());
        credito.setCliente(cliente);

        creditoRepository.save(credito);
        return new ResponseDTO("Crédito registrado correctamente");
    }
      //! ACTUALIZAR CREDITO
    public ResponseDTO actualizarCredito(Long idCredito, CreditoTDO creditoDTO) {
        Optional<Credito> creditoOpt = creditoRepository.findById(idCredito);
        if (creditoOpt.isPresent()) {
            Credito credito = creditoOpt.get();
            credito.setMonto(creditoDTO.getMonto());
            credito.setFechaInicio(creditoDTO.getFechaInicio());
            credito.setFechaFin(creditoDTO.getFechaFin());
            credito.setEstado(creditoDTO.getEstado());
            credito.setCuotas(creditoDTO.getCuotas());
            credito.setTipoInteres(creditoDTO.getTipoInteres());
            credito.setTipoCredito(creditoDTO.getTipoCredito());
            creditoRepository.save(credito);
            return new ResponseDTO("Crédito actualizado correctamente");
        }
        return new ResponseDTO("Crédito no encontrado");
    }
    //*aqui agregar una lista de las personas que tienen creditos pendientes o cosas asi 
       // LISTAR TODOS LOS CREDITOS
    public List<CreditoTDO> listarCreditos() {
        List<Credito> creditos = creditoRepository.findAll();
        return creditos.stream()
                .map(credito -> new CreditoTDO(
                        credito.getIdCredito(),
                        credito.getMonto(),
                        credito.getFechaInicio(),
                        credito.getFechaFin(),
                        credito.getEstado(),
                        credito.getCuotas(),
                        credito.getTipoInteres(),
                        credito.getTipoCredito(),
                        credito.getCliente().getIdCliente()))
                .collect(Collectors.toList());
    }

    // ELIMINAR CREDITO
    public ResponseDTO eliminarCredito(Long idCredito) {
        Optional<Credito> creditoOpt = creditoRepository.findById(idCredito);
        if (creditoOpt.isPresent()) {
            creditoRepository.delete(creditoOpt.get());
            return new ResponseDTO("Crédito eliminado correctamente");
        }
        return new ResponseDTO("Crédito no encontrado");
    }

    
}
