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

    @GetMapping("page/{page}")
    public ResponseEntity<HashMap<String, Object>> findAllByPage(@PathVariable Integer page) {
        RESPONSE.clear();
        try {
            final Pageable pageable = PageRequest.of(page, CANTIDAD_POR_PAGINA);
            Page paginate = service.getAll(pageable);
            RESPONSE.put(NOMBRE_EN_PLURAL, paginate);
            return new ResponseEntity(RESPONSE, HttpStatus.OK);
        } catch (DataAccessException e) {
            RESPONSE.put("mensaje", "No se ha logrado realizar la consulta en la base de datos");
            RESPONSE.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity(RESPONSE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("details/{id}")
    public ResponseEntity<HashMap<String, Object>> findAllById(@PathVariable Long id) {
        RESPONSE.clear();
        try {
            final Reporte reporte = service.findByID(id);
            if (reporte == null) {
                RESPONSE.put("mensaje", NOMBRE_ENTIDAD + " no encontrado");
                return new ResponseEntity(RESPONSE, HttpStatus.NOT_FOUND);
            } else {
                RESPONSE.put(NOMBRE_ENTIDAD, reporte);
                return new ResponseEntity(RESPONSE, HttpStatus.OK);
            }
        } catch (DataAccessException e) {
            RESPONSE.put("mensaje", "No se ha logrado realizar la consulta en la base de datos");
            RESPONSE.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity(RESPONSE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/")
    public ResponseEntity<HashMap<String, Object>> create(@Valid @RequestBody Reporte reporte, BindingResult result) {
        RESPONSE.clear();
        if (result.hasErrors()) {

            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            RESPONSE.put("errors", errors);
            return new ResponseEntity(RESPONSE, HttpStatus.BAD_REQUEST);
        }
        if (!reporte.esRangoValido()) {
            RESPONSE.put("error", "Fechas erroneas, ingrese correctamente los valores");
            return new ResponseEntity(RESPONSE, HttpStatus.BAD_REQUEST);
        }
        try {
            Reporte nuevaEntidad = service.save(reporte);
            RESPONSE.put("mensaje", NOMBRE_ENTIDAD + " ha sido creado con éxito!");
            RESPONSE.put(NOMBRE_ENTIDAD, nuevaEntidad);
            return new ResponseEntity(RESPONSE, HttpStatus.CREATED);

        } catch (DataAccessException e) {
            RESPONSE.put("mensaje", "Error al realizar el insert en la base de datos");
            RESPONSE.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity(RESPONSE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("edit/{id}")
    public ResponseEntity<HashMap<String, Object>> edit(@Valid @RequestBody Reporte reporte, BindingResult result, @PathVariable Long id) {
        RESPONSE.clear();
        if (result.hasErrors()) {

            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            RESPONSE.put("errors", errors);
            return new ResponseEntity(RESPONSE, HttpStatus.BAD_REQUEST);
        }
        if (!reporte.esRangoValido()) {
            RESPONSE.put("error", "Fechas erroneas, ingrese correctamente los valores");
            return new ResponseEntity(RESPONSE, HttpStatus.BAD_REQUEST);
        }
        try {
            Reporte buscado = service.findByID(id);
            if (buscado != null) {
                BeanUtils.copyProperties(reporte, buscado, "id");
                Reporte actualizado = service.save(buscado);
                RESPONSE.put("mensaje", NOMBRE_ENTIDAD + " ha sido actualizado con éxito!");
                RESPONSE.put(NOMBRE_ENTIDAD, actualizado);
                return new ResponseEntity(RESPONSE, HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity(RESPONSE, HttpStatus.NOT_MODIFIED);
            }
        } catch (DataAccessException e) {
            RESPONSE.put("mensaje", "Error al realizar la actualización en la base de datos");
            RESPONSE.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity(RESPONSE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HashMap<String, Object>> delete(@PathVariable Long id) {
        RESPONSE.clear();
        try {
            final Reporte reporte = service.findByID(id);
            if (reporte == null) {
                return new ResponseEntity(RESPONSE, HttpStatus.NO_CONTENT);
            } else {
                service.delete(id);
                RESPONSE.put("mensaje", "Se elimino!");
                return new ResponseEntity(RESPONSE, HttpStatus.ACCEPTED);
            }
        } catch (DataAccessException e) {
            RESPONSE.put("mensaje", "Error al realizar la eliminación en la base de datos");
            RESPONSE.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity(RESPONSE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //---------------------------------------------------------------------------
    //---------------------------------- EXTRA ----------------------------------
    //---------------------------------------------------------------------------

    @GetMapping("servicios")
    public ResponseEntity<HashMap<String, Object>> findAllServicios() {
        RESPONSE.clear();
        try {
            RESPONSE.put("servicios", ListaServicios.values());
            return new ResponseEntity(RESPONSE, HttpStatus.OK);
        } catch (DataAccessException e) {
            RESPONSE.put("mensaje", "No se ha logrado realizar la consulta en la base de datos");
            RESPONSE.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity(RESPONSE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
