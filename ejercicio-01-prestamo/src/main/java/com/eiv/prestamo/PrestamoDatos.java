package com.eiv.prestamo;

import java.math.BigDecimal;

import com.eiv.prestamo.utiles.Asserts;

public class PrestamoDatos {

	public static enum SistemaAmortizacionEnum {
		FRANCES, ALEMAN;

		public static SistemaAmortizacionEnum of(Integer value) {
			switch (value) {
				case 2:
					return ALEMAN;
				default:
					return FRANCES;

			}
		}
	}

	private BigDecimal capital;
	private Integer cuotas;
	private BigDecimal tna;
	private SistemaAmortizacionEnum sistema;

	public PrestamoDatos() {
	}

	public PrestamoDatos(BigDecimal capital, Integer cuotas, 
			BigDecimal tna, SistemaAmortizacionEnum sistema) {
		super();
		this.capital = capital;
		this.cuotas = cuotas;
		this.tna = tna;
		this.sistema = sistema;
	}

	public BigDecimal getCapital() {
		return capital;
	}

	public void setCapital(BigDecimal capital) {
		this.capital = capital;
	}

	public Integer getCuotas() {
		return cuotas;
	}

	public void setCuotas(Integer cuotas) {
		this.cuotas = cuotas;
	}

	public BigDecimal getTna() {
		return tna;
	}

	public void setTna(BigDecimal tna) {
		this.tna = tna;
	}

	public SistemaAmortizacionEnum getSistema() {
		return sistema;
	}

	public void setSistema(SistemaAmortizacionEnum sistema) {
		this.sistema = sistema;
	}

	@Override
	public String toString() {
		return "PrestamoDatos [capital=" + capital + ", cuotas=" + cuotas + ", tna=" + tna + ", sistema=" + sistema
				+ "]";
	}

	public static void esValido(PrestamoDatos prestamoDatos) {

		Asserts.notNull(prestamoDatos, "No hay datos para validar!");
		Asserts.notNull(prestamoDatos.getSistema(), "No hay sistema de calculo!");
		Asserts.notNull(prestamoDatos.getCapital(), "El CAPITAL no puede ser nulo!");
		Asserts.notNull(prestamoDatos.getCuotas(), "La cantidad de CUOTAS no puede ser nula!");
		Asserts.notNull(prestamoDatos.getTna(), "La TASA no puede ser nula!");

		Asserts.isPositive(prestamoDatos.getCapital(), "El CAPITAL debe ser un numero positivo!");
		Asserts.isPositive(prestamoDatos.getCuotas(), "Debe haber al menos una cuota!");
		Asserts.isPositive(prestamoDatos.getTna(), "La TASA debe ser un numero positivo!");
	}
}
