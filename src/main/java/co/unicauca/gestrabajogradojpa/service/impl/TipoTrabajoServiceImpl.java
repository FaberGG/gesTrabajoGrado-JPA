package co.unicauca.gestrabajogradojpa.service.impl;

import co.unicauca.gestrabajogradojpa.dto.TipoTrabajoDTO;
import co.unicauca.gestrabajogradojpa.exception.ResourceNotFoundException;
import co.unicauca.gestrabajogradojpa.mapper.TipoTrabajoMapper;
import co.unicauca.gestrabajogradojpa.repository.TipoTrabajoRepository;
import co.unicauca.gestrabajogradojpa.service.TipoTrabajoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service @RequiredArgsConstructor
public class TipoTrabajoServiceImpl implements TipoTrabajoService {

    private final TipoTrabajoRepository repo;

    @Override @Transactional(readOnly = true)
    public List<TipoTrabajoDTO> findAll() {
        return repo.findAll().stream().map(TipoTrabajoMapper::toDTO).toList();
    }

    @Override @Transactional(readOnly = true)
    public TipoTrabajoDTO findById(Long id) {
        return repo.findById(id).map(TipoTrabajoMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("TipoTrabajo " + id + " no encontrado"));
    }
}
