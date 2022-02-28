package ias.controllers;

import ias.enums.ListaServicios;
import ias.models.Reporte;
import ias.services.api.ReporteService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


import javax.validation.Valid;

@CrossOrigin(origins = {"http://localhost:4200","https://calculadora-nomina.netlify.app","https://calculadora-nomina-ias.netlify.app"})
@RestController
@RequestMapping("/reportes/")
public class ReporteRest {

    private final static int CANTIDAD_POR_PAGINA = 5;

    private final static String NOMBRE_ENTIDAD = "reporte";

    private final static String NOMBRE_EN_PLURAL = "reportes";

    private final static Map<String, Object> RESPONSE = new HashMap<>();

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

    //---------------------------------------------------------------------------
    //---------------------------------- EXTRA ----------------------------------
    //---------------------------------------------------------------------------

    @GetMapping("servicios")
    public ResponseEntity<HashMap<String, Object>> findAllServicios() {
        return service.findAllServicios();
    }
}
