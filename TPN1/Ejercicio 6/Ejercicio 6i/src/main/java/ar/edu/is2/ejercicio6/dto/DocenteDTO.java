package ar.edu.is2.ejercicio6.dto;

import ar.edu.is2.ejercicio6.model.Sexo;
import java.time.LocalDate;

public class DocenteDTO {
  private Long id;
  private String nombre;
  private String apellido;
  private Sexo sexo;
  private LocalDate fechaNacimiento;
  private String mail;
  private String clave;
  private String claveRepetida;

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

  public String getApellido() {
    return apellido;
  }

  public void setApellido(String apellido) {
    this.apellido = apellido;
  }

  public Sexo getSexo() {
    return sexo;
  }

  public void setSexo(Sexo sexo) {
    this.sexo = sexo;
  }

  public LocalDate getFechaNacimiento() {
    return fechaNacimiento;
  }

  public void setFechaNacimiento(LocalDate fechaNacimiento) {
    this.fechaNacimiento = fechaNacimiento;
  }

  public String getMail() {
    return mail;
  }

  public void setMail(String mail) {
    this.mail = mail;
  }

  public String getClave() {
    return clave;
  }

  public void setClave(String clave) {
    this.clave = clave;
  }

  public String getClaveRepetida() {
    return claveRepetida;
  }

  public void setClaveRepetida(String claveRepetida) {
    this.claveRepetida = claveRepetida;
  }
}
