package ias.services.impl;

import ias.enums.ListaServicios;
import ias.genericos.GenericServiceImpl;
import ias.models.Reporte;
import ias.repositories.ReporteDAO;
import ias.services.api.ReporteService;
import ias.utilities.Calculadora;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReporteServiceImpl extends GenericServiceImpl<Reporte, Long> implements ReporteService {

    private final static Calculadora CALCULADORA = new Calculadora();

    private final static Map<String, Object> RESPONSE = new HashMap<>();

    private final static String NOMBRE_EN_PLURAL = "reportes";

    private final static String NOMBRE_ENTIDAD = "reporte";

    @Autowired
    private ReporteDAO dao;

    @Override
    public JpaRepository<Reporte, Long> getDao() {
        return dao;
    }

    //-----------------------------------------------------------------------------------
    //---------------------------------Constructores-------------------------------------
    //-----------------------------------------------------------------------------------

    public ReporteServiceImpl() {
    }

    public ReporteServiceImpl(ReporteDAO dao) {
        this.dao = dao;
    }

    //-----------------------------------------------------------------------------------
    //---------------------------------CRUD RESPONSE-------------------------------------
    //-----------------------------------------------------------------------------------

    @Override
    public ResponseEntity<HashMap<String, Object>> findAll() {
        RESPONSE.clear();
        try {
            RESPONSE.put(NOMBRE_EN_PLURAL, dao.findAll());
            return new ResponseEntity(RESPONSE, HttpStatus.OK);
        } catch (DataAccessException e) {
            RESPONSE.put("mensaje", "No se ha logrado realizar la consulta en la base de datos");
            RESPONSE.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity(RESPONSE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<HashMap<String, Object>> create(Reporte reporte, BindingResult result) {
        RESPONSE.clear();
        if (result!=null){
            if (result.hasErrors()) {

                List<String> errors = result.getFieldErrors()
                        .stream()
                        .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                        .collect(Collectors.toList());

                RESPONSE.put("errors", errors);
                return new ResponseEntity(RESPONSE, HttpStatus.BAD_REQUEST);
            }
        }
        if (!reporte.esRangoValido()) {
            RESPONSE.put("error", "Fechas erroneas, ingrese correctamente los valores");
            return new ResponseEntity(RESPONSE, HttpStatus.BAD_REQUEST);
        }
        try {
            Reporte nuevaEntidad = dao.save(reporte);
            RESPONSE.put("mensaje", NOMBRE_ENTIDAD + " se ha sido creado con éxito!");
            RESPONSE.put(NOMBRE_ENTIDAD, nuevaEntidad);
            return new ResponseEntity(RESPONSE, HttpStatus.CREATED);

        } catch (DataAccessException e) {
            RESPONSE.put("mensaje", "Error al realizar el insert en la base de datos");
            RESPONSE.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity(RESPONSE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //---------------------------------------------------------------------------
    //---------------------------------- EXTRA ----------------------------------
    //---------------------------------------------------------------------------

    @Override
    public ResponseEntity<HashMap<String, Object>> findAllServicios() {
        RESPONSE.clear();
        RESPONSE.put("servicios", ListaServicios.values());
        return new ResponseEntity(RESPONSE, HttpStatus.OK);
    }

    // -----------------------------------------------------------------------------------
    //-------------------------------------CALCULO------------------------------------------
    //-----------------------------------------------------------------------------------

    @Override
    public ResponseEntity<HashMap<String, Object>> findAllByTecnico(String tecnico, Integer anio, Integer semana) {
        RESPONSE.clear();
        try {
            final List<Reporte> reportes = dao.findAllByTecnico(tecnico);
            RESPONSE.put("mensaje", CALCULADORA.darInformes(anio, semana, reportes));
            return new ResponseEntity(RESPONSE, HttpStatus.OK);
        } catch (DataAccessException e) {
            RESPONSE.put("mensaje", "No se ha logrado realizar la consulta en la base de datos");
            RESPONSE.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity(RESPONSE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
