package ias.services.api;

import ias.genericos.GenericServiceApi;
import ias.models.Reporte;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;

public interface ReporteService extends GenericServiceApi<Reporte, Long> {

    ResponseEntity<HashMap<String, Object>> findAll();

    ResponseEntity<HashMap<String, Object>> findAllByTecnico(String tecnico, Integer anio, Integer semana);
}
