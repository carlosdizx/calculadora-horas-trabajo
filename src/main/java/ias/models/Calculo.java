package ias.models;

import java.util.Date;

public class Calculo {
    private double horas;

    private double nocturnas;

    private double dominicales;

    private double horasExtra;

    private double nocturnasExtra;

    private double dominicaleExtra;

    final private Date inicio;

    final private Date fin;

    private final double horasTrabajadas;

    public Calculo(final Date pInicio, final Date pFin) {
        horas = 0;
        nocturnas = 0;
        dominicales = 0;
        horasExtra = 0;
        nocturnasExtra = 0;
        dominicaleExtra = 0;
        inicio = pInicio;
        fin = pFin;

        final double diferencia = fin.getTime() - inicio.getTime();
        final double dias = diferencia / (24 * 60 * 60 * 1000);
        final double horas = (diferencia / (60 * 60 * 1000) - dias * 24);
        final double minutos = ((diferencia / (60 * 1000)) - dias * 24 * 60 - horas * 60);
        horasTrabajadas = ((dias * 24 + horas) + (minutos / 60));
    }

    public double[] darHoras() {
        return new double[]{horas, nocturnas, dominicales, horasExtra, nocturnasExtra, dominicaleExtra};
    }

    public double calcularHorasNormales(double pValor) {
        if (pValor == 0) {
            return 0.0;
        }
        return 10;
    }
}
