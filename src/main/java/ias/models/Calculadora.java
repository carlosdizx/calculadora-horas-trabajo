package ias.models;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class Calculadora {

    private final static int[] HORAS_NORMALES = new int[]{7, 20};

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

    public int darNumeroSemana(final Date pFecha) {
        final Calendar calendar = new GregorianCalendar();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.setMinimalDaysInFirstWeek(7);
        calendar.setTime(pFecha);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    public boolean esDelMismoAño(final int pFecha) {
        final int fecha_inicio = reporte.darAnioFecha(reporte.getFecha_inicio());
        final int fecha_fin = reporte.darAnioFecha(reporte.getFecha_finalizacion());
        return pFecha == fecha_fin && pFecha == fecha_inicio;
    }

    public double darTotalHorasTrabajadas() {
        double diferencia = reporte.getFecha_finalizacion().getTime() - reporte.getFecha_inicio().getTime();
        double dias = diferencia / (24 * 60 * 60 * 1000);
        double horas = (diferencia / (60 * 60 * 1000) - dias * 24);
        double minutos = ((diferencia / (60 * 1000)) - dias * 24 * 60 - horas * 60);

        return ((dias * 24 + horas) + (minutos / 60));
    }

    public double darTotalHorasTrabajadasPorSemana() {
        double diferencia = reporte.getFecha_finalizacion().getTime() - reporte.getFecha_inicio().getTime();
        double dias = diferencia / (24 * 60 * 60 * 1000);
        double horas = (diferencia / (60 * 60 * 1000) - dias * 24);
        double minutos = ((diferencia / (60 * 1000)) - dias * 24 * 60 - horas * 60);
        return ((dias * 24 + horas) + (minutos / 60));
    }

    public void darTotalHorasTrabajadasPorSemanaPorHorario() {
        final long inicio = reporte.getFecha_inicio().getTime();
        final long fin = reporte.getFecha_finalizacion().getTime();
        double diferencia = fin - inicio;
        double dias = diferencia / (24 * 60 * 60 * 1000);
        double horas = (diferencia / (60 * 60 * 1000) - dias * 24);
        double minutos = ((diferencia / (60 * 1000)) - dias * 24 * 60 - horas * 60);
        final double horasTotales = ((dias * 24 + horas) + (minutos / 60));

        final int semana = darNumeroSemana(reporte.getFecha_inicio());
        if (inicio >= HORAS_NORMALES[0] && fin <= HORAS_NORMALES[1]) {
            System.out.println("Reporte de " + reporte.getTecnico() + ", " +
                    " de la semana " + semana + " " +
                    "trabajo en horario normal, en total realizo " + horasTotales + " horas");
        } else {
            System.out.println("Reporte de " + reporte.getTecnico() + ", " +
                    " de la semana " + darNumeroSemana(reporte.getFecha_inicio()) + " " +
                    "trabajo en horario extraordinario, en total realizo " + horasTotales + " horas");
        }
    }

    public List<String> darInformes(final int pFecha, final int pSemana, final List<Reporte> pReportes) {
        final List<String> informes = new ArrayList();
        AtomicReference<Double> horasSemana = new AtomicReference<>((double) 0);
        pReportes.stream().forEach(rp -> {
            setReporte(rp);
            if (esDelMismoAño(pFecha)) {
                int semana_inicio = darNumeroSemana(reporte.getFecha_inicio());
                int semana_fin = darNumeroSemana(reporte.getFecha_finalizacion());
                if (semana_inicio == semana_fin && pSemana == semana_fin) {
                    horasSemana.updateAndGet(v -> v + darTotalHorasTrabajadasPorSemana());
                }
            }
        });
        informes.add("Semana " + pSemana + ", horas acumuladas: " + horasSemana + "");
        return informes;
    }
}
