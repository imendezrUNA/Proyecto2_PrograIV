package eif209.facturacioncsr.presentation;

import ch.qos.logback.core.net.server.Client;
import eif209.facturacioncsr.data.ClienteRepository;
import eif209.facturacioncsr.data.ProveedorRepository;
import eif209.facturacioncsr.logic.Cliente;
import eif209.facturacioncsr.logic.Proveedor;
import eif209.facturacioncsr.logic.dto.ClienteResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clientes")
public class Clientes {
    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    ProveedorRepository proveedorRepository;


    @GetMapping
    public Iterable<Cliente> read() {
        return clienteRepository.findAll();
    }

    @GetMapping("/{id}")
    public Collection<Cliente> read(@PathVariable long id) {
        try {
            return clienteRepository.findClienteByProveedorId(id);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/search")
    public List<Cliente> findByNombre(@RequestParam String nombre) {
        return clienteRepository.findByNombre(nombre);
    }
    @PostMapping()
    public void create(@RequestBody ClienteResponseDTO clienteDTO) {
        try {
            Optional<Proveedor> proveedor = proveedorRepository.findProveedorByUsuarioByUsuarioId_Id(clienteDTO.getProveedorId());
            Cliente cliente = new Cliente();
            cliente.setId(clienteDTO.getId());
            cliente.setNombre(clienteDTO.getNombre());
            cliente.setCorreoElectronico(clienteDTO.getCorreoElectronico());
            cliente.setNumeroTelefono(clienteDTO.getNumeroTelefono());
            cliente.setDireccion(clienteDTO.getDireccion());
            cliente.setProveedor(proveedor.get());
            clienteRepository.save(cliente);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }
}