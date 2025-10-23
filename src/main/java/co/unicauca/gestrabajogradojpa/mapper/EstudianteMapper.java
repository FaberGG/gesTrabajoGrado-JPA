package co.unicauca.gestrabajogradojpa.mapper;

import co.unicauca.gestrabajogradojpa.dto.EstudianteDTO;
import co.unicauca.gestrabajogradojpa.model.Estudiante;

public final class EstudianteMapper {
    private EstudianteMapper() {}

    public static EstudianteDTO toDTO(Estudiante e) {
        if (e == null) return null;
        return new EstudianteDTO(
                e.getId(), e.getNombres(), e.getApellidos(),
                e.getEmail(), e.getCelular(),
                e.getCodigoEstudiante(), e.getPrograma()
        );
    }
}
