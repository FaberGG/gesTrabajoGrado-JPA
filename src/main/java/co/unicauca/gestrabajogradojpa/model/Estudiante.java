package co.unicauca.gestrabajogradojpa.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "estudiante")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Estudiante extends Persona {

    @NotNull
    @Column(unique = true)
    @Size(max = 20)
    private String codigoEstudiante;

    @NotNull
    @Enumerated(EnumType.STRING)
    private EnumProgram programa;

    public Estudiante(String nombres, String apellidos, String email, String celular,
                  String codigoEstudiante, EnumProgram programa) {
        super(null, nombres, apellidos, email, celular);
        this.codigoEstudiante = codigoEstudiante;
        this.programa = programa;
    }
}
