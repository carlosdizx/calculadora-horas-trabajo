package ias.models;

import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculoTest {

    private Calculo calculo;

    //------------------------------------------------------------------------------------------------------------------
    //------------------------------------------------------Escenarios-----------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------

    private void setupEscenarioUno(int pDia, int pInicio, int pFinal) {
        /**
        Fecha de inicio es del pDia de febrero del 2022, a las pInicio:00 (semana 7)
        Fecha de finalización es del pDia de febrero del 2022, a las pFinal:00 (semana 7)
         */
        final Date fechaI = new GregorianCalendar(2022, 1, pDia, pInicio, 0).getTime();
        final Date fechaF = new GregorianCalendar(2022, 1, pDia, pFinal, 30).getTime();

        calculo = new Calculo();

        calculo.setSemana(7);
        calculo.setInicio(fechaI);
        calculo.setFin(fechaF);
    }

    private void setupEscenarioDos(int pDiaI, int pDiaF, int pInicio, int pFinal) {
        final Date fechaI = new GregorianCalendar(2022, 1, pDiaI, pInicio, 0).getTime();
        final Date fechaF = new GregorianCalendar(2022, 1, pDiaF, pFinal, 0).getTime();

        calculo = new Calculo();

        calculo.setSemana(7);
        calculo.setInicio(fechaI);
        calculo.setFin(fechaF);
    }

    //------------------------------------------------------------------------------------------------------------------
    //------------------------------------------------------Métodos-----------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------

    @Test
    void testMismoDiaNormal() {
        setupEscenarioUno(14, 7, 10);
        calculo.calcularHoras();
        assertEquals(3.5, calculo.getNormales(), "El valor de las horas normales no es el correcto!");
    }

    @Test
    void testMismoDiaNocturno() {
        setupEscenarioUno(14, 2, 6);
        calculo.calcularHoras();
        assertEquals(4.5, calculo.getNocturnas(), "El valor de las horas nocturnas no es el correcto!");
    }

    @Test
    void testMismoDiaDominical() {
        setupEscenarioUno(20, 0, 23);
        calculo.calcularHoras();
        assertEquals(23.5, calculo.getDominicales(), "El valor de las horas dominicales no es el correcto!");
    }

    @Test
    void testMismoDiaCambioHorario() {
        setupEscenarioUno(14, 13, 21);
        calculo.calcularHoras();
        assertEquals(7, calculo.getNormales(), "El valor de las horas normales no es el correcto!");
        assertEquals(1.5, calculo.getNocturnas(), "El valor de las horas nocturnas no es el correcto!");
    }

    @Test
    void testCambioHorario() {
        setupEscenarioDos(19, 20, 16, 5);
        calculo.calcularHoras();
        assertEquals(4, calculo.getNormales(), "El valor de las horas normales no es el correcto!");
        assertEquals(4, calculo.getNocturnas(), "El valor de las horas nocturnas no es el correcto!");
        assertEquals(5, calculo.getDominicales(), "El valor de las horas dominicales no es el correcto!");

        setupEscenarioDos(14, 20, 0, 0);
        calculo.calcularHoras();
        double sumatoria = calculo.getNormales() + calculo.getNocturnas() + calculo.getDominicales();
        assertTrue(sumatoria >= 48, "La suma de las horas no da 48");
        assertTrue(calculo.getNormalesExtra() >= 52, "La suma de las horas debe arrojar 52");
        assertTrue(calculo.getNocturnasExtra() >= 43, "La suma de las horas debe arrojar 43~44");
        assertTrue(calculo.getNocturnasExtra() >= 24, "La suma de las horas debe arrojar 24");
    }
}