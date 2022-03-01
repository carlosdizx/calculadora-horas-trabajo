package ias.controllers;

import ias.utilities.Calculadora;
import ias.entity.Reporte;
import ias.services.api.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:4200","https://calculadora-nomina.netlify.app","https://calculadora-nomina-ias.netlify.app"})
@RestController
@RequestMapping("/calculos/")
public class CalculadoraRest {

    private final static Calculadora CALCULADORA = new Calculadora();

    private final static Map<String, Object> RESPONSE = new HashMap<>();

    @Autowired
    private ReporteService service;

    @GetMapping("{tecnico}/{anio}/{semana}")
    public ResponseEntity<HashMap<String, Object>> findAllByTecnico(@PathVariable String tecnico, @PathVariable Integer anio, @PathVariable Integer semana) {
        RESPONSE.clear();
        try {
            final List<Reporte> listado = service.findAllByTecnico(tecnico);
            RESPONSE.put("mensaje", CALCULADORA.darInformes(anio, semana, listado));
            return new ResponseEntity(RESPONSE, HttpStatus.OK);
        } catch (DataAccessException e) {
            RESPONSE.put("mensaje", "No se ha logrado realizar la consulta en la base de datos");
            RESPONSE.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity(RESPONSE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
