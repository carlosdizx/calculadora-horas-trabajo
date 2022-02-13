package ias.modelos;

import ias.enums.ListaServicios;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity(name = "reportes")
public class Reporte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El tecnico no puede estar nulo")
    @NotEmpty(message = "El técnico no puede estar vacio")
    private String tecnico;

    @NotNull(message = "El servicio no puede estar nulo")
    @NotEmpty(message = "El servicio no puede estar vacio")
    @Enumerated(EnumType.STRING)
    private ListaServicios servicio;

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull(message = "La fecha de inicio no puede estar nulo")
    @NotEmpty(message = "La fecha de inicio no puede estar vacio")
    private Date fecha_inicio;

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull(message = "La fecha de finalización no puede estar nulo")
    @NotEmpty(message = "La fecha de finalización no puede estar vacio")
    private Date fecha_fino;

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

    public Date getFecha_fino() {
        return fecha_fino;
    }

    public void setFecha_fino(Date fecha_fino) {
        this.fecha_fino = fecha_fino;
    }
}
