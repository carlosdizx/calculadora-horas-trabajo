package ias.models;

import ias.exeptions.DineroInsuficienteException;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CuentaTest {

    Cuenta cuenta;

    @BeforeEach
    void initMetodoCuenta() {
        System.out.println("Inicializando el metodo!");
        cuenta = new Cuenta("Carlos", new BigDecimal("1000.12345"));
    }

    @AfterEach
    void tearDown() {
        System.out.println("Finalizando el metodo!");
    }

    @BeforeAll
    static void beforeAll() {
        System.out.println("---------Inicializando el test---------");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("---------Finalizando el test---------");
    }

    @Test
    @DisplayName("Probando el nombre de la cuenta")
    void testNombreCuenta() {
        String esperado = "Carlos";
        assertNotNull(cuenta.getPersona(), () -> "La persona no puede ser nula");
        assertEquals(esperado, cuenta.getPersona(), () -> "El nombre de la persona de la cuenta no es el esperado");
        assertTrue(cuenta.getPersona().equals(esperado), () -> "La persona no corresponde a la esperada");
    }

    @Test
    @DisplayName("Probando el saldo de la cuenta, valor esperado, no nulo, no negativo  y mayor a 0")
    void testSaldoCuenta() {
        assertEquals(1000.12345, cuenta.getSaldo().doubleValue());
        assertNotNull(cuenta.getSaldo());
        assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);
        assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) >= 0);
    }

    @Test
    @DisplayName("Probando la referencia de instancia para dos cuentas")
    void testReferenciaCuenta() {
        cuenta = new Cuenta("Carlos", new BigDecimal("8900.9997"));
        Cuenta cuenta2 = new Cuenta("Carlos", new BigDecimal("8900.9997"));

        //assertNotEquals(cuenta1, cuenta2);
        assertEquals(cuenta, cuenta2);
    }

    @Test
    @DisplayName("Probando la debitación de un monto a una cuenta, no nulo, esperado en entero y en texto plano")
    void testDebitoCuenta() {
        cuenta.debito(new BigDecimal(100));
        assertNotNull(cuenta.getSaldo());
        assertEquals(900, cuenta.getSaldo().intValue());
        assertEquals("900.12345", cuenta.getSaldo().toPlainString());
    }

    @Test
    @DisplayName("Probando la acreditación de un monto a una cuenta, no nulo, esperado en entero y en texto plano")
    void testCreditoCuenta() {
        cuenta.credito(new BigDecimal(100));
        assertNotNull(cuenta.getSaldo());
        assertEquals(1100, cuenta.getSaldo().intValue());
        assertEquals("1100.12345", cuenta.getSaldo().toPlainString());
    }

    @Test
    @DisplayName("Probando excepción DineroInsuficienteException, falla intencionada, mensaje correcto")
    void testDineroInsuficienteException() {
        Exception exception = assertThrows(DineroInsuficienteException.class, () -> {
            cuenta.debito(new BigDecimal("1500"));
        });
        String actual = exception.getMessage();
        assertEquals("Dinero insuficiente", actual);
    }

    @Test
    @DisplayName("Probando la transferencia de monto para dos cuentas, debitación y acreditación a cada cuenta el " +
            "valor del monto")
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
    @DisplayName("Probando las relaciones entre cuenta y banco, desde la perspectiva de la cuenta hacia el banco y " +
            "del lado contrario con assertAll")
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