package ias.exeptions;

public class DineroInsuficienteException extends RuntimeException {
    public DineroInsuficienteException(String mensaje) {
        super(mensaje);
    }
}
