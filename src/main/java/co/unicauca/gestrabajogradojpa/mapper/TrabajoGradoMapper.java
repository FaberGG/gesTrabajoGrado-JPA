package co.unicauca.gestrabajogradojpa.mapper;

import co.unicauca.gestrabajogradojpa.dto.*;
import co.unicauca.gestrabajogradojpa.model.TrabajoGrado;

import java.util.List;

public final class TrabajoGradoMapper {
    private TrabajoGradoMapper() {}

    public static TrabajoGradoDTO toDTO(TrabajoGrado tg) {
        if (tg == null) return null;

        TipoTrabajoDTO tipoDTO = TipoTrabajoMapper.toDTO(tg.getTipoTrabajo());
        EstudianteDTO e1 = EstudianteMapper.toDTO(tg.getEstudiante1());
        EstudianteDTO e2 = EstudianteMapper.toDTO(tg.getEstudiante2());
        var dir = ProfesorMapper.toDTO(tg.getDirector());
        List<ProfesorDTO> codirs = tg.getCodirectores()
                .stream().map(ProfesorMapper::toDTO).toList();
        List<FormatoADTO> fas = tg.getFormatosA()
                .stream().map(FormatoAMapper::toDTO).toList();

        return new TrabajoGradoDTO(
                tg.getId(), tg.getTitulo(),
                tipoDTO, e1, e2, dir, codirs, fas
        );
    }
}
