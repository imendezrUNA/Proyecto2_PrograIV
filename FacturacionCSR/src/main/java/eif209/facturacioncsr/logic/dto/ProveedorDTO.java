package eif209.facturacioncsr.logic.dto;

public class ProveedorDTO {
    private Long id;
    private String nombre;
    private String correoElectronico;
    private String estadoUsuario; // Estado del usuario

    public ProveedorDTO() {
        this.id = null;
        this.nombre = null;
        this.correoElectronico = null;
        this.estadoUsuario = null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getEstadoUsuario() {
        return estadoUsuario;
    }

    public void setEstadoUsuario(String estadoUsuario) {
        this.estadoUsuario = estadoUsuario;
    }
}
