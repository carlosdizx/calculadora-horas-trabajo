package ias.modelos;

import ias.enums.ListaServicios;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Entity(name = "reportes")
public class Reporte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty
    @NotNull
    @Size(min = 2, max = 15)
    private String tecnico;

    @Column(nullable = false)
    @NotNull
    private ListaServicios servicio;

    @Column(nullable = false)
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha_inicio;

    @Column(nullable = false)
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha_finalizacion;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTecnico() {
        return tecnico;
    }

    public void setTecnico(String tecnico) {
        this.tecnico = tecnico;
    }

    public ListaServicios getServicio() {
        return servicio;
    }

    public void setServicio(ListaServicios servicio) {
        this.servicio = servicio;
    }

    public Date getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(Date fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public Date getFecha_finalizacion() {
        return fecha_finalizacion;
    }

    public void setFecha_finalizacion(Date fecha_finalizacion) {
        this.fecha_finalizacion = fecha_finalizacion;
    }

    public boolean esRangoValido() {
        if (fecha_finalizacion.compareTo(fecha_inicio) > 0) {
            return true;
        }
        return false;
    }

    public int darNumeroSemana(final Date pFecha)
    {
        final Calendar calendar =  new GregorianCalendar();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set (Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.setMinimalDaysInFirstWeek(7);
        calendar.setTime(pFecha);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    public String horasTrabajadas() {
        long diferencia = fecha_finalizacion.getTime() - fecha_inicio.getTime();
        long dias=diferencia/(24*60*60*1000);
        long horas=(diferencia/(60*60*1000)-dias*24);
        long minutos=((diferencia/(60*1000))-dias*24*60-horas*60);
        String msg = dias + " días " + horas + " horas " + minutos + " minutos";
        System.out.println(msg);
        msg = (dias*24+horas/60)+"";

        return msg;
    }

    @Override
    public String toString() {
        return "Reporte{" +
                "id=" + id +
                ", tecnico='" + tecnico + '\'' +
                ", servicio=" + servicio +
                ", fecha_inicio=" + fecha_inicio +
                ", fecha_finalizacion=" + fecha_finalizacion +
                ", semana_incio=" + darNumeroSemana(fecha_inicio) +
                ", semana_fin=" + darNumeroSemana(fecha_finalizacion) +
                ", horas_trabajadas=" + horasTrabajadas() +
                '}';
    }
}
