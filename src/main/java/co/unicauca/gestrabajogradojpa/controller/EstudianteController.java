package co.unicauca.gestrabajogradojpa.controller;

import co.unicauca.gestrabajogradojpa.model.Estudiante;
import co.unicauca.gestrabajogradojpa.repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/estudiantes")
@CrossOrigin(origins = "*")
public class EstudianteController {

    @Autowired
    private EstudianteRepository estudianteRepository;

    @GetMapping
    public List<Estudiante> getAllEstudiantes() {
        return estudianteRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estudiante> getEstudianteById(@PathVariable Long id) {
        return estudianteRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Estudiante createEstudiante(@RequestBody Estudiante estudiante) {
        return estudianteRepository.save(estudiante);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEstudiante(@PathVariable Long id, @RequestBody Estudiante estudianteDetails) {
        return estudianteRepository.findById(id)
                .map(estudiante -> {
                    // Verificar si intentan modificar campos bloqueados
                    if (estudianteDetails.getCodigoEstudiante() != null || 
                        estudianteDetails.getPrograma() != null) {
                        
                        Map<String, Object> warning = new HashMap<>();
                        warning.put("error", "Campos no modificables");
                        warning.put("mensaje", "No tienes permiso para modificar: codigoEstudiante y programa");
                        warning.put("camposBloqueados", List.of("codigoEstudiante", "programa"));
                        warning.put("camposModificables", List.of("nombres", "apellidos", "email", "celular"));
                        return ResponseEntity.status(403).body(warning); // 403 Forbidden
                    }
                    
                    // Campos modificables por el estudiante
                    if (estudianteDetails.getNombres() != null) {
                        estudiante.setNombres(estudianteDetails.getNombres());
                    }
                    if (estudianteDetails.getApellidos() != null) {
                        estudiante.setApellidos(estudianteDetails.getApellidos());
                    }
                    if (estudianteDetails.getEmail() != null) {
                        estudiante.setEmail(estudianteDetails.getEmail());
                    }
                    if (estudianteDetails.getCelular() != null) {
                        estudiante.setCelular(estudianteDetails.getCelular());
                    }
                    
                    return ResponseEntity.ok(estudianteRepository.save(estudiante));
                })
                .orElse(ResponseEntity.notFound().build());
    }

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