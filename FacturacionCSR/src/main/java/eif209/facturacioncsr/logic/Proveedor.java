package eif209.facturacioncsr.logic;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Collection;
import java.util.Objects;

@Entity
@JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = LazyFieldsFilter.class)
/*@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")*/
public class Proveedor {
    @Id
    @Column(name = "ID")
    private long id;
    @NotBlank(message = "El nombre no puede estar en blanco")
    @Size(max = 100, message = "El nombre no puede tener más de 100 caracteres")
    @Column(name = "nombre", nullable = false)
    private String nombre;
    @NotBlank(message = "El correo electrónico no puede estar en blanco")
    @Email(message = "El correo electrónico debe ser válido")
    @Size(max = 255, message = "El correo electrónico no puede tener más de 255 caracteres")
    @Column(name = "correoElectronico", nullable = false)
    private String correoElectronico;
    @NotBlank(message = "El número de teléfono no puede estar en blanco")
    @Pattern(regexp = "^(\\+506)?[1-9]\\d{7}$", message = "El número de teléfono debe tener el formato correcto")
    @Column(name = "numeroTelefono", nullable = false)
    private String numeroTelefono;
    @Size(max = 255, message = "La dirección no puede tener más de 255 caracteres")
    @Column(name = "direccion")
    private String direccion;
    @OneToMany(mappedBy = "proveedorByProveedorId", fetch = FetchType.LAZY)
    private Collection<Factura> facturasById;
    @OneToMany(mappedBy = "proveedor", fetch = FetchType.LAZY)
    private Collection<Producto> productosById;
    //@OneToOne(fetch = FetchType.LAZY)
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuarioID", referencedColumnName = "ID", nullable = false)
    private Usuario usuarioByUsuarioId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Proveedor proveedor = (Proveedor) o;
        return id == proveedor.id && Objects.equals(nombre, proveedor.nombre) && Objects.equals(correoElectronico, proveedor.correoElectronico) && Objects.equals(numeroTelefono, proveedor.numeroTelefono) && Objects.equals(direccion, proveedor.direccion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, correoElectronico, numeroTelefono, direccion);
    }

    public Collection<Factura> getFacturasById() {
        return facturasById;
    }

    public void setFacturasById(Collection<Factura> facturasById) {
        this.facturasById = facturasById;
    }

    public Collection<Producto> getProductosById() {
        return productosById;
    }

    public void setProductosById(Collection<Producto> productosById) {
        this.productosById = productosById;
    }

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuarioID", referencedColumnName = "ID", nullable = false)
    @JsonBackReference
    public Usuario getUsuarioByUsuarioId() {
        return usuarioByUsuarioId;
    }

    public void setUsuarioByUsuarioId(Usuario usuarioByUsuarioId) {
        this.usuarioByUsuarioId = usuarioByUsuarioId;
    }
}
