package ias.models;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static java.time.temporal.ChronoUnit.HOURS;
import static java.time.temporal.ChronoUnit.MINUTES;

public class Informe {

    private final static int HORA_MINIMA = 7;

    private final static int HORA_MAXIMA = 20;

    private final LocalDateTime fechaI;

    private final LocalDateTime fechaF;

    private final Date inicio;

    private final Date finalizacion;

    private final long datosI[];

    private final long datosF[];

    public Informe(final Date pFechaInicio, final Date pFechaFinalizacion) {
        fechaI = Instant.ofEpochMilli(pFechaInicio.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        fechaF = Instant.ofEpochMilli(pFechaFinalizacion.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        inicio = pFechaInicio;
        finalizacion = pFechaFinalizacion;
        datosI = new long[]{fechaI.getMinute(), fechaI.getHour(), fechaI.getDayOfMonth(), fechaI.getMonthValue(), fechaI.getYear()};
        datosF = new long[]{fechaF.getMinute(), fechaF.getHour(), fechaF.getDayOfMonth(), fechaF.getMonthValue(), fechaF.getYear()};
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

    public int esHorarioNormal() {
        if (datosI[2] == datosF[2] && datosI[3] == datosF[3] && datosI[4] == datosF[4]) {
            if (datosI[1] >= HORA_MINIMA && datosF[1] <= HORA_MAXIMA) {
                return 0;
            } else if (datosI[1] < HORA_MINIMA && datosF[1] > HORA_MAXIMA) {
                return 3;
            }
            else if (datosI[1] < HORA_MINIMA) {
                return -2;
            } else if (datosF[1] > HORA_MAXIMA){
                return 2;
            }
        }
        return 1;
    }

    public void asignarHoras() {
        final int resultado = esHorarioNormal();
        if (resultado == 0) {
            final String tiempo = HOURS.between(fechaI, fechaF) + " horas con " + MINUTES.between(fechaI, fechaF) + " minutos";
            final String horas = (HOURS.between(fechaI, fechaF) + (double) MINUTES.between(fechaI, fechaF) / 60) + " horas";
            System.out.println("Trabajó en día normal con horarío normal, en total realizó " + tiempo + ", " +
                    "oséa, " + horas);
        } else if (resultado == 3) {
            System.out.println("Hora de inicio y de salida fallán");
        } else if (resultado == -2) {
            System.out.println("Falla la fecha de inicio " + fechaI);
        } else if (resultado == 2) {
            System.out.println("Falla la fecha de finalizacion " + fechaF);
        } else {
            System.out.println("Anormal");
        }
    }

    public void darDiferencia() {
        asignarHoras();
    }
}
