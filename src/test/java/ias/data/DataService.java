package ias.data;

import ias.enums.ListaServicios;
import ias.models.Reporte;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class DataService {
    public final static List<Reporte> REPORTES_0 = Collections.emptyList();

    public final static List<Reporte> REPORTES_1 = Arrays.asList(
            new Reporte(1, "XXXXXXXX", ListaServicios.A, new Date(), new Date()),
            new Reporte(2, "XXXXXXXX", ListaServicios.B, new Date(), new Date()),
            new Reporte(3, "XXXXXXXX", ListaServicios.D, new Date(), new Date()),
            new Reporte(4, "XXXXXXXX", ListaServicios.A, new Date(), new Date()),
            new Reporte(5, "XXXXXXXX", ListaServicios.D, new Date(), new Date()),
            new Reporte(6, "XXXXXXXX", ListaServicios.C, new Date(), new Date()),
            new Reporte(7, "XXXXXXXX", ListaServicios.C, new Date(), new Date()),
            new Reporte(8, "XXXXXXXX", ListaServicios.A, new Date(), new Date())
    );
}
