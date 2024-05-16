package eif209.facturacioncsr.logic;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Collection;
import java.util.Objects;

@Entity
public class Cliente {
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
    @Pattern(regexp = "^(\\+506)?[2-8]\\d{7}$", message = "El número de teléfono debe tener el formato correcto")
    @Column(name = "numeroTelefono", nullable = false)
    private String numeroTelefono;
    @Size(max = 255, message = "La dirección no puede tener más de 255 caracteres")
    @Column(name = "direccion")
    private String direccion;
    @OneToMany(mappedBy = "clienteByClienteId")
    private Collection<Factura> facturasById;
    @ManyToOne
    @JoinColumn(name = "proveedorID", referencedColumnName = "ID", nullable = false)
    private Proveedor proveedor;

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
        Cliente cliente = (Cliente) o;
        return id == cliente.id && Objects.equals(nombre, cliente.nombre) && Objects.equals(correoElectronico, cliente.correoElectronico) && Objects.equals(numeroTelefono, cliente.numeroTelefono) && Objects.equals(direccion, cliente.direccion);
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
    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }
}
