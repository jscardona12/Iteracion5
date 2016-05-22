package dtm;

import java.io.Serializable;

public class CargaUnificada implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Peso de la carga
	 */
	private double peso;
	/**
	 * Volumen de la carga
	 */
	private double volumen;
	/**
	 * Tipo de la carga
	 */
	private String tipo;
	/**
	 * Valor de la carga
	 */
	private double valor;
	
	
	
	public CargaUnificada(double peso, double volumen, String tipo, double valor) {
		this.peso = peso;
		this.volumen = volumen;
		this.tipo = tipo;
		this.valor = valor;
	}
	
	public double getPeso() {
		return peso;
	}
	public void setPeso(double peso) {
		this.peso = peso;
	}
	public double getVolumen() {
		return volumen;
	}
	public void setVolumen(double volumen) {
		this.volumen = volumen;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	
	
}
