package eif209.facturacioncsr.presentation;

import eif209.facturacioncsr.data.ClienteRepository;
import eif209.facturacioncsr.data.FacturaRepository;
import eif209.facturacioncsr.data.ProductoRepository;
import eif209.facturacioncsr.data.ProveedorRepository;
import eif209.facturacioncsr.logic.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/facturar")
public class Facturar {
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    ProveedorRepository proveedorRepository;
    @Autowired
    FacturaRepository facturaRepository;
    @Autowired
    ProductoRepository productoRepository;
    @Autowired
    Service service;
    @GetMapping("/search")
    public Cliente findById(@RequestParam String id){
        System.out.println(id);
        return clienteRepository.findClienteById(Long.parseLong(id));
    }


    @GetMapping("/searchProveedor")
    public Proveedor findProveedor(@RequestParam String id){
        System.out.println(id);
        return proveedorRepository.findProveedorByUsuarioByUsuarioId_Id(Integer.parseInt(id)).get();
    }

    @GetMapping("/searchProducto")
    public Detallefactura productoFindById(@RequestParam String id){
        System.out.println(id);
        Producto producto = productoRepository.findProductoById(Integer.parseInt(id));
        Detallefactura detallefactura = new Detallefactura();
        detallefactura.setCantidad(1);
        detallefactura.setPrecioUnitario(producto.getPrecio());
        detallefactura.setSubtotal(producto.getPrecio());
        detallefactura.setProductoByProductoId(producto);
        return detallefactura;
    }

    @PostMapping()
    public void guardar(@RequestBody GuardarFactura fact) {
        service.registrarFactura(fact.getClienteByClienteId(), fact.getProveedorByProveedorId(), fact.getDetallefacturasById(), fact.getTotal());
    }



}
