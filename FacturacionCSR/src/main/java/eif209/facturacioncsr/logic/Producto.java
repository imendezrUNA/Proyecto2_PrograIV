package eif209.facturacioncsr.logic;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Producto {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID")
    private int id;
    @NotBlank(message = "El nombre del producto no puede estar en blanco")
    @Size(max = 50, message = "El nombre del producto no puede tener más de 50 caracteres")
    @Column(name = "nombre", nullable = false)
    private String nombre;
    @NotBlank(message = "La descripción del producto no puede estar en blanco")
    @Size(max = 255, message = "La descripción del producto no puede tener más de 255 caracteres")
    @Column(name = "descripcion", nullable = false)
    private String descripcion;
    @NotNull(message = "El precio del producto no puede ser nulo")
    @Positive(message = "El precio del producto debe ser un número positivo")
    @Column(name = "precio", nullable = false)
    private BigDecimal precio;
    @OneToMany(mappedBy = "productoByProductoId")
    private Collection<Detallefactura> detallefacturasById;
    @ManyToOne
    @JoinColumn(name = "proveedorID", referencedColumnName = "ID", nullable = false)
    private Proveedor proveedor;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Producto producto = (Producto) o;
        return id == producto.id && Objects.equals(nombre, producto.nombre) && Objects.equals(descripcion, producto.descripcion) && Objects.equals(precio, producto.precio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, descripcion, precio);
    }

    public Collection<Detallefactura> getDetallefacturasById() {
        return detallefacturasById;
    }

    public void setDetallefacturasById(Collection<Detallefactura> detallefacturasById) {
        this.detallefacturasById = detallefacturasById;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }
}
