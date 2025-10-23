package co.unicauca.gestrabajogradojpa.service;

import co.unicauca.gestrabajogradojpa.dto.EstudianteDTO;
import java.util.List;

public interface EstudianteService {
    List<EstudianteDTO> findAll();
    EstudianteDTO findById(Long id);
    void delete(Long id);
}
