package ar.edu.is2.ejercicio4.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.*;

/**
 * Entidad abstracta. @Inheritance(JOINED) guarda atributos comunes y específicos en tablas
 * relacionadas.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "usuarios")
public abstract class Usuario {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @NotNull @Positive private Integer documento;

  @NotBlank
  @Size(max = 80)
  private String nombre;

  @NotBlank
  @Size(max = 80)
  private String apellido;

  @NotNull private LocalDate fechaNacimiento;

  @NotBlank
  @Email
  @Column(unique = true)
  private String correoPersonal;

  @NotBlank private String password;

  @Enumerated(EnumType.STRING)
  @NotNull
  private EstadoUsuario estado = EstadoUsuario.PENDIENTE_REGISTRO;

  private int intentosFallidos;

  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "direccion_id")
  private Direccion direccion;

  @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<HistorialInicioSesion> historial = new ArrayList<>();

  public String getId() {
    return id;
  }

  public Integer getDocumento() {
    return documento;
  }

  public void setDocumento(Integer v) {
    documento = v;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String v) {
    nombre = v;
  }

  public String getApellido() {
    return apellido;
  }

  public void setApellido(String v) {
    apellido = v;
  }

  public LocalDate getFechaNacimiento() {
    return fechaNacimiento;
  }

  public void setFechaNacimiento(LocalDate v) {
    fechaNacimiento = v;
  }

  public String getCorreoPersonal() {
    return correoPersonal;
  }

  public void setCorreoPersonal(String v) {
    correoPersonal = v;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String v) {
    password = v;
  }

  public EstadoUsuario getEstado() {
    return estado;
  }

  public void setEstado(EstadoUsuario v) {
    estado = v;
  }

  public int getIntentosFallidos() {
    return intentosFallidos;
  }

  public void setIntentosFallidos(int v) {
    intentosFallidos = v;
  }

  public Direccion getDireccion() {
    return direccion;
  }

  public void setDireccion(Direccion v) {
    direccion = v;
  }

  public List<HistorialInicioSesion> getHistorial() {
    return historial;
  }
}
