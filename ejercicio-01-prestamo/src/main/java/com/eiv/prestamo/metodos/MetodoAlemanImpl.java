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

public class MetodoAlemanImpl implements Metodo {
	private static final Logger LOG = LogManager.getLogger(MetodoAlemanImpl.class);
	
	@Override
	public BigDecimal calculoValorCuota(PrestamoDatos prestamoDatos) {

		BigDecimal capital = prestamoDatos.getCapital()
				.divide(BigDecimal.valueOf(prestamoDatos.getCuotas()), RoundingMode.HALF_UP);
		
		
		return   capital;
		
	}

public List<Cuota> calcularCuotas(PrestamoDatos prestamoDatos) {
        
        int nrocuotas = prestamoDatos.getCuotas();
        
        
        BigDecimal valorCuota = calculoValorCuota(prestamoDatos);
        
        BigDecimal saldoCapital = prestamoDatos.getCapital();
        
        LOG.debug("Valor aleman de cuota calculada: " + valorCuota);
        
        List<Cuota> cuotas = new ArrayList<>();
        
        for(int i = 0; i < nrocuotas; i++) {
            
            BigDecimal interes = saldoCapital
            		.multiply(prestamoDatos.getTna())
            		.multiply(App.DIAS)
            		.divide(App.CIEN, RoundingMode.HALF_UP).divide(App.DIAS_ANIO, RoundingMode.HALF_UP)
                    .setScale(2, RoundingMode.HALF_UP);
            
            BigDecimal capitalCuota = valorCuota;
            
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
