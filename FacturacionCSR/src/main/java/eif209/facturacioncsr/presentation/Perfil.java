package eif209.facturacioncsr.presentation;

import eif209.facturacioncsr.logic.Proveedor;
import eif209.facturacioncsr.logic.Service;
import eif209.facturacioncsr.security.UserDetailsImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/perfil")
public class Perfil {

    private final Service service;

    @Autowired
    public Perfil(Service service) {
        this.service = service;
    }

    @GetMapping("/get")
    public Proveedor getPerfil(@AuthenticationPrincipal UserDetailsImp userDetails) {
        int userId = userDetails.usuario().getId();
        return service.proveedorRead(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Proveedor no encontrado"));
    }

    @PostMapping("/update")
    public void updatePerfil(@RequestBody Proveedor proveedor, @AuthenticationPrincipal UserDetailsImp userDetails) {
        proveedor.setUsuarioByUsuarioId(userDetails.usuario());
        if (!service.actualizarProveedor(proveedor)) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "No se pudo actualizar el perfil");
        }
    }
}
