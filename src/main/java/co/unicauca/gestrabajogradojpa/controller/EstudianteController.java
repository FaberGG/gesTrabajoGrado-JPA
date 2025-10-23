package co.unicauca.gestrabajogradojpa.controller;

import co.unicauca.gestrabajogradojpa.model.Estudiante;
import co.unicauca.gestrabajogradojpa.repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estudiantes")
@CrossOrigin(origins = "*") // Permite llamadas desde Postman o navegador
public class EstudianteController {

    @Autowired
    private EstudianteRepository estudianteRepository;

    //  Obtener todos los estudiantes
    @GetMapping
    public List<Estudiante> getAllEstudiantes() {
        return estudianteRepository.findAll();
    }

    //  Obtener un estudiante por ID
    @GetMapping("/{id}")
    public ResponseEntity<Estudiante> getEstudianteById(@PathVariable Long id) {
        return estudianteRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear un nuevo estudiante
    @PostMapping
    public Estudiante createEstudiante(@RequestBody Estudiante estudiante) {
        return estudianteRepository.save(estudiante);
    }

    //  Actualizar un estudiante existente
    @PutMapping("/{id}")
    public ResponseEntity<Estudiante> updateEstudiante(@PathVariable Long id, @RequestBody Estudiante estudianteDetails) {
        return estudianteRepository.findById(id)
                .map(estudiante -> {
                    estudiante.setNombres(estudianteDetails.getNombres());
                    estudiante.setApellidos(estudianteDetails.getApellidos());
                    estudiante.setCodigoEstudiante(estudianteDetails.getCodigoEstudiante());
                    return ResponseEntity.ok(estudianteRepository.save(estudiante));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    //  Eliminar un estudiante
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEstudiante(@PathVariable Long id) {
        return estudianteRepository.findById(id)
                .map(estudiante -> {
                    estudianteRepository.delete(estudiante);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
