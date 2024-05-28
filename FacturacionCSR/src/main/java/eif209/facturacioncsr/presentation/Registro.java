package eif209.facturacioncsr.presentation;

import eif209.facturacioncsr.logic.dto.ProveedorRegistroDTO;
import eif209.facturacioncsr.logic.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/registro")
public class Registro {

    private final Service service;

    @Autowired
    public Registro(Service service) {
        this.service = service;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registrarProveedor(@Valid @RequestBody ProveedorRegistroDTO registroDTO) {
        if (!service.registrarProveedorYUsuario(registroDTO)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un proveedor registrado con la misma cédula o nombre de usuario.");
        }
    }
}
