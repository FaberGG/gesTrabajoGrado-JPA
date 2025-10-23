package co.unicauca.gestrabajogradojpa.service;

import co.unicauca.gestrabajogradojpa.dto.FormatoACreateDTO;
import co.unicauca.gestrabajogradojpa.dto.FormatoADTO;
import java.util.List;

public interface FormatoAService {
    List<FormatoADTO> findByTrabajo(Integer trabajoId);
    FormatoADTO create(Integer trabajoId, FormatoACreateDTO dto);
}
