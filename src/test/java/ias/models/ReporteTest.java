package ias.models;

import ias.enums.ListaServicios;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;

class ReporteTest {

    private Reporte reporte;

    @BeforeEach
    void setUp() {
        reporte = new Reporte(1L, "1082749257", ListaServicios.A,
                new GregorianCalendar(2022, 2, 10, 7, 0).getTime(),
                new GregorianCalendar(2022, 2, 10, 7, 0).getTime());
    }

    @Nested
    class AtributosReporte {
        @Test
        void testTecnico() {
            assertNotNull(reporte.getTecnico());
            assertEquals("1082749257", reporte.getTecnico());

            reporte.setTecnico("87570236");

            assertNotNull(reporte.getTecnico());
            assertEquals("87570236", reporte.getTecnico());
        }

        @Test
        void testServicio() {
            assertNotNull(reporte.getServicio());
            assertTrue(ListaServicios.A.equals(reporte.getServicio()));

            reporte.setServicio(ListaServicios.D);

            assertNotNull(reporte.getServicio());
            assertTrue(ListaServicios.D.equals(reporte.getServicio()));
        }

        @Test
        void testFechaInicio() {
            Date fecha = reporte.getFecha_inicio();
            assertNotNull(fecha);
            assertEquals(2022, reporte.darAnioFecha(fecha));
            fecha.setYear(fecha.getYear() + 1);
            assertEquals(2023, reporte.darAnioFecha(fecha));
        }

        @Test
        void testFechaFinal() {
            Date fecha = reporte.getFecha_finalizacion();
            assertNotNull(fecha);
            assertEquals(2022, reporte.darAnioFecha(fecha));
            fecha.setYear(fecha.getYear() + 1);
            assertEquals(2023, reporte.darAnioFecha(fecha));
        }
    }


    @Test
    void esRangoValido() {
        assertFalse(reporte.esRangoValido());
        reporte.setFecha_finalizacion(
                new GregorianCalendar(2022, 2, 10, 10, 0).getTime());
        assertTrue(reporte.esRangoValido());
        reporte.setFecha_finalizacion(
                new GregorianCalendar(2022, 2, 10, 5, 0).getTime());
        assertFalse(reporte.esRangoValido());
    }

    @Test
    void darAnioFecha() {
        assertEquals(2022, reporte.darAnioFecha(reporte.getFecha_inicio()));
        assertEquals(2022, reporte.darAnioFecha(reporte.getFecha_finalizacion()));

        reporte.setFecha_inicio(
                new GregorianCalendar(2023, 2, 10, 10, 0).getTime());
        reporte.setFecha_finalizacion(
                new GregorianCalendar(2024, 2, 10, 10, 0).getTime());

        assertEquals(2023, reporte.darAnioFecha(reporte.getFecha_inicio()));
        assertEquals(2024, reporte.darAnioFecha(reporte.getFecha_finalizacion()));
    }
}