package dto;

import java.io.Serializable;

/**
 * DTO para transferir información de usuario entre cliente y servidor.
 */
public class UserDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Integer id;
    private String email;
    private String username;
    private String nombre;
    private String apellidos;
    private String dni;
    private String direccion;
    private String telefono1;
    private String telefono2;
    private String argazkiaUrl;
    private String tipoId;
    private String tipoNombre;
    
    // Campos para matriculación (ciclo y curso)
    private Integer cicloId;
    private String cicloNombre;
    private Integer curso;
    
    public UserDTO() {
    }
    
    // Getters y Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getApellidos() {
        return apellidos;
    }
    
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
    
    public String getDni() {
        return dni;
    }
    
    public void setDni(String dni) {
        this.dni = dni;
    }
    
    public String getDireccion() {
        return direccion;
    }
    
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    public String getTelefono1() {
        return telefono1;
    }
    
    public void setTelefono1(String telefono1) {
        this.telefono1 = telefono1;
    }
    
    public String getTelefono2() {
        return telefono2;
    }
    
    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }
    
    public String getArgazkiaUrl() {
        return argazkiaUrl;
    }
    
    public void setArgazkiaUrl(String argazkiaUrl) {
        this.argazkiaUrl = argazkiaUrl;
    }
    
    public String getTipoId() {
        return tipoId;
    }
    
    public void setTipoId(String tipoId) {
        this.tipoId = tipoId;
    }
    
    public String getTipoNombre() {
        return tipoNombre;
    }
    
    public void setTipoNombre(String tipoNombre) {
        this.tipoNombre = tipoNombre;
    }
    
    public Integer getCicloId() {
        return cicloId;
    }
    
    public void setCicloId(Integer cicloId) {
        this.cicloId = cicloId;
    }
    
    public String getCicloNombre() {
        return cicloNombre;
    }
    
    public void setCicloNombre(String cicloNombre) {
        this.cicloNombre = cicloNombre;
    }
    
    public Integer getCurso() {
        return curso;
    }
    
    public void setCurso(Integer curso) {
        this.curso = curso;
    }
}