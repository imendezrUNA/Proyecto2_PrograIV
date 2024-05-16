package eif209.facturacioncsr.data;

import eif209.facturacioncsr.logic.Detallefactura;
import eif209.facturacioncsr.logic.Factura;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetallefacturaRepository extends CrudRepository<Detallefactura, Integer> {

}
