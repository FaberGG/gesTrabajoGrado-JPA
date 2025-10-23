package co.unicauca.gestrabajogradojpa.controller;

import co.unicauca.gestrabajogradojpa.model.Profesor;
import co.unicauca.gestrabajogradojpa.repository.ProfesorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<Profesor> updateProfesor(
            @PathVariable Long id,
            @RequestBody Profesor profesorDetails) {

        return profesorRepository.findById(id)
                .map(profesor -> {
                    profesor.setNombres(profesorDetails.getNombres());
                    profesor.setApellidos(profesorDetails.getApellidos());
                    profesor.setEmail(profesorDetails.getEmail());
                    profesor.setPrograma(profesorDetails.getPrograma());
                    profesor.setCelular(profesorDetails.getCelular());
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
