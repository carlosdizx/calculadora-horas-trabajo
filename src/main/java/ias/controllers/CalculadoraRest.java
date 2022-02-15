package ias.controllers;

import ias.models.Calculadora;
import ias.models.Reporte;
import ias.services.api.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/calculos/")
public class CalculadoraRest {

    private final static Calculadora CALCULADORA = new Calculadora(null);

    private final static String NOMBRE_ENTIDAD = "calculo";

    private final static String NOMBRE_EN_PLURAL = "calculos";

    private final static Map<String, Object> RESPONSE = new HashMap<>();

    @Autowired
    private ReporteService service;

    @GetMapping("{tecnico}/{semana}")
    public ResponseEntity<HashMap<String, Object>> findAllByTecnico(@PathVariable String tecnico, @PathVariable Integer semana) {
        RESPONSE.clear();
        try {
            final List<Reporte> listado = service.findAllByTecnico(tecnico);
            if (listado.isEmpty()) {
                RESPONSE.put("mensaje", "Sin datos");
                return new ResponseEntity(RESPONSE, HttpStatus.OK);
            }
            String msg = "horas trabjadas: ";
            AtomicReference<Double> contHoras = new AtomicReference<>((double) 0);
            AtomicReference<Double> contHorasSemana = new AtomicReference<>((double) 0);
            listado.stream().forEach(reporte -> {
                CALCULADORA.setReporte(reporte);
                contHoras.updateAndGet(v -> v + CALCULADORA.darHorasTrabajadas());
                contHorasSemana.updateAndGet(v -> v + CALCULADORA.darHorasTrabajadasPorSemana(semana));
            });
            RESPONSE.put("mensaje", msg + contHoras);
            RESPONSE.put("mensaje2", msg + contHorasSemana);
            return new ResponseEntity(RESPONSE, HttpStatus.OK);
        } catch (DataAccessException e) {
            RESPONSE.put("mensaje", "No se ha logrado realizar la consulta en la base de datos");
            RESPONSE.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity(RESPONSE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
