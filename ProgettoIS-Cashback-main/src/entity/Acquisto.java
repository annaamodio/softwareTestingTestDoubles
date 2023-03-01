package entity;

import java.sql.Date;

import database.DBAcquisto;

public class Acquisto {

	private int idAcquisto;
	private Date data;
	private float importo;
	private CartaDiCredito carta;
	
	public Acquisto(int idAcquisto, Date data, float importo, CartaDiCredito carta) {
		this.idAcquisto = idAcquisto;
		this.data = data;
		this.importo = importo;
		this.carta = carta;
	}
	
	public Acquisto(int idAcquisto) {
		
		this.idAcquisto = idAcquisto;
		
		DBAcquisto acquisto = new DBAcquisto(idAcquisto);
		this.data = acquisto.getData();
		this.importo=acquisto.getImporto();
		
		acquisto.caricaCartaAcquistoDaDB();
		caricaCarta(acquisto);
	}
	
	public Acquisto(DBAcquisto acquisto) {
		
		this.idAcquisto=acquisto.getIdAcquisto();
		this.data = acquisto.getData();
		this.importo=acquisto.getImporto();
		acquisto.caricaCartaAcquistoDaDB();
		caricaCarta(acquisto);
		
	}
	
	public void caricaCarta(DBAcquisto acquisto) {
		
		CartaDiCredito carta = new CartaDiCredito(acquisto.getCarta());
		this.setCarta(carta);
		
	}
	
	
	public int getIdAcquisto() {
		return idAcquisto;
	}
	public void setIdAcquisto(int idAcquisto) {
		this.idAcquisto = idAcquisto;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public float getImporto() {
		return importo;
	}
	public void setImporto(float importo) {
		this.importo = importo;
	}
	public CartaDiCredito getCarta() {
		return carta;
	}
	public void setCarta(CartaDiCredito carta) {
		this.carta = carta;
	}

	@Override
	public String toString() {
		return "Acquisto [idAcquisto=" + idAcquisto + ", data=" + data + ", importo=" + importo + ", carta="
				+ carta + "]";
	}
	




}
