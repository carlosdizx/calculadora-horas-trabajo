package ias.models;

import java.time.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static java.time.temporal.ChronoUnit.MINUTES;
import static java.time.temporal.ChronoUnit.HOURS;

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

    public double darTotalHorasTrabajadasPorReporte() {
        double diferencia = reporte.getFecha_finalizacion().getTime() - reporte.getFecha_inicio().getTime();
        double dias = diferencia / (24 * 60 * 60 * 1000);
        double horas = (diferencia / (60 * 60 * 1000) - dias * 24);
        double minutos = ((diferencia / (60 * 1000)) - dias * 24 * 60 - horas * 60);
        return ((dias * 24 + horas) + (minutos / 60));
    }

    private boolean esTrabajoEnDiasNormales(final LocalDateTime pFecha) {
        return pFecha.getDayOfWeek() == DayOfWeek.MONDAY || pFecha.getDayOfWeek() == DayOfWeek.TUESDAY ||
                pFecha.getDayOfWeek() == DayOfWeek.WEDNESDAY || pFecha.getDayOfWeek() == DayOfWeek.THURSDAY ||
                pFecha.getDayOfWeek() == DayOfWeek.FRIDAY || pFecha.getDayOfWeek() == DayOfWeek.SATURDAY;
    }

    public String darDetallesReporte(final boolean horasExtra) {
        final String msg;
        final LocalDateTime inicio = Instant.ofEpochMilli(reporte.getFecha_inicio().getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        final LocalDateTime fin = Instant.ofEpochMilli(reporte.getFecha_finalizacion().getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        final String tiempo = HOURS.between(inicio, fin) + " horas con " + MINUTES.between(inicio, fin) + " minutos";
        final String horas = (HOURS.between(inicio, fin) + (double) MINUTES.between(inicio, fin) / 60) + " horas";
        final LocalTime tiempoMin = LocalTime.of(07, 00, 00, 00);
        final LocalTime tiempoMax = LocalTime.of(20, 00, 00, 00);
        //System.out.println(HOURS.between(tiempoMin, inicio) + "~" + HOURS.between(tiempoMax, fin));
        if (esTrabajoEnDiasNormales(inicio) && esTrabajoEnDiasNormales(fin)) {
            //----------------------------------- Días normales-----------------------------------
            System.out.println(inicio.getHour() + "~" + fin.getHour() + "~" + (inicio.getHour() >= HORAS_NORMALES[0]) + "~" + (fin.getHour() >= HORAS_NORMALES[1]));
            if (inicio.getHour() >= HORAS_NORMALES[0] && fin.getHour() <= HORAS_NORMALES[1]) {
                msg = "Trabajó en día normal con horarío normal, en total realizó " + tiempo + " " +
                        "Oséa, " + horas + (horasExtra?"(son horas extra)":"");
            } else {
                if (inicio.getHour() > HORAS_NORMALES[1] && fin.getHour() >= HORAS_NORMALES[1] ||
                        inicio.getHour() < HORAS_NORMALES[1] || fin.getHour() < HORAS_NORMALES[0]) {
                    msg = "Trabajó en día normal con horarío nocturno, en total realizo " + tiempo + " " +
                            "Oséa, " + horas + (horasExtra?"(son horas nocturnas extra)":"");
                } else {
                    msg = "";
                }
            }
        } else {
            msg = "Trabajó en horarío dominical, en total realizo " + tiempo + " " +
                    "Oséa, " + horas + (horasExtra?"(son horas dominicales extra)":"");
        }
        return msg;
    }

    public List darInformes(final int pFecha, final int pSemana, final List<Reporte> pReportes) {
        final List informes = new ArrayList();
        final AtomicReference<Double> horasSemana = new AtomicReference<>((double) 0);
        final List mensajes = new ArrayList<>();
        pReportes.stream().forEach(rp -> {
            setReporte(rp);
            if (esDelMismoAño(pFecha)) {
                int semana_inicio = darNumeroSemana(reporte.getFecha_inicio());
                int semana_fin = darNumeroSemana(reporte.getFecha_finalizacion());
                if (semana_inicio == semana_fin && pSemana == semana_fin) {
                    horasSemana.updateAndGet(v -> v + darTotalHorasTrabajadasPorReporte());
                    mensajes.add(darDetallesReporte( horasSemana.get() >= 48));
                }
            }
        });
        informes.add("Semana " + pSemana + ", horas acumuladas: " + horasSemana + "");
        informes.add(mensajes);
        return informes;
    }
}
