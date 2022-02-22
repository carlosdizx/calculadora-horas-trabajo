package ias.models;

import ias.entity.Reporte;

import java.util.*;

public class Calculadora {

    private Reporte reporte;

    public Calculadora() {

    }

    public void setReporte(Reporte reporte) {
        this.reporte = reporte;
    }

    //---------------------------------------------------------------------------
    //-------------------------------- CÁLCULOS ---------------------------------
    //---------------------------------------------------------------------------

    private boolean esDelMismoAño(final int pFecha) {
        final int fecha_inicio = reporte.darAnioFecha(reporte.getFecha_inicio());
        final int fecha_fin = reporte.darAnioFecha(reporte.getFecha_finalizacion());
        return pFecha == fecha_fin && pFecha == fecha_inicio;
    }

    public List darInformes(final int pFecha, final int pSemana, final List<Reporte> pReportes) {
        final List<String> informes = new ArrayList();
        final Calculo calculo = new Calculo();
        calculo.setSemana(pSemana);
        pReportes.stream().forEach(rp -> {
            setReporte(rp);
            if (esDelMismoAño(pFecha)) {
                calculo.setInicio(rp.getFecha_inicio());
                calculo.setFin(rp.getFecha_finalizacion());
                calculo.calcularHoras();
            }
        });
        informes.add("Horas normales trabajadas " + calculo.getNormales());
        informes.add("Horas nocturnas trabajadas " + calculo.getNocturnas());
        informes.add("Horas dominicales trabajadas " + calculo.getDominicales());
        informes.add("Horas normales extra trabajadas " + calculo.getNormalesExtra());
        informes.add("Horas nocturnas extra trabajadas " + calculo.getNocturnasExtra());
        informes.add("Horas dominicales extra trabajadas " + calculo.getDominicaleExtra());
        return informes;
    }
}
