package co.unicauca.gestrabajogradojpa.mapper;

import co.unicauca.gestrabajogradojpa.dto.TipoTrabajoDTO;
import co.unicauca.gestrabajogradojpa.model.TipoTrabajo;

public final class TipoTrabajoMapper {
    private TipoTrabajoMapper() {}

    public static TipoTrabajoDTO toDTO(TipoTrabajo t) {
        if (t == null) return null;
        return new TipoTrabajoDTO(t.getId(), t.getNombre(), t.getMaxEstudiantes());
    }
}
