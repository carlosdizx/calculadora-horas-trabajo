package ias.models;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;

import static java.time.temporal.ChronoUnit.MINUTES;
import static java.time.temporal.ChronoUnit.HOURS;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.MONTHS;
import static java.time.temporal.ChronoUnit.YEARS;

public class Informe {

    private final static int[] HORAS_NORMALES = new int[]{7, 20};

    private final LocalDateTime fecha_inicio;

    private final LocalDateTime fecha_finalizacion;

    private final Date inicio;

    private final Date finalizacion;

    private final long datos[];

    public Informe(final Date pFechaInicio, final Date pFechaFinalizacion) {
        fecha_inicio = Instant.ofEpochMilli(pFechaInicio.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        fecha_finalizacion = Instant.ofEpochMilli(pFechaFinalizacion.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        inicio = pFechaInicio;
        finalizacion = pFechaFinalizacion;
        final long minute = MINUTES.between(fecha_inicio, fecha_finalizacion);
        final long hour = HOURS.between(fecha_inicio, fecha_finalizacion);
        final long day = DAYS.between(fecha_inicio, fecha_finalizacion);
        final long month = MONTHS.between(fecha_inicio, fecha_finalizacion);
        final long year = YEARS.between(fecha_inicio, fecha_finalizacion);
        datos = new long[]{minute, hour, day, month, year};
    }

    public double diferenciaEnHoras() {
        final double diferencia = finalizacion.getTime() - inicio.getTime();
        final double dias = diferencia / (24 * 60 * 60 * 1000);
        final double horas = (diferencia / (60 * 60 * 1000) - dias * 24);
        final double minutos = ((diferencia / (60 * 1000)) - dias * 24 * 60 - horas * 60);
        final double resultado_horasresultado_horas = ((dias * 24 + horas) + (minutos / 60));
        return resultado_horasresultado_horas;
    }

    public double diferenciaEnDias() {
        final double diferencia = finalizacion.getTime() - inicio.getTime();
        final double dias = diferencia / (24 * 60 * 60 * 1000);
        final double horas = (diferencia / (60 * 60 * 1000) - dias * 24);
        final double minutos = ((diferencia / (60 * 1000)) - dias * 24 * 60 - horas * 60);
        final double resultado_horasresultado_horas = ((dias + horas / 24) + (minutos / 60) / 24);
        return resultado_horasresultado_horas;
    }


    public void darDiferencia() {

        Arrays.stream(datos).forEach((dato) -> {
            System.out.println(dato);
        });
        System.out.println(diferenciaEnHoras());
        System.out.println(diferenciaEnDias());
    }
}
