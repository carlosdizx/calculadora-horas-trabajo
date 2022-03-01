package ias.utilities;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Calculo implements Serializable {

    private final static int HORA_MINIMA = 7;

    private final static int HORA_MAXIMA = 20;

    private final static int TIPO_DOMINICAL = -1;

    private final static int TIPO_NORMAL = 0;

    private final static int TIPO_NOCTURNO = 1;

    private final static int HORAS_MAXIMAS = 48;

    private double normales;

    private double nocturnas;

    private double dominicales;

    private double normalesExtra;

    private double nocturnasExtra;

    private double dominicaleExtra;

    private Date inicio;

    private Date fin;

    private int semana;

    public Calculo() {
        normales = 0;
        nocturnas = 0;
        dominicales = 0;
        normalesExtra = 0;
        nocturnasExtra = 0;
        dominicaleExtra = 0;
    }

    public void setInicio(final Date pFechaI) {
        this.inicio = pFechaI;
    }

    public void setFin(final Date pFechaI) {
        this.fin = pFechaI;
    }

    public void setSemana(final int pSemana) {
        this.semana = pSemana;
    }

    private int darNumeroSemana(final Date pFecha) {
        final Calendar calendar = new GregorianCalendar();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.setMinimalDaysInFirstWeek(7);
        calendar.setTime(pFecha);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    private int darTipoJornada(final Date pFecha) {
        if (pFecha.getDay() == 0) {
            return -1;
        }
        if (pFecha.getHours() >= HORA_MINIMA && pFecha.getHours() < HORA_MAXIMA) {
            return 0;
        } else {
            return 1;
        }
    }

    public void calcularHoras() {
        while (inicio.getTime() < fin.getTime()) {
            if (darNumeroSemana(inicio) == semana) {
                final int tipo = darTipoJornada(inicio);
                if ((normales + nocturnas + dominicales) > HORAS_MAXIMAS) {
                    if (tipo == TIPO_DOMINICAL) {
                        normalesExtra += 0.0166667;
                    } else if (tipo == TIPO_NORMAL) {
                        nocturnasExtra += 0.0166667;
                    } else if (tipo == TIPO_NOCTURNO) {
                        dominicaleExtra += 0.0166667;
                    }
                } else if (tipo == TIPO_DOMINICAL) {
                    normales += 0.0166667;
                } else if (tipo == TIPO_NORMAL) {
                    nocturnas += 0.0166667;
                } else if (tipo == TIPO_NOCTURNO) {
                    dominicales += 0.0166667;
                }
            }
            inicio.setMinutes(inicio.getMinutes() + 1);
        }
    }

    public double getNormales() {
        return Math.round(normales * 100.0) / 100.0;
    }

    public double getNocturnas() {
        return Math.round(nocturnas * 100.0) / 100.0;
    }

    public double getDominicales() {
        return Math.round(dominicales * 100.0) / 100.0;
    }

    public double getNormalesExtra() {
        return Math.round(normalesExtra * 100.0) / 100.0;
    }

    public double getNocturnasExtra() {
        return Math.round(nocturnasExtra * 100.0) / 100.0;
    }

    public double getDominicaleExtra() {
        return Math.round(dominicaleExtra * 100.0) / 100.0;
    }

}
