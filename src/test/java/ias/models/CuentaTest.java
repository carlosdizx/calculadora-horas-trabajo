package ias.models;

import ias.exeptions.DineroInsuficienteException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CuentaTest {

    @Test
    void testNombreCuenta() {
        Cuenta cuenta = new Cuenta("Andres", new BigDecimal("1000.12345"));
        String esperado = "Andres";
        assertNotNull(cuenta.getPersona(), () -> "La persona no puede ser nula");
        assertEquals(esperado, cuenta.getPersona(), () -> "El nombre de la persona de la cuenta no es el esperado");
        assertTrue(cuenta.getPersona().equals(esperado), () -> "La persona no corresponde a la esperada");
    }

    @Test
    void testSaldoCuenta() {
        Cuenta cuenta = new Cuenta("Carlos", new BigDecimal("1000.12345"));
        assertEquals(1000.12345, cuenta.getSaldo().doubleValue());
        assertNotNull(cuenta.getSaldo());
        assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);
        assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) >= 0);
    }

    @Test
    void testReferenciaCuenta() {
        Cuenta cuenta1 = new Cuenta("Carlos", new BigDecimal("8900.9997"));
        Cuenta cuenta2 = new Cuenta("Carlos", new BigDecimal("8900.9997"));

        //assertNotEquals(cuenta1, cuenta2);
        assertEquals(cuenta1, cuenta2);
    }

    @Test
    void testDebitoCuenta() {
        Cuenta cuenta = new Cuenta("Carlos", new BigDecimal("1000.12345"));
        cuenta.debito(new BigDecimal(100));
        assertNotNull(cuenta.getSaldo());
        assertEquals(900, cuenta.getSaldo().intValue());
        assertEquals("900.12345", cuenta.getSaldo().toPlainString());
    }

    @Test
    void testCreditoCuenta() {
        Cuenta cuenta = new Cuenta("Carlos", new BigDecimal("1000.12345"));
        cuenta.credito(new BigDecimal(100));
        assertNotNull(cuenta.getSaldo());
        assertEquals(1100, cuenta.getSaldo().intValue());
        assertEquals("1100.12345", cuenta.getSaldo().toPlainString());
    }

    @Test
    void testDineroInsuficienteException() {
        Cuenta cuenta = new Cuenta("Carlos", new BigDecimal("1000.12345"));
        Exception exception = assertThrows(DineroInsuficienteException.class, () -> {
            cuenta.debito(new BigDecimal("1500"));
        });
        String actual = exception.getMessage();
        assertEquals("Dinero insuficiente", actual);
    }

    @Test
    void testTransferirDineroCuenta() {
        Cuenta cuenta1 = new Cuenta("Carlos", new BigDecimal("2500"));
        Cuenta cuenta2 = new Cuenta("Ernesto", new BigDecimal("1500.8989"));
        Banco banco = new Banco();
        banco.setNombre("Banco del estado");
        banco.transferir(cuenta2, cuenta1, new BigDecimal("500"));

        assertEquals("1000.8989", cuenta2.getSaldo().toPlainString());
        assertEquals("3000", cuenta1.getSaldo().toPlainString());
    }

    @Test
    void testRelacionBancoCuentas() {
        Cuenta cuenta1 = new Cuenta("Carlos", new BigDecimal("2500"));
        Cuenta cuenta2 = new Cuenta("Ernesto", new BigDecimal("1500.8989"));
        Banco banco = new Banco();

        banco.addCuenta(cuenta1);
        banco.addCuenta(cuenta2);

        banco.setNombre("Banco del estado");
        banco.transferir(cuenta2, cuenta1, new BigDecimal("500"));

        assertAll(
                () -> assertEquals("1000.8989", cuenta2.getSaldo().toPlainString(),
                        () -> "El valor del saldo de la cuenta 2 no es el esperado"),
                () -> assertEquals("3000", cuenta1.getSaldo().toPlainString(),
                        () -> "El valor del saldo de la cuenta 1 no es el esperado"),
                () -> assertEquals(2, banco.getCuentas().size(),
                        () -> "El banco no tiene las cuentas esperadas"),
                () -> assertEquals("Banco del estado", cuenta1.getBanco().getNombre(),
                        () -> "El nombre del banco no es el esperado"),
                () -> assertEquals("Ernesto", banco.getCuentas().stream()
                        .filter(c -> c.getPersona().equals("Ernesto"))
                        .findFirst().get().getPersona(), () -> "El banco no tiene la cuenta de la persona esperada"),
                () -> assertTrue(banco.getCuentas().stream()
                        .filter(c -> c.getPersona().equals("Carlos"))
                        .findFirst().isPresent(), () -> "El banco no tiene la cuenta de la persona esperada"),
                () -> assertTrue(banco.getCuentas().stream()
                        .anyMatch(c -> c.getPersona().equals("Carlos")),
                        () -> "El banco no tiene la cuenta de la persona esperada")
        );
    }
}