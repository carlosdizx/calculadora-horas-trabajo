package ias.models;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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

    public int darNumeroSemana(final Date pFecha) {
        final Calendar calendar = new GregorianCalendar();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.setMinimalDaysInFirstWeek(7);
        calendar.setTime(pFecha);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    public double darHorasTrabajadas() {
        double diferencia = reporte.getFecha_finalizacion().getTime() - reporte.getFecha_inicio().getTime();
        double dias = diferencia / (24 * 60 * 60 * 1000);
        double horas = (diferencia / (60 * 60 * 1000) - dias * 24);
        double minutos = ((diferencia / (60 * 1000)) - dias * 24 * 60 - horas * 60);

        return ((dias * 24 + horas) + (minutos / 60));
    }

    public double darHorasTrabajadasPorSemana(final int pSemana) {
        double diferencia = reporte.getFecha_finalizacion().getTime() - reporte.getFecha_inicio().getTime();
        double dias = diferencia / (24 * 60 * 60 * 1000);
        double horas = (diferencia / (60 * 60 * 1000) - dias * 24);
        double minutos = ((diferencia / (60 * 1000)) - dias * 24 * 60 - horas * 60);

        int semana_inicio = darNumeroSemana(reporte.getFecha_inicio());
        int semana_fin = darNumeroSemana(reporte.getFecha_finalizacion());

        if (semana_inicio == semana_fin) {
            if (pSemana == semana_fin) {
                return ((dias * 24 + horas) + (minutos / 60));
            }
        }
        return 0;
    }
}
