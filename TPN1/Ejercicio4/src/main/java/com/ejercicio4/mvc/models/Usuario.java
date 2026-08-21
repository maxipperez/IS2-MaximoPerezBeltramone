package com.ejercicio4.mvc.models;

import com.ejercicio4.mvc.models.enums.EstadoUsuario;
import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Clase abstracta que representa a un Usuario genérico en el sistema.
 * 
 * @Entity: Marca esta clase como una entidad JPA. Se creará una tabla para almacenar su información.
 * @Inheritance: Define la estrategia de herencia para las clases hijas (Administrador y Cliente).
 *               InheritanceType.JOINED: Crea una tabla para Usuario y tablas separadas para las clases hijas.
 *               Las tablas hijas se unen (JOIN) con esta mediante su clave primaria (que actúa como clave foránea).
 *               Es ideal para evitar valores nulos y mantener normalizada la base de datos.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Usuario {

    /**
     * @Id: Define que este atributo es la clave primaria (Primary Key).
     * @GeneratedValue: Delega a la BD/JPA la generación del valor. 
     *                  GenerationType.UUID genera un identificador único alfanumérico (ej. "a1b2c3d4-..."),
     *                  lo cual es más seguro y escalable que un entero autoincremental para IDs públicos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    /**
     * @Column: Configura propiedades a nivel de esquema en la base de datos.
     *          unique = true: Asegura que no haya dos usuarios con el mismo documento.
     *          nullable = false: Es obligatorio (NOT NULL).
     */
    @Column(unique = true, nullable = false)
    private int documento;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    /**
     * @Temporal: Indica a JPA cómo mapear el tipo java.util.Date a un tipo SQL.
     *            TemporalType.DATE: Almacena solo el día, mes y año (sin la hora), lo correcto para fecha de nacimiento.
     */
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;

    @Column(unique = true, nullable = false)
    private String correoPersonal;

    @Column(nullable = false)
    private String password;

    /**
     * @Enumerated: Indica cómo se persistirá el Enum en la BD.
     *              EnumType.STRING: Guarda el texto ("ACTIVO", "ELIMINADO"). Es más robusto ante cambios
     *              en el orden del enum que EnumType.ORDINAL (que guardaría 0, 1, 2...).
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoUsuario estado;

    @Column(nullable = false)
    private int intentosFallidos;

    /**
     * @OneToOne: Relación uno a uno. Un usuario tiene una dirección principal.
     * @JoinColumn: Especifica que en la tabla 'usuario' se creará una columna (direccion_id) 
     *              que funcionará como clave foránea hacia la tabla 'direccion'.
     * cascade = CascadeType.ALL: Si se guarda/elimina el Usuario, también se guardará/eliminará su Dirección.
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "direccion_id")
    private Direccion direccion;

    /**
     * @OneToMany: Relación uno a muchos. Un usuario tiene varios registros de historial de sesión.
     * mappedBy = "usuario": Indica que la relación es bidireccional y está controlada por el atributo 
     *                       'usuario' en la clase HistorialInicioSesion. Evita crear una tabla intermedia.
     * cascade = CascadeType.ALL: Operaciones en cadena hacia el historial.
     */
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<HistorialInicioSesion> historialSesiones;

    // Constructores vacíos obligatorios para JPA
    public Usuario() {}

    // Firmas de métodos según UML
    public boolean iniciarSesion(String correo, String pass) { return false; }
    public void registrarIntentoFallido() {}
    public void resetearIntentos() {}
    public void bloquearUsuario() {}
    public abstract boolean validarDatos(); // Marcado abstracto para que lo implementen los hijos

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public int getDocumento() { return documento; }
    public void setDocumento(int documento) { this.documento = documento; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public Date getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(Date fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    public String getCorreoPersonal() { return correoPersonal; }
    public void setCorreoPersonal(String correoPersonal) { this.correoPersonal = correoPersonal; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public EstadoUsuario getEstado() { return estado; }
    public void setEstado(EstadoUsuario estado) { this.estado = estado; }
    public int getIntentosFallidos() { return intentosFallidos; }
    public void setIntentosFallidos(int intentosFallidos) { this.intentosFallidos = intentosFallidos; }
    public Direccion getDireccion() { return direccion; }
    public void setDireccion(Direccion direccion) { this.direccion = direccion; }
    public List<HistorialInicioSesion> getHistorialSesiones() { return historialSesiones; }
    public void setHistorialSesiones(List<HistorialInicioSesion> historialSesiones) { this.historialSesiones = historialSesiones; }
}
