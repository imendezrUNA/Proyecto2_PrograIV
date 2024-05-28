package eif209.facturacioncsr.presentation;

import eif209.facturacioncsr.logic.Proveedor;
import eif209.facturacioncsr.logic.Service;
import eif209.facturacioncsr.logic.Usuario;
import eif209.facturacioncsr.logic.dto.ProveedorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
public class Proveedores {

    private final Service service;

    @Autowired
    public Proveedores(Service service) {
        this.service = service;
    }

    @GetMapping("/listar")
    public List<ProveedorDTO> listarProveedores(@RequestParam(name = "ordenarPor", required = false) String ordenarPor) {
        // Usa el nuevo método getProveedoresDTO que devuelve DTOs
        return service.getProveedoresDTO();
    }

    @PostMapping("/activar/{id}")
    public ResponseEntity<?> activarProveedor(@PathVariable Long id) {
        if (service.cambiarEstadoProveedor(id, Usuario.Estado.ACTIVO)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.internalServerError().body("Error al activar el proveedor");
        }
    }

    @PostMapping("/desactivar/{id}")
    public ResponseEntity<?> desactivarProveedor(@PathVariable Long id) {
        if (service.cambiarEstadoProveedor(id, Usuario.Estado.INACTIVO)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.internalServerError().body("Error al desactivar el proveedor");
        }
    }
}
