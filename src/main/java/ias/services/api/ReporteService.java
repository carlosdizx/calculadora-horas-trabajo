package ias.services.api;

import ias.genericos.GenericServiceApi;
import ias.modelos.Reporte;

import java.util.List;

public interface ReporteService extends GenericServiceApi<Reporte, Long> {
    List<Reporte> findAllByTecnico(String tecnico);
}
