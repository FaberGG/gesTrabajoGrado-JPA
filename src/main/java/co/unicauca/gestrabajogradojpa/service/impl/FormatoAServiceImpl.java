package co.unicauca.gestrabajogradojpa.service.impl;

import co.unicauca.gestrabajogradojpa.dto.FormatoACreateDTO;
import co.unicauca.gestrabajogradojpa.dto.FormatoADTO;
import co.unicauca.gestrabajogradojpa.exception.ResourceNotFoundException;
import co.unicauca.gestrabajogradojpa.mapper.FormatoAMapper;
import co.unicauca.gestrabajogradojpa.model.FormatoA;
import co.unicauca.gestrabajogradojpa.model.TrabajoGrado;
import co.unicauca.gestrabajogradojpa.repository.FormatoARepository;
import co.unicauca.gestrabajogradojpa.repository.TrabajoGradoRepository;
import co.unicauca.gestrabajogradojpa.service.FormatoAService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service @RequiredArgsConstructor
public class FormatoAServiceImpl implements FormatoAService {

    private final FormatoARepository repo;
    private final TrabajoGradoRepository tgRepo;

    @Override @Transactional(readOnly = true)
    public List<FormatoADTO> findByTrabajo(Integer trabajoId) {
        TrabajoGrado tg = tgRepo.findById(trabajoId)
                .orElseThrow(() -> new ResourceNotFoundException("TrabajoGrado " + trabajoId + " no encontrado"));
        return tg.getFormatosA().stream().map(FormatoAMapper::toDTO).toList();
    }

    @Override @Transactional
    public FormatoADTO create(Integer trabajoId, FormatoACreateDTO dto) {
        TrabajoGrado tg = tgRepo.findById(trabajoId)
                .orElseThrow(() -> new ResourceNotFoundException("TrabajoGrado " + trabajoId + " no encontrado"));

        var f = new FormatoA();
        f.setObservaciones(dto.observaciones());
        f.setEvaluadoPor(dto.evaluadoPor());
        f.setFechaEvaluacion(dto.fechaEvaluacion());
        f.setTrabajoGrado(tg);

        f = repo.save(f);
        return FormatoAMapper.toDTO(f);
    }
}
