package co.unicauca.gestrabajogradojpa.controller;

import co.unicauca.gestrabajogradojpa.model.TrabajoGrado;
import co.unicauca.gestrabajogradojpa.repository.TrabajoGradoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trabajos")
public class TrabajoGradoController {

    @Autowired
    private TrabajoGradoRepository trabajoGradoRepository;

    @GetMapping
    public List<TrabajoGrado> getAllTrabajos() {
        return trabajoGradoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrabajoGrado> getTrabajoById(@PathVariable Integer id) {
        return trabajoGradoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
