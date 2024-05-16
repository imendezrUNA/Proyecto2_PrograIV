package eif209.facturacioncsr.data;
import eif209.facturacioncsr.logic.Factura;
import eif209.facturacioncsr.logic.Producto;
import eif209.facturacioncsr.logic.Proveedor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
@Repository
public interface FacturaRepository extends CrudRepository<Factura, Integer> {
    @Query("select f from Factura f where f.proveedorByProveedorId = ?1")
    Iterable<Factura> findFacturasByProveedorId(Long provId);
    @Query("select f from Factura f where f.id = (select MAX(id) from Factura)")
    Factura findByMaxId();
    List<Factura> findByProveedorByProveedorIdId(Long id);

}
