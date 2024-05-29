package pe.edu.upc.TFFinanzas.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.upc.TFFinanzas.dtos.DetalleVentaDTO;
import pe.edu.upc.TFFinanzas.entities.DetalleVenta;
import pe.edu.upc.TFFinanzas.entities.Producto;
import pe.edu.upc.TFFinanzas.entities.Cliente;
import pe.edu.upc.TFFinanzas.repositories.DetalleVentaRespository;
import pe.edu.upc.TFFinanzas.repositories.ProductoRespository;
import pe.edu.upc.TFFinanzas.repositories.ClienteRespositoy;
import pe.edu.upc.TFFinanzas.dtos.auth.ResponseDTO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DetalleVentaService {
    private final DetalleVentaRespository detalleVentaRepository;
    private final ProductoRespository productoRepository;
    private final ClienteRespositoy clienteRepository;

    // REGISTRAR DETALLE VENTA
    public ResponseDTO registrarDetalleVenta(DetalleVentaDTO detalleVentaDTO) {
        Optional<Producto> productoOpt = productoRepository.findById(detalleVentaDTO.getIdProducto());
        if (!productoOpt.isPresent()) {
            return new ResponseDTO("Producto no encontrado");
        }

        Optional<Cliente> clienteOpt = clienteRepository.findById(detalleVentaDTO.getIdCliente());
        if (!clienteOpt.isPresent()) {
            return new ResponseDTO("Cliente no encontrado");
        }

        Producto producto = productoOpt.get();
        Cliente cliente = clienteOpt.get();
        
        DetalleVenta detalleVenta = new DetalleVenta();
        detalleVenta.setCantidad(detalleVentaDTO.getCantidad());
        detalleVenta.setPrecio(detalleVentaDTO.getPrecio());
        detalleVenta.setFechaVenta(detalleVentaDTO.getFechaVenta());
        detalleVenta.setProducto(producto);
        detalleVenta.setCliente(cliente);

        detalleVentaRepository.save(detalleVenta);
        return new ResponseDTO("Detalle de venta registrado correctamente");
    }

    // ACTUALIZAR DETALLE VENTA
    public ResponseDTO actualizarDetalleVenta(Long idDetalleVenta, DetalleVentaDTO detalleVentaDTO) {
        Optional<DetalleVenta> detalleVentaOpt = detalleVentaRepository.findById(idDetalleVenta);
        if (!detalleVentaOpt.isPresent()) {
            return new ResponseDTO("Detalle de venta no encontrado");
        }

        Optional<Producto> productoOpt = productoRepository.findById(detalleVentaDTO.getIdProducto());
        if (!productoOpt.isPresent()) {
            return new ResponseDTO("Producto no encontrado");
        }

        Optional<Cliente> clienteOpt = clienteRepository.findById(detalleVentaDTO.getIdCliente());
        if (!clienteOpt.isPresent()) {
            return new ResponseDTO("Cliente no encontrado");
        }

        DetalleVenta detalleVenta = detalleVentaOpt.get();
        Producto producto = productoOpt.get();
        Cliente cliente = clienteOpt.get();
        
        detalleVenta.setCantidad(detalleVentaDTO.getCantidad());
        detalleVenta.setPrecio(detalleVentaDTO.getPrecio());
        detalleVenta.setFechaVenta(detalleVentaDTO.getFechaVenta());
        detalleVenta.setProducto(producto);
        detalleVenta.setCliente(cliente);

        detalleVentaRepository.save(detalleVenta);
        return new ResponseDTO("Detalle de venta actualizado correctamente");
    }

    // LISTAR TODOS LOS DETALLES DE VENTA
    public List<DetalleVentaDTO> listarTodosLosDetallesVenta() {
        List<DetalleVenta> detallesVenta = detalleVentaRepository.findAll();
        return detallesVenta.stream()
                .map(detalleVenta -> new DetalleVentaDTO(
                        detalleVenta.getIdDetalleVenta(),
                        detalleVenta.getCantidad(),
                        detalleVenta.getPrecio(),
                        detalleVenta.getFechaVenta(),
                        detalleVenta.getProducto().getIdProducto(),
                        detalleVenta.getCliente().getIdCliente()))
                .collect(Collectors.toList());
    }

    // ELIMINAR DETALLE VENTA
    public ResponseDTO eliminarDetalleVenta(Long idDetalleVenta) {
        if (detalleVentaRepository.existsById(idDetalleVenta)) {
            detalleVentaRepository.deleteById(idDetalleVenta);
            return new ResponseDTO("Detalle de venta eliminado correctamente");
        }
        return new ResponseDTO("Detalle de venta no encontrado");
    }
    // ?CONSULTAS PARA DETALLE DE VENTA
    //! BUSCAR DETALLE VENTA POR FECHA y ver quien compro y que productos compró
    
}
