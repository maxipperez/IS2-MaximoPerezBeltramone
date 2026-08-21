/**
 * Entidades y enumeraciones del dominio de gestión comercial.
 *
 * <p>Las clases anotadas con {@code @Entity} representan tablas administradas por JPA. {@code @Id}
 * identifica cada fila y {@code @GeneratedValue} solicita que JPA genere su identificador.
 * {@code @Inheritance(JOINED)} materializa la jerarquía Usuario en tablas relacionadas. Las
 * asociaciones {@code @OneToOne}, {@code @OneToMany} y {@code @ManyToOne} reproducen las
 * cardinalidades del DER; {@code @JoinColumn} nombra la clave foránea y {@code mappedBy} señala el
 * lado no propietario de una relación. Las restricciones {@code @NotBlank}, {@code @NotNull},
 * {@code @Positive} y similares expresan validaciones declarativas.
 */
package ar.edu.is2.ejercicio4.model;
