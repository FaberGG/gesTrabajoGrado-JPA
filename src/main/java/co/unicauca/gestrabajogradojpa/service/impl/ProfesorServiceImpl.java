package co.unicauca.gestrabajogradojpa.service.impl;

import co.unicauca.gestrabajogradojpa.dto.ProfesorDTO;
import co.unicauca.gestrabajogradojpa.exception.ResourceNotFoundException;
import co.unicauca.gestrabajogradojpa.mapper.ProfesorMapper;
import co.unicauca.gestrabajogradojpa.repository.ProfesorRepository;
import co.unicauca.gestrabajogradojpa.service.ProfesorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service @RequiredArgsConstructor
public class ProfesorServiceImpl implements ProfesorService {

    private final ProfesorRepository repo;

    @Override @Transactional(readOnly = true)
    public List<ProfesorDTO> findAll() {
        return repo.findAll().stream().map(ProfesorMapper::toDTO).toList();
    }

    @Override @Transactional(readOnly = true)
    public ProfesorDTO findById(Long id) {
        return repo.findById(id).map(ProfesorMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Profesor " + id + " no encontrado"));
    }
}
