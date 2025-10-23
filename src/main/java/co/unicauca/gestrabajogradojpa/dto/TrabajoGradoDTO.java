package co.unicauca.gestrabajogradojpa.dto;

import java.util.List;

public record TrabajoGradoDTO(
        Integer id,
        String titulo,
        TipoTrabajoDTO tipoTrabajo,
        EstudianteDTO estudiante1,
        EstudianteDTO estudiante2,
        ProfesorDTO director,
        List<ProfesorDTO> codirectores,
        List<FormatoADTO> formatosA // si prefieres, puedes quitar esta lista y devolverla por endpoint aparte
) {}
