package ias.models;

import ias.exeptions.DineroInsuficienteException;

import java.math.BigDecimal;

public class Cuenta {

    private String persona;

    private BigDecimal saldo;

    public Cuenta(String persona, BigDecimal salgo) {
        this.persona = persona;
        this.saldo = salgo;
    }

    public String getPersona() {
        return persona;
    }

    public void setPersona(String persona) {
        this.persona = persona;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public void debito(BigDecimal monto) {
        BigDecimal nuevoSaldo = saldo.subtract(monto);
        if (nuevoSaldo.compareTo(BigDecimal.ZERO) < 0) {
            throw new DineroInsuficienteException("Dinero insuficiente");
        }
        saldo = nuevoSaldo;
    }

    public void credito(BigDecimal monto) {
        saldo = saldo.add(monto);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Cuenta)) {
            return false;
        }
        Cuenta c = (Cuenta) obj;
        if (this.persona == null || this.saldo == null) {
            return false;
        }
        return this.persona.equals(c.persona) && this.saldo.equals(c.saldo);
    }
}
