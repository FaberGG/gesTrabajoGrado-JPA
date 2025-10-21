package co.unicauca.gestrabajogradojpa.repository;

import co.unicauca.gestrabajogradojpa.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {
}
