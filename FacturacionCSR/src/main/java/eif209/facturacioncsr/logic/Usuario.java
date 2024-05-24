package eif209.facturacioncsr.logic;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Objects;

@Entity
public class Usuario {
    public Usuario(String nombreUsuario, String contrasena, Rol rol) {
        this.nombreUsuario = nombreUsuario;
        this.setContrasena(contrasena);
        //this.estado = Estado.PENDIENTE; // Estado por defecto
        this.rol = rol;
    }

    public Usuario() {

    }

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
        this.contrasena = contrasena; // Descomentar en caso de querer usar PasswordUpdater.java
        //var encoder = new BCryptPasswordEncoder(); // Comentar en caso de querer usar PasswordUpdater.java
        //this.contrasena = "{bcrypt}"+encoder.encode(contrasena); // Comentar en caso de querer usar PasswordUpdater.java
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
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
