package ias.controllers;

import ias.services.api.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@CrossOrigin(origins = {"http://localhost:4200", "https://calculadora-nomina.netlify.app", "https://calculadora-nomina-ias.netlify.app"})
@RestController
@RequestMapping("/calculos/")
public class CalculadoraRest {


    @Autowired
    private ReporteService service;

    @GetMapping("{tecnico}/{anio}/{semana}")
    public ResponseEntity<HashMap<String, Object>> findAllByTecnico(@PathVariable String tecnico, @PathVariable Integer anio, @PathVariable Integer semana) {
        return service.findAllByTecnico(tecnico, anio, semana);
    }
}
