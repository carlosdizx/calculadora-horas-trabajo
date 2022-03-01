package ias.models;

import java.math.BigDecimal;

public class Cuenta {

    private String persona;

    private BigDecimal salgo;

    public Cuenta(String persona, BigDecimal salgo) {
        this.persona = persona;
        this.salgo = salgo;
    }

    public String getPersona() {
        return persona;
    }

    public void setPersona(String persona) {
        this.persona = persona;
    }

    public BigDecimal getSalgo() {
        return salgo;
    }

    public void setSalgo(BigDecimal salgo) {
        this.salgo = salgo;
    }
}
