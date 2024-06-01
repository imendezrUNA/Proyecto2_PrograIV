package eif209.facturacioncsr.presentation;

import eif209.facturacioncsr.data.ClienteRepository;
import eif209.facturacioncsr.data.ProveedorRepository;
import eif209.facturacioncsr.logic.Cliente;
import eif209.facturacioncsr.logic.Proveedor;
import eif209.facturacioncsr.logic.dto.ClienteResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clientes")
public class Clientes {
    private final ClienteRepository clienteRepository;
    private final ProveedorRepository proveedorRepository;

    public Clientes(ClienteRepository clienteRepository, ProveedorRepository proveedorRepository) {
        this.clienteRepository = clienteRepository;
        this.proveedorRepository = proveedorRepository;
    }

    @GetMapping
    public Iterable<Cliente> read() {
        return clienteRepository.findAll();
    }

    @GetMapping("/{id}")
    public Cliente read(@PathVariable long id) {
        return clienteRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/search")
    public List<Cliente> findByNombre(@RequestParam String nombre) {
        return clienteRepository.findByNombre(nombre);
    }

    @PostMapping
    public void create(@RequestBody ClienteResponseDTO clienteDTO) {
        try {
            Optional<Proveedor> proveedor = proveedorRepository.findProveedorByUsuarioByUsuarioId_Id(clienteDTO.getProveedorId());
            Cliente cliente = new Cliente();
            cliente.setId(clienteDTO.getId()); // Asegúrate de que el ID se está configurando correctamente
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

    @PutMapping("/{id}")
    public void update(@PathVariable long id, @RequestBody ClienteResponseDTO clienteDTO) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        cliente.setNombre(clienteDTO.getNombre());
        cliente.setCorreoElectronico(clienteDTO.getCorreoElectronico());
        cliente.setNumeroTelefono(clienteDTO.getNumeroTelefono());
        cliente.setDireccion(clienteDTO.getDireccion());
        clienteRepository.save(cliente);
    }
}
