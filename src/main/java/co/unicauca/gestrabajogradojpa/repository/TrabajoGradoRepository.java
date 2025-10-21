package co.unicauca.gestrabajogradojpa.repository;

import co.unicauca.gestrabajogradojpa.model.TrabajoGrado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrabajoGradoRepository extends JpaRepository<TrabajoGrado, Integer> {
    // Nota: El tipo de ID es Integer, no Long
}
