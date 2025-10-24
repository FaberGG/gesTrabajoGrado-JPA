package co.unicauca.gestrabajogradojpa.controller;

import co.unicauca.gestrabajogradojpa.model.Profesor;
import co.unicauca.gestrabajogradojpa.repository.ProfesorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/api/profesores")
@CrossOrigin(origins = "*")
public class ProfesorController {

    @Autowired
    private ProfesorRepository profesorRepository;

    // GET: Obtener todos los profesores
    @GetMapping
    public List<Profesor> getAllProfesores() {
        return profesorRepository.findAll();
    }

    // GET: Obtener un profesor por ID
    @GetMapping("/{id}")
    public ResponseEntity<Profesor> getProfesorById(@PathVariable Long id) {
        return profesorRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST: Crear nuevo profesor
    @PostMapping
    public Profesor createProfesor(@RequestBody Profesor profesor) {
        return profesorRepository.save(profesor);
    }

    // PUT: Actualizar profesor existente
   @PutMapping("/{id}")
    public ResponseEntity<?> updateProfesor(@PathVariable Long id, @RequestBody Profesor profesorDetails) {
        return profesorRepository.findById(id)
                .map(profesor -> {
                    // Verificar si intentan modificar campos bloqueados
                    if (profesorDetails.getPrograma() != null) {
                        
                        Map<String, Object> warning = new HashMap<>();
                        warning.put("error", "Campo no modificable");
                        warning.put("mensaje", "No tienes permiso para modificar el programa");
                        warning.put("camposBloqueados", List.of("programa"));
                        warning.put("camposModificables", List.of("nombres", "apellidos", "email", "celular"));
                        return ResponseEntity.status(403).body(warning); // 403 Forbidden
                    }
                    
                    // Campos modificables por el profesor
                    if (profesorDetails.getNombres() != null) {
                        profesor.setNombres(profesorDetails.getNombres());
                    }
                    if (profesorDetails.getApellidos() != null) {
                        profesor.setApellidos(profesorDetails.getApellidos());
                    }
                    if (profesorDetails.getEmail() != null) {
                        profesor.setEmail(profesorDetails.getEmail());
                    }
                    if (profesorDetails.getCelular() != null) {
                        profesor.setCelular(profesorDetails.getCelular());
                    }
                    
                    Profesor actualizado = profesorRepository.save(profesor);
                    return ResponseEntity.ok(actualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    //  DELETE: Eliminar profesor
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProfesor(@PathVariable Long id) {
        return profesorRepository.findById(id)
                .map(profesor -> {
                    profesorRepository.delete(profesor);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
