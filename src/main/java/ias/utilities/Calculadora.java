package ias.utilities;

import ias.models.Reporte;

import java.util.*;

public class Calculadora {



    private Reporte reporte;

    public Calculadora() {

    }

    private void setReporte(Reporte reporte) {
        this.reporte = reporte;
    }

    //---------------------------------------------------------------------------
    //-------------------------------- CÁLCULOS ---------------------------------
    //---------------------------------------------------------------------------

    private boolean esDelMismoAño(final int pAnio) {
        final int fecha_inicio = reporte.darAnioFecha(reporte.getFecha_inicio());
        final int fecha_fin = reporte.darAnioFecha(reporte.getFecha_finalizacion());
        return pAnio == fecha_fin || pAnio == fecha_inicio;
    }

    public Map<String, Object> darInformes(final int pAnio, final int pSemana, final List<Reporte> pReportes) {
        final Calculo calculo = new Calculo();
        calculo.setSemana(pSemana);
        pReportes.stream().forEach(rp -> {
            setReporte(rp);
            if (esDelMismoAño(pAnio)) {
                calculo.setInicio(rp.getFecha_inicio());
                calculo.setFin(rp.getFecha_finalizacion());
                calculo.calcularHoras();
            }
        });
        final Map<String, Object> json = new HashMap<>();
            json.put("normales",calculo.getNormales());
            json.put("nocturnas",calculo.getNocturnas());
            json.put("dominicales",calculo.getDominicales());
            json.put("normales_extra",calculo.getNormalesExtra());
            json.put("nocturnas_extra",calculo.getNocturnasExtra());
            json.put("dominicales_extra",calculo.getDominicaleExtra());
        return json;
    }
}
