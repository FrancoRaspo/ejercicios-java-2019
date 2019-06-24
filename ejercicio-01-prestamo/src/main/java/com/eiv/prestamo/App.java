package com.eiv.prestamo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.eiv.prestamo.metodos.Metodo;
import com.eiv.prestamo.metodos.MetodoFrancesImpl;

public class App {

    private static final Logger LOG = LogManager.getLogger(App.class);

    public static final BigDecimal DIAS = BigDecimal.valueOf(30);
    public static final BigDecimal CIEN = BigDecimal.valueOf(100);
    public static final BigDecimal DIAS_ANIO = BigDecimal.valueOf(365);

    public static void main(String[] args) {
        
        LOG.info("Iniciando aplicacion ...");
        
        App app = new App();
        app.run();
    }

    public void run() {
        
        // PIDO AL USUARIO LA CARGA INICIAL DE DATOS DEL PRESTAMO
        LOG.debug("Pido al usuario los datos del prestamo a calcular ...");
        Optional<PrestamoDatos> prestamoDatosOp = cargarDatos();
        
        prestamoDatosOp.ifPresent(prestamoDatos -> {
            
            // CALCULO LAS CUOTAS DEL PRESTAMO
            LOG.debug("Calculo la tabla de amortizaciones ...");
            List<Cuota> cuotas = calcularCuotas(prestamoDatos);
            
            // MUESTRO EN PANTALLA LAS CUOTAS
            LOG.debug("Muestro por pantalla el resultado ...");
            cuotas.forEach(cuota -> {
                System.out.println(cuota);
            });

        });        
    }
    
    public Optional<PrestamoDatos> cargarDatos() {
        
        try (Scanner scanner = new Scanner(System.in)) {
            
            System.out.println("Ingrese capital del prestamo: ");
            BigDecimal capital = scanner.nextBigDecimal();
            
            System.out.println("Ingrese cantidad de cuotas del prestamo: ");
            Integer cuotas = scanner.nextInt();
            
            System.out.println("Ingrese tasa nominal anual: ");
            BigDecimal tna = scanner.nextBigDecimal();
            
            PrestamoDatos prestamoDatos = new PrestamoDatos(capital, cuotas, tna);
            PrestamoDatos.esValido(prestamoDatos);
            
            return Optional.of(prestamoDatos);
            
        } catch (InputMismatchException e) {
            LOG.error("Los datos se ingresan en un formato incorrecto!");
        } catch (IllegalArgumentException e) {
            LOG.error("Los datos no se pueden validar: " + e.getMessage());
        } catch (Exception e) {
            LOG.error("Error al cargar datos!");
        }
        
        return Optional.empty();
    }
    
    public List<Cuota> calcularCuotas(PrestamoDatos prestamoDatos) {
        
        int nrocuotas = prestamoDatos.getCuotas();
        
        BigDecimal razon = prestamoDatos.getTna()
                .multiply(DIAS)
                .divide(CIEN.multiply(DIAS_ANIO), 6, RoundingMode.HALF_UP);
        
        BigDecimal valorCuota = calculoValorCuota(prestamoDatos);
        BigDecimal saldoCapital = prestamoDatos.getCapital();
        
        LOG.debug("Valor de cuota calculada: " + valorCuota);
        
        List<Cuota> cuotas = new ArrayList<>();
        
        for (int i = 0; i < nrocuotas; i++) {
            
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
    
    public BigDecimal calculoValorCuota(PrestamoDatos prestamoDatos) {
        
        Metodo metodo = new MetodoFrancesImpl();
        return metodo.calculoValorCuota(prestamoDatos);
    }
}
