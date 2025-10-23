package co.unicauca.gestrabajogradojpa.mapper;

import co.unicauca.gestrabajogradojpa.dto.ProfesorDTO;
import co.unicauca.gestrabajogradojpa.model.Profesor;

public final class ProfesorMapper {
    private ProfesorMapper() {}

    public static ProfesorDTO toDTO(Profesor p) {
        if (p == null) return null;
        return new ProfesorDTO(
                p.getId(), p.getNombres(), p.getApellidos(),
                p.getEmail(), p.getCelular(), p.getPrograma()
        );
    }
}
