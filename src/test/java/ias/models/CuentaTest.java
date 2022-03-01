package ias.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CuentaTest {

    @Test
    void testNombreCuenta() {
        Cuenta cuenta = new Cuenta("Andres", new BigDecimal("1000.12345"));
        String esperado = "Andres";

        assertEquals(esperado, cuenta.getPersona());
    }

    @Test
    void testSaldoCuenta() {
        Cuenta cuenta = new Cuenta("Carlos", new BigDecimal("1000.12345"));
        assertEquals(1000.12345, cuenta.getSalgo().doubleValue());
        assertFalse(cuenta.getSalgo().compareTo(BigDecimal.ZERO) < 0);
        assertTrue(cuenta.getSalgo().compareTo(BigDecimal.ZERO) >= 0);
    }
}