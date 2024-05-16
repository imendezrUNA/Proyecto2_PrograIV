package eif209.facturacioncsr.data;

import eif209.facturacioncsr.logic.Proveedor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProveedorRepository extends CrudRepository<Proveedor, Long> {
    Optional<Proveedor> findByUsuarioByUsuarioId_Id(int usuarioId);

    @Query("SELECT p FROM Proveedor p JOIN p.usuarioByUsuarioId u ORDER BY u.estado ASC")
    List<Proveedor> findAllByUsuarioEstadoOrderByEstadoAsc();

    @Query("SELECT p FROM Proveedor p JOIN p.usuarioByUsuarioId u ORDER BY u.estado DESC")
    List<Proveedor> findAllByUsuarioEstadoOrderByEstadoDesc();

    Optional<Proveedor> findProveedorByUsuarioByUsuarioId_Id(int usuarioId);
}
