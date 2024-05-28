package eif209.facturacioncsr.presentation;

import eif209.facturacioncsr.data.ProductoRepository;
import eif209.facturacioncsr.logic.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productos")
public class Productos {
    @Autowired
    private ProductoRepository productoRepository;

    @GetMapping
    public List<Producto> read() {
        return productoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Producto read(@PathVariable Integer id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));
    }

    @GetMapping("/search")
    public List<Producto> findByNombre(@RequestParam String nombre) {
        return productoRepository.findByNombre(nombre);
    }

    @PostMapping
    public void create(@RequestBody Producto producto) {
        try {
            productoRepository.save(producto);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Conflicto al crear el producto");
        }
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Integer id, @RequestBody Producto producto) {
        Producto existingProducto = productoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));

        existingProducto.setNombre(producto.getNombre());
        existingProducto.setDescripcion(producto.getDescripcion());
        existingProducto.setPrecio(producto.getPrecio());

        try {
            productoRepository.save(existingProducto);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Conflicto al actualizar el producto");
        }
    }
}
