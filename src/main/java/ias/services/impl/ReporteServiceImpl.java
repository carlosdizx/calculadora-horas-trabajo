package ias.services.impl;

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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReporteServiceImpl extends GenericServiceImpl<Reporte, Long> implements ReporteService {

    private final static Calculadora CALCULADORA = new Calculadora();

    private final static Map<String, Object> RESPONSE = new HashMap<>();

    @Autowired
    private ReporteDAO dao;

    @Override
    public JpaRepository<Reporte, Long> getDao() {
        return dao;
    }

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
