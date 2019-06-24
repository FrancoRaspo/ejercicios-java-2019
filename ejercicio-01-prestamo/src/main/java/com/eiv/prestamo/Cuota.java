package com.eiv.prestamo;

import java.math.BigDecimal;

public class Cuota {

    private Integer nro;
    private BigDecimal capital;
    private BigDecimal interes;
    private BigDecimal saldo;
    
    public Cuota() {
    }

    public Cuota(Integer nro, BigDecimal capital, BigDecimal interes, BigDecimal saldo) {
        super();
        this.nro = nro;
        this.capital = capital;
        this.interes = interes;
        this.saldo = saldo;
    }

    public Integer getNro() {
        return nro;
    }

    public void setNro(Integer nro) {
        this.nro = nro;
    }

    public BigDecimal getCapital() {
        return capital;
    }

    public void setCapital(BigDecimal capital) {
        this.capital = capital;
    }

    public BigDecimal getInteres() {
        return interes;
    }

    public void setInteres(BigDecimal interes) {
        this.interes = interes;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((nro == null) ? 0 : nro.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Cuota other = (Cuota) obj;
        if (nro == null) {
            if (other.nro != null) {
                return false;
            }
        } else if (!nro.equals(other.nro)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Cuota [nro=" + nro + ", capital=" + capital + ", interes=" + interes 
                + ", saldo=" + saldo + "]";
    }
    
}
