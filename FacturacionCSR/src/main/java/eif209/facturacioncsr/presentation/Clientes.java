package eif209.facturacioncsr.presentation;

import eif209.facturacioncsr.data.ClienteRepository;
import eif209.facturacioncsr.logic.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class Clientes {
    @Autowired
    ClienteRepository clienteRepository;

    @GetMapping
    public Iterable<Cliente> read() {
        return clienteRepository.findAll();
    }

    @GetMapping("/{cedula}")
    public Cliente read(@PathVariable String id) {
        try {
            return clienteRepository.findClienteById(id);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/search")
    public List<Cliente> findByNombre(@RequestParam String nombre) {
        return clienteRepository.findByNombre(nombre);
    }
    @PostMapping()
    public void create(@RequestBody Cliente cliente) {
        try {
            clienteRepository.save(cliente);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }
    @DeleteMapping("/{cedula}")
    public void delete(@PathVariable String id) {
        try {
            clienteRepository.delete(id);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}