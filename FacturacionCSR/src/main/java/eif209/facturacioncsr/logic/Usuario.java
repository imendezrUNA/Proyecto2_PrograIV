package eif209.facturacioncsr.logic;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Collection;
import java.util.Objects;

@Entity
public class Usuario {
    public enum Estado {
        ACTIVO, INACTIVO, PENDIENTE;
    }

    public enum Rol {
        ADMINISTRADOR, PROVEEDOR;
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID")
    private int id;
    @NotBlank(message = "El nombre de usuario no puede estar en blanco")
    @Size(max = 50, message = "El nombre de usuario no puede tener más de 50 caracteres")
    @Column(name = "nombreUsuario", unique = true, nullable = false)
    private String nombreUsuario;
    @NotBlank(message = "La contraseña no puede estar en blanco")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    @Column(name = "contrasena", nullable = false)
    private String contrasena;
    @NotNull(message = "El estado no puede ser nulo")
    @Column(name = "estado", nullable = false)
    @Enumerated(EnumType.STRING)
    private Estado estado;
    @NotNull(message = "El rol no puede ser nulo")
    @Column(name = "rol", nullable = false)
    @Enumerated(EnumType.STRING)
    private Rol rol;
    @OneToOne(mappedBy = "usuarioByUsuarioId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Proveedor proveedor;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Object getEstado() {
        return estado;
    }

    public void setEstado(Object estado) {
        this.estado = (Estado) estado;
    }

    public Object getRol() {
        return rol;
    }

    public void setRol(Object rol) {
        this.rol = (Rol) rol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return id == usuario.id && Objects.equals(nombreUsuario, usuario.nombreUsuario) && Objects.equals(contrasena, usuario.contrasena) && Objects.equals(estado, usuario.estado) && Objects.equals(rol, usuario.rol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombreUsuario, contrasena, estado, rol);
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }
}
