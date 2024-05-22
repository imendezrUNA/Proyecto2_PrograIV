package eif209.facturacioncsr.presentation;



import eif209.facturacioncsr.data.ProductoRepository;
import eif209.facturacioncsr.logic.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class Productos {
    @Autowired
    ProductoRepository productoRepository;

    @GetMapping
    public Iterable<Producto> read() {
        return productoRepository.findAll();
    }

    @GetMapping("/{cedula}")
    public Producto read(@PathVariable int id) {
        try {
            return productoRepository.findProductoById(id);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/search")
    public List<Producto> findByNombre(@RequestParam String nombre) {
        return productoRepository.findByNombre(nombre);
    }
    @PostMapping()
    public void create(@RequestBody Producto producto) {
        try {
            productoRepository.save(producto);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }
//    @DeleteMapping("/{cedula}")
//    public void delete(@PathVariable String id) {
//        try {
//            productoRepository.delete(id);
//        } catch (Exception ex) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
//        }
//    }
}