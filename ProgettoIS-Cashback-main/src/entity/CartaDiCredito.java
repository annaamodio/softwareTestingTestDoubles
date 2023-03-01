package entity;

import database.DBCartaDiCredito;

public class CartaDiCredito {

	private String numero;
	private String scadenza;
	
	public CartaDiCredito(String numero, String scadenza) {
		this.numero = numero;
		this.scadenza = scadenza;
	}
	
	public CartaDiCredito(String numero) {
		
		this.numero = numero;
		
		DBCartaDiCredito carta = new DBCartaDiCredito(numero);
		this.scadenza=carta.getScadenza();
	
	}
	
	public CartaDiCredito(DBCartaDiCredito carta) {
		
		this.numero=carta.getNumero();
		this.scadenza=carta.getScadenza();
		
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getScadenza() {
		return scadenza;
	}

	public void setScadenza(String scadenza) {
		this.scadenza = scadenza;
	}

	@Override
	public String toString() {
		return "CartaDiCredito [numero=" + numero + ", scadenza=" + scadenza + "]";
	}

	
}
