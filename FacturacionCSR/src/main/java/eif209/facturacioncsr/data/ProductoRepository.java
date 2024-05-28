package eif209.facturacioncsr.data;

import eif209.facturacioncsr.logic.Producto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Collection;

@Repository
public interface ProductoRepository extends CrudRepository<Producto, Integer> {
    @Override
    List<Producto> findAll();

    Optional<Producto> findById(Integer id); // Método estándar para buscar por ID

    Collection<Producto> findProductoByProveedorId(Long proveedorId);

    @Query("select p from Producto p where p.id = ?1")
    Optional<Producto> findByProductoById(Integer productoId);

    List<Producto> findByNombre(String nombre);

    Producto findProductoById(int id);
}
