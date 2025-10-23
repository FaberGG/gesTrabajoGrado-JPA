package co.unicauca.gestrabajogradojpa.dto;

import co.unicauca.gestrabajogradojpa.model.EnumModalidad;

public record TipoTrabajoDTO(
        Long id,
        EnumModalidad nombre,
        int maxEstudiantes
) {}
