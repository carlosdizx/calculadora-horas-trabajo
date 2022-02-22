package ias.repositories;

import ias.entity.Reporte;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReporteDAO extends JpaRepository<Reporte, Long> {

    List<Reporte> findAllByTecnico(String tecnico);
}
