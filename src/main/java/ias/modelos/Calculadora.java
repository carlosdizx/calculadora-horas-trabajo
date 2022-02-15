package ias.modelos;

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

    public int darHorasTrabajadas() {
        long diferencia = reporte.getFecha_finalizacion().getTime() - reporte.getFecha_inicio().getTime();
        long dias = diferencia / (24 * 60 * 60 * 1000);
        long horas = (diferencia / (60 * 60 * 1000) - dias * 24);
        long minutos = ((diferencia / (60 * 1000)) - dias * 24 * 60 - horas * 60);
        /*
        String msg = dias + " días " + horas + " horas " + minutos + " minutos";
        System.out.println(msg);
         */
        return (int) (dias * 24 + horas / 60);
    }
}