package ias.models;

import java.time.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static java.time.temporal.ChronoUnit.MINUTES;
import static java.time.temporal.ChronoUnit.HOURS;

public class Calculadora {

    private Reporte reporte;

    public Calculadora(Reporte reporte) {
        this.reporte = reporte;
    }


    public Reporte getReporte() {
        return reporte;
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
        AtomicReference<Double> normales = new AtomicReference<>((double) 0);
        AtomicReference<Double> nocturnas = new AtomicReference<>((double) 0);
        AtomicReference<Double> dominicales = new AtomicReference<>((double) 0);
        AtomicReference<Double> normalesExtra = new AtomicReference<>((double) 0);
        AtomicReference<Double> nocturnasExtra = new AtomicReference<>((double) 0);
        AtomicReference<Double> dominicaleExtra = new AtomicReference<>((double) 0);
        pReportes.stream().forEach(rp -> {
            setReporte(rp);
            if (esDelMismoAño(pFecha)) {
                final Calculo calculo = new Calculo(rp.getFecha_inicio(), rp.getFecha_finalizacion(), pSemana);
                System.out.println(calculo.getNormales());
                System.out.println(calculo.getNocturnas());
                System.out.println(calculo.getDominicales());
                System.out.println(calculo.getNormalesExtra());
                System.out.println(calculo.getNocturnasExtra());
                System.out.println(calculo.getDominicaleExtra());
                normales.updateAndGet(v -> v + calculo.getNormales());
                nocturnas.updateAndGet(v -> v + calculo.getNocturnas());
                dominicales.updateAndGet(v -> v + calculo.getDominicales());
                normalesExtra.updateAndGet(v -> v + calculo.getNormalesExtra());
                nocturnasExtra.updateAndGet(v -> v + calculo.getNocturnasExtra());
                dominicaleExtra.updateAndGet(v -> v + calculo.getDominicaleExtra());
            }
        });
        informes.add("Horas normales trabajadas " + normales);
        informes.add("Horas nocturnas trabajadas " + nocturnas);
        informes.add("Horas dominicales trabajadas " + dominicales);
        informes.add("Horas normales extra trabajadas " + normalesExtra);
        informes.add("Horas nocturnas extra trabajadas " + nocturnasExtra);
        informes.add("Horas dominicales extra trabajadas " + dominicaleExtra);
        return informes;
    }
}
