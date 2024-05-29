package pe.edu.upc.TFFinanzas.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.upc.TFFinanzas.dtos.PagoDTO;
import pe.edu.upc.TFFinanzas.dtos.auth.ResponseDTO;
import pe.edu.upc.TFFinanzas.entities.Credito;
import pe.edu.upc.TFFinanzas.entities.Pago;
import pe.edu.upc.TFFinanzas.repositories.CreditoRepository;
import pe.edu.upc.TFFinanzas.repositories.PagoRespository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class PagoService {
    private final PagoRespository pagoRepository;
    private final CreditoRepository creditoRepository;

    // Registrar Pago
    public ResponseDTO registrarPago(PagoDTO pagoDTO) {
        Optional<Credito> creditoOpt = creditoRepository.findById(pagoDTO.getIdCredito());
        if (!creditoOpt.isPresent()) {
            return new ResponseDTO("Crédito no encontrado");
        }
        Credito credito = creditoOpt.get();

        Pago pago = new Pago();
        pago.setMonto(pagoDTO.getMonto());
        pago.setFechaPago(pagoDTO.getFechaPago());
        pago.setTipoPago(pagoDTO.getTipoPago());
        pago.setCredito(credito);

        pagoRepository.save(pago);
        return new ResponseDTO("Pago registrado correctamente");
    }

    // Actualizar Pago
    public ResponseDTO actualizarPago(Long idPago, PagoDTO pagoDTO) {
        Optional<Pago> pagoOpt = pagoRepository.findById(idPago);
        if (pagoOpt.isPresent()) {
            Pago pago = pagoOpt.get();
            pago.setMonto(pagoDTO.getMonto());
            pago.setFechaPago(pagoDTO.getFechaPago());
            pago.setTipoPago(pagoDTO.getTipoPago());
            pagoRepository.save(pago);
            return new ResponseDTO("Pago actualizado correctamente");
        }
        return new ResponseDTO("Pago no encontrado");
    }

    // Listar Todos los Pagos
    public List<PagoDTO> listarPagos() {
        List<Pago> pagos = pagoRepository.findAll();
        return pagos.stream()
        .map(pago -> new PagoDTO(
            pago.getIdPago(),
            pago.getMonto(),
            pago.getFechaPago(),
            pago.getTipoPago(),
            pago.getCredito().getIdCredito()))
        .collect(Collectors.toList());
    }

    // Eliminar Pago
    public ResponseDTO eliminarPago(Long idPago) {
        Optional<Pago> pagoOpt = pagoRepository.findById(idPago);
        if (pagoOpt.isPresent()) {
            pagoRepository.delete(pagoOpt.get());
            return new ResponseDTO("Pago eliminado correctamente");
        }
        return new ResponseDTO("Pago no encontrado");
    }

    //BUSCAR PAGO POR ID
    public List<Pago> search(Long idPago) {
        return pagoRepository.buscarPago(idPago);
    }
    
}
