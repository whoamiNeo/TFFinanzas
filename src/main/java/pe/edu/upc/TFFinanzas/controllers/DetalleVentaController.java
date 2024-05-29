package pe.edu.upc.TFFinanzas.controllers;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pe.edu.upc.TFFinanzas.dtos.DetalleVentaDTO;
import pe.edu.upc.TFFinanzas.services.DetalleVentaService;
import pe.edu.upc.TFFinanzas.dtos.auth.ResponseDTO;

import java.util.List;

@RestController
@RequestMapping("/detalleVenta")
@RequiredArgsConstructor
public class DetalleVentaController {
    private final DetalleVentaService detalleVentaService;

    //REGISTRAR DETALLE VENTA
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/registrar")
    public ResponseEntity<?> registrarDetalleVenta(@RequestBody DetalleVentaDTO detalleVentaDTO) {
        try {
            return new ResponseEntity<>(detalleVentaService.registrarDetalleVenta(detalleVentaDTO), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO("Error en el registro"), HttpStatus.BAD_REQUEST);
        }
    }
    //ACTUALIZAR DETALLE VENTA
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarDetalleVenta(@PathVariable Long id, @RequestBody DetalleVentaDTO detalleVentaDTO) {
        try {
            return new ResponseEntity<>(detalleVentaService.actualizarDetalleVenta(id, detalleVentaDTO), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO("Error al actualizar Detalle de Venta"), HttpStatus.NOT_FOUND);
        }
    }
    //LISTAR TODAS LAS VENTAS
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/listar")
    public ResponseEntity<List<DetalleVentaDTO>> listarTodosLosDetallesVenta() {
        List<DetalleVentaDTO> detallesVenta = detalleVentaService.listarTodosLosDetallesVenta();
        return new ResponseEntity<>(detallesVenta, HttpStatus.OK);
    }
    //!VERIFICAR SI ESTO ENTRA O NO COMO CONSULTA
    //ELIMINAR DETALLE VENTA
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<ResponseDTO> eliminarDetalleVenta(@PathVariable Long id) {
        ResponseDTO response = detalleVentaService.eliminarDetalleVenta(id);
        if (response.getMessage().equals("Detalle de venta no encontrado")) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
