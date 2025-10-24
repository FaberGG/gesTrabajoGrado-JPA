package co.unicauca.gestrabajogradojpa.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

@Entity
@Table(name = "formato_a")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FormatoA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private Integer numeroIntento;

    @NotNull
    @Size(max = 255)
    private String rutaArchivo;

    @NotNull
    @Size(max = 100)
    private String nombreArchivo;

    @Size(max = 255)
    private String rutaCartaAceptacion;

    @Size(max = 100)
    private String nombreCartaAceptacion;

    @NotNull
    private LocalDateTime fechaCarga;

    @NotNull
    @Enumerated(EnumType.STRING)
    private EnumEstadoFormato estado;

    @Column(columnDefinition = "TEXT")
    private String observaciones;

    private Integer evaluadoPor;

    private LocalDateTime fechaEvaluacion;

    // Relación hacia TrabajoGrado (lado "muchos")
    @ManyToOne
    @JoinColumn(name = "trabajo_grado_id", nullable = false)
    @JsonIgnore  // Evita bucle infinito en JSON
    private TrabajoGrado trabajoGrado;
}
