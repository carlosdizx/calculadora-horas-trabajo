package ias.data;

import ias.enums.ListaServicios;
import ias.models.Reporte;

import java.util.*;

public class DataService {
    public final static Reporte REPORTE_0 =
            new Reporte(1, "1082749257", ListaServicios.A, new Date(), new Date());

    public final static Reporte REPORTE_1 = null;


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


    public final static List<Reporte> REPORTES_2 = Arrays.asList(
            new Reporte(1, "1082749257", ListaServicios.A,
                    new GregorianCalendar(2022,1,1,0,0).getTime(),
                    new GregorianCalendar(2022,1,2,0,0).getTime()),
            new Reporte(2, "1082749257", ListaServicios.C,
                    new GregorianCalendar(2022,1,1,0,0).getTime(),
                    new GregorianCalendar(2022,1,2,0,0).getTime()),
            new Reporte(3, "1082749257", ListaServicios.D,
                    new GregorianCalendar(2022,1,1,0,0).getTime(),
                    new GregorianCalendar(2022,1,2,0,0).getTime()),
            new Reporte(4, "1082749257", ListaServicios.B,
                    new GregorianCalendar(2022,1,1,0,0).getTime(),
                    new GregorianCalendar(2022,1,2,0,0).getTime()),
            new Reporte(5, "87570236", ListaServicios.B,
                    new GregorianCalendar(2022,1,1,0,0).getTime(),
                    new GregorianCalendar(2022,1,2,0,0).getTime()),
            new Reporte(6, "87570236", ListaServicios.D,
                    new GregorianCalendar(2022,1,1,0,0).getTime(),
                    new GregorianCalendar(2022,1,2,0,0).getTime()),
            new Reporte(7, "27156864", ListaServicios.A,
                    new GregorianCalendar(2022,1,1,0,0).getTime(),
                    new GregorianCalendar(2022,1,2,0,0).getTime()),
            new Reporte(8, "87570236", ListaServicios.D,
                    new GregorianCalendar(2022,1,1,0,0).getTime(),
                    new GregorianCalendar(2022,1,2,0,0).getTime()),
            new Reporte(9, "27156864", ListaServicios.C,
                    new GregorianCalendar(2022,1,1,0,0).getTime(),
                    new GregorianCalendar(2022,1,2,0,0).getTime()),
            new Reporte(10, "27156864", ListaServicios.C,
                    new GregorianCalendar(2022,1,1,0,0).getTime(),
                    new GregorianCalendar(2022,1,2,0,0).getTime()),
            new Reporte(11, "87570236", ListaServicios.A,
                    new GregorianCalendar(2022,1,1,0,0).getTime(),
                    new GregorianCalendar(2022,1,2,0,0).getTime()),
            new Reporte(12, "27156864", ListaServicios.A,
                    new GregorianCalendar(2022,1,1,0,0).getTime(),
                    new GregorianCalendar(2022,1,2,0,0).getTime())
    );
}
