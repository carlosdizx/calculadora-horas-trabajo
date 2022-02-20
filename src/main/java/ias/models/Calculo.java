package ias.models;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Calculo {

    private final static int HORA_MINIMA = 7;

    private final static int HORA_MAXIMA = 20;

    private double normales;

    private double nocturnas;

    private double dominicales;

    private double normalesExtra;

    private double nocturnasExtra;

    private double dominicaleExtra;

    final private Date inicio;

    final private Date fin;

    private final int semana;

    public Calculo(final Date pFechaI, final Date pFechaF, final int pSemana) {
        normales = 0;
        nocturnas = 0;
        dominicales = 0;
        normalesExtra = 0;
        nocturnasExtra = 0;
        dominicaleExtra = 0;
        inicio = pFechaI;
        fin = pFechaF;
        semana = pSemana;

        int contador = 0;
        while (inicio.getTime() < fin.getTime()) {
            inicio.setMinutes(inicio.getMinutes() + 1);
            contador++;
        }
        inicio.setMinutes(inicio.getMinutes() - contador);

        calcularHoras();
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

    private void calcularHoras() {
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
                inicio.setMinutes(inicio.getMinutes() + 1);
            } else {
                return;
            }
        }
    }

    //--------------------------------------------------------------------------------


    public double getNormales() {
        return normales / 60;
    }

    public double getNocturnas() {
        return nocturnas / 60;
    }

    public double getDominicales() {
        return dominicales / 60;
    }

    public double getNormalesExtra() {
        return normalesExtra / 60;
    }

    public double getNocturnasExtra() {
        return nocturnasExtra / 60;
    }

    public double getDominicaleExtra() {
        return dominicaleExtra / 60;
    }

    //-----------------------------------------------------------

    public static void main(String[] args) {
        final Date fechaI = new GregorianCalendar(2022, 1, 20, 7, 0).getTime();
        final Date fechaF = new GregorianCalendar(2022, 1, 22, 2, 15).getTime();
        final Calculo calculo = new Calculo(fechaI, fechaF, 7);

        calculo.calcularHoras();
        System.out.println("normales: " + calculo.normales / 60);
        System.out.println("nocturnas: " + calculo.nocturnas / 60);
        System.out.println("dominicales: " + calculo.dominicales / 60);
        System.out.println("normales extra: " + calculo.nocturnasExtra / 60);
        System.out.println("nocturnas extra: " + calculo.nocturnasExtra / 60);
        System.out.println("dominicales extra: " + calculo.dominicaleExtra / 60);
    }
}
