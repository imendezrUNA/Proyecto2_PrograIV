package eif209.facturacioncsr.logic;

import eif209.facturacioncsr.data.ClienteRepository;
import eif209.facturacioncsr.data.ProductoRepository;
import eif209.facturacioncsr.data.ProveedorRepository;
import eif209.facturacioncsr.data.UsuarioRepository;

import eif209.facturacioncsr.data.*;
//import eif209.facturacioncsr.dto.ProveedorRegistroDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service("service")
public class Service {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ProveedorRepository proveedorRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private FacturaRepository facturaRepository;
    @Autowired
    private DetallefacturaRepository detallefacturaRepository;

    /*@Transactional
    public boolean registrarProveedorYUsuario(ProveedorRegistroDTO registroDTO) {
        // Verificar si ya existe un usuario con el mismo username
        if (usuarioRepository.findByNombreUsuario(registroDTO.getNombreUsuario()).isPresent()) {
            return false;
        }
        // Verificar si ya existe un proveedor con el mismo ID
        if (proveedorRepository.findById(Long.valueOf(registroDTO.getIdProveedor())).isPresent()) {
            return false;
        }
        // Crear el nuevo Usuario
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombreUsuario(registroDTO.getNombreUsuario());
        nuevoUsuario.setContrasena(registroDTO.getContrasena()); // texto plano
        nuevoUsuario.setEstado(Usuario.Estado.PENDIENTE);
        nuevoUsuario.setRol(Usuario.Rol.PROVEEDOR);

        // Crear el nuevo Proveedor
        Proveedor nuevoProveedor = new Proveedor();
        nuevoProveedor.setId(Long.parseLong(registroDTO.getIdProveedor()));
        nuevoProveedor.setNombre(registroDTO.getNombreProveedor());
        nuevoProveedor.setCorreoElectronico(registroDTO.getCorreoElectronico());
        nuevoProveedor.setNumeroTelefono(registroDTO.getNumeroTelefono());
        nuevoProveedor.setDireccion(registroDTO.getDireccion());
        nuevoProveedor.setUsuarioByUsuarioId(nuevoUsuario); // Asociar el usuario al proveedor

        // Guardar las entidades en la db
        usuarioRepository.save(nuevoUsuario);
        proveedorRepository.save(nuevoProveedor);

        return true;
    }*/

    @Transactional
    public boolean cambiarEstadoProveedor(Long proveedorId, Usuario.Estado nuevoEstado) {
        Optional<Proveedor> proveedorOpt = proveedorRepository.findById(proveedorId);
        if (proveedorOpt.isPresent()) {
            Proveedor proveedor = proveedorOpt.get();
            Usuario usuario = proveedor.getUsuarioByUsuarioId();
            usuario.setEstado(nuevoEstado);
            usuarioRepository.save(usuario);
            return true;
        }
        return false;
    }

    public boolean registroDeProducto(Producto producto) {
        if (productoRepository.findProductoById(producto.getId()).equals(producto.getId())) {
            return false;
        }
        //crea nuevo producto
        Producto nuevo = new Producto();
        nuevo.setId(producto.getId());
        nuevo.setDescripcion(producto.getDescripcion());
        nuevo.setNombre(producto.getNombre());
        nuevo.setPrecio(producto.getPrecio());
        nuevo.setProveedor(producto.getProveedor());
        return true;
    }

    public Iterable<Producto> findProductByProveId(Long proveId) {
        return productoRepository.findProductoByProveedorId(proveId);
    }
    public Iterable<Cliente> findClienteByProveedorId( Long proveId) {
        return clienteRepository.findClienteByProveedorId(proveId);
    }

    public Optional<Usuario> usuarioRead(String nombreUsuario) {
        return usuarioRepository.findByNombreUsuario(nombreUsuario);
    }

    public Optional<Proveedor> proveedorRead(int usuarioId) {
        return proveedorRepository.findProveedorByUsuarioByUsuarioId_Id(usuarioId);
    }
    public Optional<Cliente> clienteRead(String clienteId) {
        return clienteRepository.findByClienteId(clienteId);
    }

    public Optional<Producto> productoRead(int ProductoId) {
        return productoRepository.findByProductoById(ProductoId);
    }

    public Iterable<Proveedor> proveedorFindAll() {
        return proveedorRepository.findAll();
    }

    public List<Proveedor> proveedorFindAllSortedByEstadoAsc() {
        return proveedorRepository.findAllByUsuarioEstadoOrderByEstadoAsc();
    }

    public List<Proveedor> proveedorFindAllSortedByEstadoDesc() {
        return proveedorRepository.findAllByUsuarioEstadoOrderByEstadoDesc();
    }

    public Iterable<Cliente> clienteFindAll() {
        return clienteRepository.findAll();
    }

    public Iterable<Cliente> clienteReadAll(long id) {
        return clienteRepository.findClienteByProveedorId(id); //cambiar
    }

    public Iterable<Cliente> clienteSearch(Proveedor proveedor, String nombreCliente) {
        return null; //cambiar
    }

    public void guardarProducto(Producto productoGuardar) {
        productoRepository.save(productoGuardar);

    }


    @Transactional
    public boolean actualizarProveedor(Proveedor proveedorActualizado) {
        if (proveedorActualizado == null || proveedorActualizado.getUsuarioByUsuarioId() == null) {
            return false;
        }

        Optional<Proveedor> proveedorOpt = proveedorRepository.findById(proveedorActualizado.getId());
        if (proveedorOpt.isEmpty()) {
            return false; // No se encontró un proveedor con el ID
        }

        Proveedor proveedorExistente = proveedorOpt.get();
        proveedorExistente.setNombre(proveedorActualizado.getNombre());
        proveedorExistente.setCorreoElectronico(proveedorActualizado.getCorreoElectronico());
        proveedorExistente.setNumeroTelefono(proveedorActualizado.getNumeroTelefono());
        proveedorExistente.setDireccion(proveedorActualizado.getDireccion());

        proveedorRepository.save(proveedorExistente);
        return true;
    }

    public List<Factura> facturasByProveedorId(Long provId){
        return (List<Factura>)facturaRepository.findByProveedorByProveedorIdId(provId);
    }

    public void registrarFactura(Cliente cliente, Proveedor proveedor, List<Detallefactura> detallesFactura, BigDecimal total){

        LocalDate localDate = LocalDate.now();
        Factura factura = new Factura();
        factura.setId(-1);
        factura.setFecha( Date.valueOf(localDate));
        factura.setTotal(total);
        factura.setClienteByClienteId(cliente);
        factura.setDetallefacturasById(detallesFactura);
        factura.setProveedorByProveedorId(proveedor);
        facturaRepository.save(factura);
        registrarDetallesFactura(detallesFactura);
    }
    public Factura getByMaxId(){
        return facturaRepository.findByMaxId();
    }

    public void registrarDetallesFactura(List<Detallefactura> detallefacturas){
        Factura factura = getByMaxId();
        for (Detallefactura d: detallefacturas){
            d.setFacturaByFacturaId(factura);
            d.setSubtotal(d.getSubtotal());
            detallefacturaRepository.save(d);
        }
    }
    public Factura getFacturaById(int id){
        return facturaRepository.findById(id).get();
    }


    public void guardarCliente(Cliente clienteGuardar) {
        clienteRepository.save(clienteGuardar);

    }
    public Optional<Cliente> clienteporID(long id){
        return clienteRepository.findById(id);
    }

    public List<Cliente> clientePorNombre(String clienteNom, Long idProovedor){
        return (List<Cliente>) clienteRepository.findClientesContengan(clienteNom, idProovedor);
    }

}
