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
    private long id;

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
}
