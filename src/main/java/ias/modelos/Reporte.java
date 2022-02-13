package ias.modelos;

import ias.enums.ListaServicios;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "reportes")
public class Reporte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String tecnico;

    @Enumerated(EnumType.STRING)
    private ListaServicios servicio;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha_inicio;

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
}
