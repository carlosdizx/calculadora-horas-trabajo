package ias.models;

import ias.enums.ListaServicios;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

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
    @Enumerated(EnumType.STRING)
    private ListaServicios servicio;

    @Column(nullable = false)
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha_inicio;

    @Column(nullable = false)
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha_finalizacion;

    public Reporte() {
    }

    public Reporte(long id, String tecnico, ListaServicios servicio, Date fecha_inicio, Date fecha_finalizacion) {
        this.id = id;
        this.tecnico = tecnico;
        this.servicio = servicio;
        this.fecha_inicio = fecha_inicio;
        this.fecha_finalizacion = fecha_finalizacion;
    }

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

    public int darAnioFecha(final Date pFecha) {
        try {
            return Integer.parseInt(pFecha.toString().split("-")[0]);
        }
        catch (Exception e){
            return pFecha.getYear()+1900;
        }
    }
}
