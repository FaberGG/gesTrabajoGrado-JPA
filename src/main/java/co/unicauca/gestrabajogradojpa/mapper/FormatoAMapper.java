package co.unicauca.gestrabajogradojpa.mapper;

import co.unicauca.gestrabajogradojpa.dto.FormatoADTO;
import co.unicauca.gestrabajogradojpa.model.FormatoA;

public final class FormatoAMapper {
    private FormatoAMapper() {}

    public static FormatoADTO toDTO(FormatoA f) {
        if (f == null) return null;
        return new FormatoADTO(
                f.getId(),
                f.getObservaciones(),
                f.getEvaluadoPor(),
                f.getFechaEvaluacion(),
                f.getTrabajoGrado() != null ? f.getTrabajoGrado().getId() : null
        );
    }
}
