package ias.utilities;

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

    private void setupEscenario(int pDiaInicial, int pDiaFinal, int pHoraInicial, int pHoraFinal) {
        final Date fechaI = new GregorianCalendar(2022, 1, pDiaInicial, pHoraInicial, 0).getTime();
        final Date fechaF = new GregorianCalendar(2022, 1, pDiaFinal, pHoraFinal, 0).getTime();

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
        setupEscenario(14,14, 7, 10);
        calculo.calcularHoras();
        assertEquals(3, calculo.getNormales(), "El valor de las horas normales no es el correcto!");
    }

    @Test
    void testMismoDiaNocturno() {
        setupEscenario(14,14, 2, 6);
        calculo.calcularHoras();
        assertEquals(4, calculo.getNocturnas(), "El valor de las horas nocturnas no es el correcto!");
    }

    @Test
    void testMismoDiaDominical() {
        setupEscenario(20,20, 0, 23);
        calculo.calcularHoras();
        assertEquals(23, calculo.getDominicales(), "El valor de las horas dominicales no es el correcto!");
    }

    @Test
    void testMismoDiaCambioHorario() {
        setupEscenario(14,14, 13, 21);
        calculo.calcularHoras();
        assertEquals(7, calculo.getNormales(), "El valor de las horas normales no es el correcto!");
        assertEquals(1, calculo.getNocturnas(), "El valor de las horas nocturnas no es el correcto!");
    }

    @Test
    void testCambioHorario() {
        setupEscenario(19, 20, 16, 5);
        calculo.calcularHoras();
        assertEquals(4, calculo.getNormales(), "El valor de las horas normales no es el correcto!");
        assertEquals(4, calculo.getNocturnas(), "El valor de las horas nocturnas no es el correcto!");
        assertEquals(5, calculo.getDominicales(), "El valor de las horas dominicales no es el correcto!");

        setupEscenario(14, 20, 0, 0);
        calculo.calcularHoras();
        double sumatoria = calculo.getNormales() + calculo.getNocturnas() + calculo.getDominicales();
        assertTrue(sumatoria == 48, "La suma de las horas no da 48");
        assertTrue(calculo.getNormales() == 26, "La suma de las horas debe arrojar 26");
        assertTrue(calculo.getNocturnas() == 22, "La suma de las horas debe arrojar 22");
        assertTrue(calculo.getNormalesExtra() == 52, "La suma de las horas debe arrojar 52");
        assertTrue(calculo.getNocturnasExtra() == 44, "La suma de las horas debe arrojar 44");
    }
}