package pe.edu.upc.TFFinanzas.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.upc.TFFinanzas.dtos.DetalleCreditoDTO;
import pe.edu.upc.TFFinanzas.entities.DetalleCredito;
import pe.edu.upc.TFFinanzas.repositories.DetalleCreditoRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DetalleCreditoService {
    private final DetalleCreditoRepository detalleCreditoRepository;

    public List<DetalleCreditoDTO> listarDetallesPorCredito(Long idCredito) {
        List<DetalleCredito> detalles = detalleCreditoRepository.findByCreditoIdCredito(idCredito);
        return detalles.stream()
                .map(detalle -> new DetalleCreditoDTO(
                        detalle.getIdDetalleCredito(),
                        detalle.getSaldoInicial(),
                        detalle.getInteres(),
                        detalle.getRenta(),
                        detalle.getAmortizacion(),
                        detalle.getSaldoFinal(),
                        detalle.getFechaPagoCuota(),
                        detalle.isEstadoPago(),
                        detalle.getMora(),
                        detalle.getCredito().getIdCredito()))
                .collect(Collectors.toList());
    }

    public List<DetalleCreditoDTO> listarTodos() {
        List<DetalleCredito> detalles = detalleCreditoRepository.findAll();
        return detalles.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private DetalleCreditoDTO convertToDTO(DetalleCredito detalleCredito) {
        return new DetalleCreditoDTO(
                detalleCredito.getIdDetalleCredito(),
                detalleCredito.getSaldoInicial(),
                detalleCredito.getInteres(),
                detalleCredito.getRenta(),
                detalleCredito.getAmortizacion(),
                detalleCredito.getSaldoFinal(),
                detalleCredito.getFechaPagoCuota(),
                detalleCredito.isEstadoPago(),
                detalleCredito.getMora(),
                detalleCredito.getCredito().getIdCredito()
        );
    }
}
