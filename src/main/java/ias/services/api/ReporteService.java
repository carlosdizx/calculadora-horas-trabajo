package ias.services.api;

import ias.genericos.GenericServiceApi;
import ias.models.Reporte;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.HashMap;

public interface ReporteService extends GenericServiceApi<Reporte, Long> {

    ResponseEntity<HashMap<String, Object>> findAll();

    ResponseEntity<HashMap<String, Object>> create(Reporte reporte,BindingResult result);

    ResponseEntity<HashMap<String, Object>> findAllByTecnico(String tecnico, Integer anio, Integer semana);
}
