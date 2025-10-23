package co.unicauca.gestrabajogradojpa.controller;

import co.unicauca.gestrabajogradojpa.model.TrabajoGrado;
import co.unicauca.gestrabajogradojpa.repository.TrabajoGradoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trabajos")
@CrossOrigin(origins = "*")
public class TrabajoGradoController {

    @Autowired
    private TrabajoGradoRepository trabajoGradoRepository;

    // Obtener todos los trabajos
    @GetMapping
    public List<TrabajoGrado> getAllTrabajos() {
        return trabajoGradoRepository.findAll();
    }

    //  Obtener trabajo por ID
    @GetMapping("/{id}")
    public ResponseEntity<TrabajoGrado> getTrabajoById(@PathVariable Integer id) {
        return trabajoGradoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //  Crear un nuevo trabajo de grado
    @PostMapping
    public TrabajoGrado createTrabajo(@RequestBody TrabajoGrado trabajoGrado) {
        return trabajoGradoRepository.save(trabajoGrado);
    }

    // Actualizar trabajo existente
    @PutMapping("/{id}")
    public ResponseEntity<TrabajoGrado> updateTrabajo(
            @PathVariable Integer id,
            @RequestBody TrabajoGrado trabajoDetails) {

        return trabajoGradoRepository.findById(id)
                .map(trabajo -> {
                    trabajo.setTitulo(trabajoDetails.getTitulo());
                    trabajo.setModalidad(trabajoDetails.getModalidad());
                    trabajo.setFechaCreacion(trabajoDetails.getFechaCreacion());
                    trabajo.setObjetivoGeneral(trabajoDetails.getObjetivoGeneral());
                    trabajo.setObjetivosEspecificos(trabajoDetails.getObjetivosEspecificos());
                    trabajo.setEstado(trabajoDetails.getEstado());
                    trabajo.setNumeroIntentos(trabajoDetails.getNumeroIntentos());
                    trabajo.setEstudiante1(trabajoDetails.getEstudiante1());
                    trabajo.setEstudiante2(trabajoDetails.getEstudiante2());
                    trabajo.setDirector(trabajoDetails.getDirector());
                    trabajo.setCodirectores(trabajoDetails.getCodirectores());
                    trabajo.setTipoTrabajo(trabajoDetails.getTipoTrabajo());
                    TrabajoGrado actualizado = trabajoGradoRepository.save(trabajo);
                    return ResponseEntity.ok(actualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    //  Eliminar un trabajo
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTrabajo(@PathVariable Integer id) {
        return trabajoGradoRepository.findById(id)
                .map(trabajo -> {
                    trabajoGradoRepository.delete(trabajo);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
