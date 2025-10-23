package co.unicauca.gestrabajogradojpa.service;

import co.unicauca.gestrabajogradojpa.dto.TrabajoGradoCreateDTO;
import co.unicauca.gestrabajogradojpa.dto.TrabajoGradoDTO;
import java.util.List;

public interface TrabajoGradoService {
    List<TrabajoGradoDTO> findAll();
    TrabajoGradoDTO findById(Integer id);
    TrabajoGradoDTO create(TrabajoGradoCreateDTO dto);
}
