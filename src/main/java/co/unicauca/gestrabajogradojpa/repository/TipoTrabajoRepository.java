package co.unicauca.gestrabajogradojpa.repository;

import co.unicauca.gestrabajogradojpa.model.TipoTrabajo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoTrabajoRepository extends JpaRepository<TipoTrabajo, Long> {
}
