package co.unicauca.gestrabajogradojpa.repository;

import co.unicauca.gestrabajogradojpa.model.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
}
