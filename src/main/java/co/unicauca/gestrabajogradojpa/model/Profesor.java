package co.unicauca.gestrabajogradojpa.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "profesor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Profesor extends Persona {

    @NotNull
    @Enumerated(EnumType.STRING)
    private EnumProgram programa;

    public Profesor(String nombres, String apellidos, String email, String celular,
                EnumProgram programa) {
        super(null, nombres, apellidos, email, celular);
        this.programa = programa;
    }
}
