package eif209.facturacioncsr.data;

import org.springframework.data.jpa.repository.Query;
import eif209.facturacioncsr.logic.Producto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Collection;

@Component("productoRepository")
public interface ProductoRepository extends CrudRepository<Producto, Long> {
    @Override
    Iterable<Producto> findAll();
    Iterable findProductosById(int id);
    Producto findProductoById(int id);

    Collection<Producto> findProductoByProveedorId(Long proveedorId);
    @Query("select p from Producto p where p.id = ?1")
    Optional<Producto> findByProductoById(String productoId);

    List<Producto> findByNombre(String nombre);
}
