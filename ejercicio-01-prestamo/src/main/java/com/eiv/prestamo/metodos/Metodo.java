package com.eiv.prestamo.metodos;

import java.math.BigDecimal;
import java.util.List;

import com.eiv.prestamo.Cuota;
import com.eiv.prestamo.PrestamoDatos;

public interface Metodo {

    public BigDecimal calculoValorCuota(PrestamoDatos prestamoDatos);
    public List<Cuota> calcularCuotas(PrestamoDatos prestamoDatos) ;
}
