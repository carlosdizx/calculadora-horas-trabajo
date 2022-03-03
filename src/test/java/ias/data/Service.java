package ias.data;

import ias.enums.ListaServicios;
import ias.models.Reporte;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Service {
    public final static List<Reporte> REPORTES_0 = Collections.emptyList();

    public final static List<Reporte> REPORTES_1 = Arrays.asList(
            new Reporte(1, "1082749257", ListaServicios.A, new Date(2022, 2, 15, 10, 0), new Date()),
            new Reporte(2, "1082749257", ListaServicios.B, new Date(2022, 2, 15, 10, 0), new Date()),
            new Reporte(3, "1082749257", ListaServicios.D, new Date(2022, 2, 15, 10, 0), new Date()),
            new Reporte(4, "87570236", ListaServicios.A, new Date(2022, 2, 15, 10, 0), new Date()),
            new Reporte(5, "87570236", ListaServicios.D, new Date(2022, 2, 15, 10, 0), new Date()),
            new Reporte(6, "27156864", ListaServicios.C, new Date(2022, 2, 15, 10, 0), new Date()),
            new Reporte(7, "27156864", ListaServicios.C, new Date(2022, 2, 15, 10, 0), new Date()),
            new Reporte(8, "27156864", ListaServicios.A, new Date(2022, 2, 15, 10, 0), new Date())
    );
}
