package co.unicauca.gestrabajogradojpa.service.impl;

import co.unicauca.gestrabajogradojpa.dto.EstudianteDTO;
import co.unicauca.gestrabajogradojpa.exception.ResourceNotFoundException;
import co.unicauca.gestrabajogradojpa.mapper.EstudianteMapper;
import co.unicauca.gestrabajogradojpa.repository.EstudianteRepository;
import co.unicauca.gestrabajogradojpa.service.EstudianteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service @RequiredArgsConstructor
public class EstudianteServiceImpl implements EstudianteService {

    private final EstudianteRepository repo;

    @Override @Transactional(readOnly = true)
    public List<EstudianteDTO> findAll() {
        return repo.findAll().stream().map(EstudianteMapper::toDTO).toList();
    }

    @Override @Transactional(readOnly = true)
    public EstudianteDTO findById(Long id) {
        return repo.findById(id).map(EstudianteMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante " + id + " no encontrado"));
    }

    @Override
    public void delete(Long id) {
        if (!repo.existsById(id)) throw new ResourceNotFoundException("Estudiante " + id + " no encontrado");
        repo.deleteById(id);
    }
}
