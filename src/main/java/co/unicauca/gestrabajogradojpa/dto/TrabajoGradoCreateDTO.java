package co.unicauca.gestrabajogradojpa.dto;

import java.util.List;

public record TrabajoGradoCreateDTO(
        String titulo,
        Long tipoTrabajoId,
        Long estudiante1Id,
        Long estudiante2Id,          // puede ser null
        Long directorId,
        List<Long> codirectorIds     // puede ser vacía
) {}
