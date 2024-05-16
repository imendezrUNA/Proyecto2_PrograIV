package eif209.facturacioncsr.data;
import eif209.facturacioncsr.logic.Cliente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
@Repository
public interface ClienteRepository extends CrudRepository<Cliente, Long> {
    @Query("select c from Cliente c where c.id = ?1")
    Optional<Cliente> findByClienteId(String clienteId);
    Collection<Cliente> findClienteByProveedorId(Long proveedorId);
    Iterable<Cliente> findAll();

    @Query("select c from Cliente c where c.nombre like %?1% and c.proveedor.id = ?2")
    Collection<Cliente> findClientesContengan(String clienteNom, Long proveedorId);

    @Override
    <S extends Cliente> S save(S entity);

    @Override
    Optional<Cliente> findById(Long aLong);
}
