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

    private final double minutosTrabjados;

    private final double horasTrabajadas;

    private final long datosI[];

    private final long datosF[];

    public Calculo(final Date pFechaI, final Date pFechaF) {
        normales = 0;
        nocturnas = 0;
        dominicales = 0;
        normalesExtra = 0;
        nocturnasExtra = 0;
        dominicaleExtra = 0;
        inicio = pFechaI;
        fin = pFechaF;

        datosI = new long[]{inicio.getMinutes(), inicio.getHours(),
                inicio.getDate(), inicio.getMonth(), inicio.getYear(),
                darNumeroSemana(inicio)
        };
        datosF = new long[]{fin.getMinutes(), fin.getHours(),
                fin.getDate(), fin.getMonth(), fin.getYear(),
                darNumeroSemana(fin)
        };

        int contador = 0;
        while (inicio.getTime() < fin.getTime()) {
            inicio.setMinutes(inicio.getMinutes() + 1);
            contador++;
        }
        inicio.setMinutes(inicio.getMinutes() - contador);
        minutosTrabjados = contador;
        horasTrabajadas = contador / 60;
    }

    private int darNumeroSemana(final Date pFecha) {
        final Calendar calendar = new GregorianCalendar();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.setMinimalDaysInFirstWeek(7);
        calendar.setTime(pFecha);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    public double[] darHoras() {
        return new double[]{normales, nocturnas, dominicales, normalesExtra, nocturnasExtra, dominicaleExtra};
    }

    private int darTipoJornada(final Date pFecha) {
        if (pFecha.getDay() == 0) {
            return -1;
        }
        if (pFecha.getHours() > HORA_MINIMA && pFecha.getHours() < HORA_MAXIMA) {
            return 0;
        } else {
            return 1;
        }
    }

    private void calcularHoras() {
        while (inicio.getTime() < fin.getTime()) {
            final int tipo = darTipoJornada(inicio);
            if (normales + nocturnas + dominicales > 2880) {
                if (tipo == 0) {
                    normalesExtra += 1;
                } else if (tipo == 1) {
                    nocturnasExtra += 1;
                } else if (tipo == -1) {
                    dominicaleExtra += 1;
                }
            }
            else if (tipo == 0) {
                normales += 1;
            } else if (tipo == 1) {
                nocturnas += 1;
            } else if (tipo == -1) {
                dominicales += 1;
            }
            inicio.setMinutes(inicio.getMinutes() + 1);
        }
    }

    public static void main(String[] args) {
        final Date fechaI = new GregorianCalendar(2022, 1, 18, 19, 0).getTime();
        final Date fechaF = new GregorianCalendar(2022, 1, 22, 2, 15).getTime();
        final Calculo calculo = new Calculo(fechaI, fechaF);
        System.out.println(calculo.minutosTrabjados);
        System.out.println(calculo.horasTrabajadas);
        System.out.println("----------------");
        System.out.println(calculo.inicio);
        System.out.println(calculo.fin);
        System.out.println("----------------");
        System.out.println(calculo.darNumeroSemana(calculo.inicio));
        System.out.println(calculo.darNumeroSemana(calculo.fin));
        System.out.println("----------------");

        calculo.calcularHoras();
        System.out.println("normales: " + calculo.normales/60);
        System.out.println("nocturnas: " + calculo.nocturnas/60);
        System.out.println("dominicales: " + calculo.dominicales/60);
        System.out.println("normales extra: " + calculo.nocturnasExtra/60);
        System.out.println("nocturnas extra: " + calculo.nocturnasExtra/60);
        System.out.println("dominicales extra: " + calculo.dominicaleExtra/60);
    }
}
