package co.unicauca.gestrabajogradojpa.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "trabajo_grado")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrabajoGrado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Size(max = 255)
    private String titulo;

    @NotNull
    @Enumerated(EnumType.STRING)
    private EnumModalidad modalidad;

    @NotNull
    private LocalDateTime fechaCreacion;

    @NotNull
    @Size(max = 500)
    private String objetivoGeneral;

    @NotNull
    @Size(max = 1000)
    private String objetivosEspecificos;

    @NotNull
    @Enumerated(EnumType.STRING)
    private EnumEstadoProyecto estado;

    @NotNull
    private Integer numeroIntentos;

    // Relación: 1 o 2 estudiantes por trabajo
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "estudiante1_id", nullable = false)
    private Estudiante estudiante1;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "estudiante2_id")
    private Estudiante estudiante2;  // Puede ser null

    // Relación: 1 director
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "director_id", nullable = false)
    private Profesor director;

    // Relación: 0 o más codirectores
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
        name = "trabajo_grado_codirectores",
        joinColumns = @JoinColumn(name = "trabajo_grado_id"),
        inverseJoinColumns = @JoinColumn(name = "profesor_id")
    )
    private List<Profesor> codirectores = new ArrayList<>();

    // Relación: Tipo de trabajo
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "tipo_trabajo_id", nullable = false)
    private TipoTrabajo tipoTrabajo;

    // Relación bidireccional: Formatos A
    @OneToMany(mappedBy = "trabajoGrado", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FormatoA> formatosA = new ArrayList<>();
}
