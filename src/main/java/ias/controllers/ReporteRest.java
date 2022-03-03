package ias.controllers;

import ias.models.Reporte;
import ias.services.api.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;

@CrossOrigin(origins = {"http://localhost:4200", "https://calculadora-nomina.netlify.app", "https://calculadora-nomina-ias.netlify.app"})
@RestController
@RequestMapping("/reportes/")
public class ReporteRest {

    @Autowired
    private ReporteService service;

    @GetMapping
    public ResponseEntity<HashMap<String, Object>> findAll() {
        return service.findAll();
    }

    @PostMapping("/")
    public ResponseEntity<HashMap<String, Object>> create(@Valid @RequestBody Reporte reporte, BindingResult result) {
        return service.create(reporte, result);
    }

    @GetMapping("servicios")
    public ResponseEntity<HashMap<String, Object>> findAllServicios() {
        return service.findAllServicios();
    }
}
