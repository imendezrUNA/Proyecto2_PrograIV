package eif209.facturacioncsr.logic;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

public class GuardarFactura {
    private float total;
    private Collection<Detallefactura> detallefacturasById;
    private Proveedor proveedorByProveedorId;
    private Cliente clienteByClienteId;

    public GuardarFactura(float total, Collection<Detallefactura> detallefacturasById, Proveedor proveedorByProveedorId,Cliente clienteByClienteId){
        this.total = total;
        this.detallefacturasById = detallefacturasById;
        this.proveedorByProveedorId = proveedorByProveedorId;
        this.clienteByClienteId = clienteByClienteId;
    }

    public void setTotal(float total){
        this.total = total;
    }

    public void setDetallefacturasById(Collection<Detallefactura> detalles){
        this.detallefacturasById = detalles;
    }

    public void setProveedorByProveedorId(Proveedor proveedor){
        this.proveedorByProveedorId = proveedor;
    }

    public void setClienteByClienteId(Cliente cliente){
        this.clienteByClienteId = cliente;
    }

    public BigDecimal getTotal(){
        return BigDecimal.valueOf(total);
    }
    public List<Detallefactura> getDetallefacturasById(){
        return (List<Detallefactura>) detallefacturasById;
    }

    public Cliente getClienteByClienteId() {
        return clienteByClienteId;
    }

    public Proveedor getProveedorByProveedorId() {
        return proveedorByProveedorId;
    }
}
