package com.eiv.prestamo.metodos;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.eiv.prestamo.App;
import com.eiv.prestamo.Cuota;
import com.eiv.prestamo.PrestamoDatos;

public class MetodoFrancesImpl implements Metodo {
	  
	private static final Logger LOG = LogManager.getLogger(MetodoFrancesImpl.class);
	
	
    @Override
    public BigDecimal calculoValorCuota(PrestamoDatos prestamoDatos) {

        BigDecimal razon = prestamoDatos.getTna()
                .multiply(App.DIAS)
                .divide(App.CIEN.multiply(App.DIAS_ANIO), 6, RoundingMode.HALF_UP);
        
        BigDecimal f = razon.add(BigDecimal.ONE).pow(prestamoDatos.getCuotas());
        
        BigDecimal valorCuota = prestamoDatos.getCapital()
                .multiply(razon)
                .multiply(f)
                .divide(f.subtract(BigDecimal.ONE), 2, RoundingMode.HALF_UP);
        
        return valorCuota;
    }

    public List<Cuota> calcularCuotas(PrestamoDatos prestamoDatos) {
        
        int nrocuotas = prestamoDatos.getCuotas();
        
        BigDecimal razon = prestamoDatos.getTna()
                .multiply(App.DIAS)
                .divide(App.CIEN.multiply(App.DIAS_ANIO), 6, RoundingMode.HALF_UP);
        
        BigDecimal valorCuota = calculoValorCuota(prestamoDatos);
        BigDecimal saldoCapital = prestamoDatos.getCapital();
        
        LOG.debug("Valor frances de cuota calculada: " + valorCuota);
        
        List<Cuota> cuotas = new ArrayList<>();
        
        for(int i = 0; i < nrocuotas; i++) {
            
            BigDecimal interes = saldoCapital.multiply(razon)
                    .setScale(2, RoundingMode.HALF_UP);
            
            BigDecimal capitalCuota = valorCuota.subtract(interes);
            
            saldoCapital = saldoCapital.subtract(capitalCuota);
            
            Cuota cuota = new Cuota(
                    i + 1, 
                    capitalCuota, 
                    interes, 
                    saldoCapital);
            
            cuotas.add(cuota);
            
            LOG.debug("Procesada cuota: " + (i + 1));
        }
        
        return cuotas;
    }
    
}