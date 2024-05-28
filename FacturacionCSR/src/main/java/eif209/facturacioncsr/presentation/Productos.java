package eif209.facturacioncsr.presentation;

import eif209.facturacioncsr.data.ProductoRepository;
import eif209.facturacioncsr.data.ProveedorRepository;
import eif209.facturacioncsr.logic.Producto;
import eif209.facturacioncsr.logic.Proveedor;
import eif209.facturacioncsr.logic.dto.ProductoResponseDTO;
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
    @Autowired
    ProveedorRepository proveedorRepository;

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
    public void create(@RequestBody ProductoResponseDTO productoDTO) {
        try {
            Optional<Proveedor> proveedor = proveedorRepository.findProveedorByUsuarioByUsuarioId_Id(productoDTO.getProveedorId());
            Producto producto = new Producto();
            producto.setId((int) productoDTO.getId());
            producto.setNombre(productoDTO.getNombre());
            producto.setDescripcion(productoDTO.getDescripcion());
            producto.setPrecio(productoDTO.getPrecio());
            producto.setProveedor(proveedor.get());
            productoRepository.save(producto);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno del servidor");
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
