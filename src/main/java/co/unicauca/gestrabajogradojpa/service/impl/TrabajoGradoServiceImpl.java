package co.unicauca.gestrabajogradojpa.service.impl;

import co.unicauca.gestrabajogradojpa.dto.TrabajoGradoCreateDTO;
import co.unicauca.gestrabajogradojpa.dto.TrabajoGradoDTO;
import co.unicauca.gestrabajogradojpa.exception.ResourceNotFoundException;
import co.unicauca.gestrabajogradojpa.mapper.TrabajoGradoMapper;
import co.unicauca.gestrabajogradojpa.model.*;
import co.unicauca.gestrabajogradojpa.repository.*;
import co.unicauca.gestrabajogradojpa.service.TrabajoGradoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service @RequiredArgsConstructor
public class TrabajoGradoServiceImpl implements TrabajoGradoService {

    private final TrabajoGradoRepository tgRepo;
    private final EstudianteRepository estRepo;
    private final ProfesorRepository profRepo;
    private final TipoTrabajoRepository tipoRepo;

    @Override @Transactional(readOnly = true)
    public java.util.List<TrabajoGradoDTO> findAll() {
        return tgRepo.findAll().stream().map(TrabajoGradoMapper::toDTO).toList();
    }

    @Override @Transactional(readOnly = true)
    public TrabajoGradoDTO findById(Integer id) {
        return tgRepo.findById(id).map(TrabajoGradoMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("TrabajoGrado " + id + " no encontrado"));
    }

    @Override @Transactional
    public TrabajoGradoDTO create(TrabajoGradoCreateDTO dto) {
        Estudiante e1 = estRepo.findById(dto.estudiante1Id())
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante1 no existe"));
        Estudiante e2 = null;
        if (dto.estudiante2Id() != null) {
            e2 = estRepo.findById(dto.estudiante2Id())
                    .orElseThrow(() -> new ResourceNotFoundException("Estudiante2 no existe"));
        }
        Profesor dir = profRepo.findById(dto.directorId())
                .orElseThrow(() -> new ResourceNotFoundException("Director no existe"));
        TipoTrabajo tipo = tipoRepo.findById(dto.tipoTrabajoId())
                .orElseThrow(() -> new ResourceNotFoundException("TipoTrabajo no existe"));

        var tg = new TrabajoGrado();
        tg.setTitulo(dto.titulo());
        tg.setEstudiante1(e1);
        tg.setEstudiante2(e2);
        tg.setDirector(dir);
        tg.setTipoTrabajo(tipo);
        tg.setCodirectores(new ArrayList<>());

        if (dto.codirectorIds() != null) {
            for (Long id : dto.codirectorIds()) {
                Profesor cod = profRepo.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Codirector " + id + " no existe"));
                tg.getCodirectores().add(cod);
            }
        }

        tg = tgRepo.save(tg);
        return TrabajoGradoMapper.toDTO(tg);
    }
}
