package co.unicauca.gestrabajogradojpa.repository;

import co.unicauca.gestrabajogradojpa.model.FormatoA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormatoARepository extends JpaRepository<FormatoA, Integer> {
    // Nota: El tipo de ID es Integer, no Long
}
