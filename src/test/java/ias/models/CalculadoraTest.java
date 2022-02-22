package ias.models;

import ias.entity.Reporte;
import ias.enums.ListaServicios;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CalculadoraTest {

    private Calculadora calculadora;

    private List<Reporte> reportes = new ArrayList<>();
    ;

    private void setupEscenarioUno() {
        calculadora = new Calculadora();

        reportes.add(new Reporte(1, "1082749257", ListaServicios.A, new GregorianCalendar(2022, 1, 14, 5, 0).getTime(), new GregorianCalendar(2022, 1, 14, 13, 20).getTime()));
        reportes.add(new Reporte(2, "1082749257", ListaServicios.B, new GregorianCalendar(2022, 1, 14, 13, 40).getTime(), new GregorianCalendar(2022, 1, 14, 17, 0).getTime()));
        reportes.add(new Reporte(3, "1082749257", ListaServicios.C, new GregorianCalendar(2022, 1, 14, 19, 00).getTime(), new GregorianCalendar(2022, 1, 15, 2, 0).getTime()));

    }

    @Test
    void testUno() {
        setupEscenarioUno();
        final Map<String, Object> informes = calculadora.darInformes(2022, 7, reportes);

        assertTrue(Double.parseDouble(informes.get("normales").toString()) >= 10.6, "Las horas normales estan mal");
        assertEquals(8, Double.parseDouble(informes.get("nocturnas").toString()), "Las horas nocturnas estan mal");
    }

    @Test
    void testDos() {
        setupEscenarioUno();
        Map<String, Object> informes = calculadora.darInformes(2022, 7, reportes);

        assertTrue(Double.parseDouble(informes.get("normales").toString()) >= 10.6, "Las horas normales estan mal");
        assertEquals(8, Double.parseDouble(informes.get("nocturnas").toString()), "Las horas nocturnas estan mal");

        reportes.add(new Reporte(1, "1082749257", ListaServicios.A, new GregorianCalendar(2022, 1, 15, 10, 50).getTime(), new GregorianCalendar(2022, 1, 15, 18, 0).getTime()));
        reportes.add(new Reporte(2, "1082749257", ListaServicios.B, new GregorianCalendar(2022, 1, 15, 18, 0).getTime(), new GregorianCalendar(2022, 1, 14, 20, 0).getTime()));
        reportes.add(new Reporte(3, "1082749257", ListaServicios.C, new GregorianCalendar(2022, 1, 20, 10, 00).getTime(), new GregorianCalendar(2022, 1, 20, 18, 0).getTime()));

        setupEscenarioUno();
        informes = calculadora.darInformes(2022, 7, reportes);

        assertTrue(Double.parseDouble(informes.get("normales").toString()) >= 17, "Las horas normales estan mal ");
        assertEquals(8, Double.parseDouble(informes.get("dominicales").toString()), "Las horas dominicales estan mal");
    }
}