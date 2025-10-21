package co.unicauca.gestrabajogradojpa.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "tipo_trabajo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TipoTrabajo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private EnumModalidad nombre;

    @NotNull
    private int maxEstudiantes;
}
