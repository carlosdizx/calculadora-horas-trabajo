package ias.utilities;

import ias.enums.ListaServicios;
import ias.models.Reporte;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    private void setupEscenarioDos() {
        calculadora = new Calculadora();

        int anio = 2022;
        int mes = 1;
        reportes.add(new Reporte(1, "1082749257", ListaServicios.A,
                new GregorianCalendar(anio, mes, 15, 13, 0).getTime(),
                new GregorianCalendar(anio, mes, 16, 1, 0).getTime()));//12 horas

        reportes.add(new Reporte(2, "1082749257", ListaServicios.B,
                new GregorianCalendar(anio, mes, 16, 2, 0).getTime(),
                new GregorianCalendar(anio, mes, 17, 0, 0).getTime()));//22 horas

        reportes.add(new Reporte(2, "1082749257", ListaServicios.C,
                new GregorianCalendar(anio, mes, 19, 17, 0).getTime(),
                new GregorianCalendar(anio, mes, 20, 2, 0).getTime()));//9 horas

        reportes.add(new Reporte(2, "1082749257", ListaServicios.C,
                new GregorianCalendar(anio, mes, 20, 20, 0).getTime(),
                new GregorianCalendar(anio, mes, 21, 4, 0).getTime()));// 8 horas (-4)
    }

    private void setupEscenarioTres() {
        calculadora = new Calculadora();

        int anio = 2022;
        int mes = 1;
        reportes.add(new Reporte(1, "87570236", ListaServicios.A,
                new GregorianCalendar(anio, mes, 21, 7, 0).getTime(),
                new GregorianCalendar(anio, mes, 21, 10, 0).getTime()));//3 horas

        reportes.add(new Reporte(2, "87570236", ListaServicios.B,
                new GregorianCalendar(anio, mes, 21, 13, 0).getTime(),
                new GregorianCalendar(anio, mes, 21, 17, 0).getTime()));//4 horas

        reportes.add(new Reporte(3, "87570236", ListaServicios.C,
                new GregorianCalendar(anio, mes, 21, 17, 30).getTime(),
                new GregorianCalendar(anio, mes, 22, 0, 0).getTime()));//6.5 horas

        reportes.add(new Reporte(4, "87570236", ListaServicios.C,
                new GregorianCalendar(anio, mes, 22, 2, 0).getTime(),
                new GregorianCalendar(anio, mes, 22, 10, 10).getTime()));//8.16 horas


        reportes.add(new Reporte(5, "87570236", ListaServicios.C,
                new GregorianCalendar(anio, mes, 22, 11, 0).getTime(),
                new GregorianCalendar(anio, mes, 22, 23, 0).getTime()));//12 horas

        reportes.add(new Reporte(6, "87570236", ListaServicios.C,
                new GregorianCalendar(anio, mes, 23, 20, 0).getTime(),
                new GregorianCalendar(anio, mes, 24, 0, 15).getTime()));//4.25 horas

        reportes.add(new Reporte(7, "87570236", ListaServicios.C,
                new GregorianCalendar(anio, mes, 25, 15, 30).getTime(),
                new GregorianCalendar(anio, mes, 26, 20, 50).getTime()));//29.33 horas

        reportes.add(new Reporte(8, "87570236", ListaServicios.C,
                new GregorianCalendar(anio, mes, 27, 8, 0).getTime(),
                new GregorianCalendar(anio, mes, 27, 16, 0).getTime()));//8 horas
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

    @Test
    void testTres() {
        setupEscenarioDos();
        Map<String, Object> informes = calculadora.darInformes(2022, 7, reportes);
        AtomicReference<Double> sumatoria = new AtomicReference<>((double) 0);
        informes.forEach((key, value) -> {
            sumatoria.updateAndGet(v -> v + ((double) value));
        });

        assertEquals(47, sumatoria.get(), "La suma de horas no es la esperada");
    }

    @Test
    void testCuatro() {
        setupEscenarioDos();
        Map<String, Object> informes = calculadora.darInformes(2022, 8, reportes);
        AtomicReference<Double> sumatoria = new AtomicReference<>((double) 0);
        informes.forEach((key, value) -> {
            sumatoria.updateAndGet(v -> v + ((double) value));
        });

        assertEquals(4, sumatoria.get(), "La suma de horas no es la esperada");
    }

    @Test
    void testQuinto() {
        setupEscenarioTres();
        Map<String, Object> informes = calculadora.darInformes(2022, 8, reportes);
        AtomicReference<Double> sumatoria = new AtomicReference<>((double) 0);
        informes.forEach((key, value) -> {
            sumatoria.updateAndGet(v -> v + ((double) value));
        });

        assertEquals(Double.parseDouble(informes.get("normales").toString()) >= 26, true, "La suma de horas normales no es la esperada");
        assertEquals(Double.parseDouble(informes.get("nocturnas").toString()) >= 21, true, "La suma de horas normales no es la esperada");
        assertEquals(Double.parseDouble(informes.get("dominicales").toString()) >= 0, true, "La suma de horas normales no es la esperada");
        assertEquals(Double.parseDouble(informes.get("normales_extra").toString()) >= 13, true, "La suma de horas normales no es la esperada");
        assertEquals(Double.parseDouble(informes.get("nocturnas_extra").toString()) >= 6, true, "La suma de horas normales no es la esperada");
        assertEquals(Double.parseDouble(informes.get("dominicales_extra").toString()) >= 8, true, "La suma de horas normales no es la esperada");


        assertEquals(sumatoria.get() >= 75, true, "La suma de horas no es la esperada");
    }
}