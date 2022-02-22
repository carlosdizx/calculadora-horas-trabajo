package ias.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Calculo implements Serializable {

    private final static int HORA_MINIMA = 7;

    private final static int HORA_MAXIMA = 20;

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
                if ((normales + nocturnas + dominicales) > 2880) {
                    if (tipo == 0) {
                        normalesExtra += 1;
                    } else if (tipo == 1) {
                        nocturnasExtra += 1;
                    } else if (tipo == -1) {
                        dominicaleExtra += 1;
                    }
                } else if (tipo == 0) {
                    normales += 1;
                } else if (tipo == 1) {
                    nocturnas += 1;
                } else if (tipo == -1) {
                    dominicales += 1;
                }
            }
            inicio.setMinutes(inicio.getMinutes() + 1);
        }
    }

    public double getNormales() {
        return (normales / 60);
    }

    public double getNocturnas() {
        return (nocturnas / 60);
    }

    public double getDominicales() {
        return (dominicales / 60);
    }

    public double getNormalesExtra() {
        return (normalesExtra / 60);
    }

    public double getNocturnasExtra() {
        return (nocturnasExtra / 60);
    }

    public double getDominicaleExtra() {
        return (dominicaleExtra / 60);
    }

}
