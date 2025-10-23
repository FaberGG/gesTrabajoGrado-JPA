package co.unicauca.gestrabajogradojpa.service;

import co.unicauca.gestrabajogradojpa.dto.ProfesorDTO;
import java.util.List;

public interface ProfesorService {
    List<ProfesorDTO> findAll();
    ProfesorDTO findById(Long id);
}
