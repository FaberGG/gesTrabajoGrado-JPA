package co.unicauca.gestrabajogradojpa.service;

import co.unicauca.gestrabajogradojpa.dto.TipoTrabajoDTO;
import java.util.List;

public interface TipoTrabajoService {
    List<TipoTrabajoDTO> findAll();
    TipoTrabajoDTO findById(Long id);
}
