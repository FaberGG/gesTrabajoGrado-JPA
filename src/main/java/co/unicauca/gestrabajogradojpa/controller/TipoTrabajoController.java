package co.unicauca.gestrabajogradojpa.controller;

import co.unicauca.gestrabajogradojpa.model.TipoTrabajo;
import co.unicauca.gestrabajogradojpa.repository.TipoTrabajoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipos")
@CrossOrigin(origins = "*")
public class TipoTrabajoController {

    @Autowired
    private TipoTrabajoRepository tipoTrabajoRepository;

    // Obtener todos los tipos de trabajo
    @GetMapping
    public List<TipoTrabajo> getAllTipos() {
        return tipoTrabajoRepository.findAll();
    }

    // Obtener tipo de trabajo por ID
    @GetMapping("/{id}")
    public ResponseEntity<TipoTrabajo> getTipoById(@PathVariable Long id) {
        return tipoTrabajoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //  Crear tipo de trabajo
    @PostMapping
    public TipoTrabajo createTipoTrabajo(@RequestBody TipoTrabajo tipoTrabajo) {
        return tipoTrabajoRepository.save(tipoTrabajo);
    }

    // Actualizar tipo de trabajo
    @PutMapping("/{id}")
    public ResponseEntity<TipoTrabajo> updateTipoTrabajo(
            @PathVariable Long id,
            @RequestBody TipoTrabajo tipoDetails) {

        return tipoTrabajoRepository.findById(id)
                .map(tipo -> {
                    tipo.setNombre(tipoDetails.getNombre());
                    tipo.setMaxEstudiantes(tipoDetails.getMaxEstudiantes());
                    TipoTrabajo actualizado = tipoTrabajoRepository.save(tipo);
                    return ResponseEntity.ok(actualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    //  Eliminar tipo de trabajo
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTipoTrabajo(@PathVariable Long id) {
        return tipoTrabajoRepository.findById(id)
                .map(tipo -> {
                    tipoTrabajoRepository.delete(tipo);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
