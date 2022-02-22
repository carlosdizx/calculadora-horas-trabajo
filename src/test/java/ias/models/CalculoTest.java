package ias.models;

import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;

class CalculoTest {

    private Calculo calculo;

    private void setupEscenarioUno(int pDia, int pInicio, int pFinal) {
        /*
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
    void testMismoDiaCambioHorario() {
        setupEscenarioUno(14, 13, 21);
        calculo.calcularHoras();
        assertEquals(7, calculo.getNormales(), "El valor de las horas normales no es el correcto!");
        assertEquals(1.5, calculo.getNocturnas(), "El valor de las horas nocturnas no es el correcto!");
    }
}