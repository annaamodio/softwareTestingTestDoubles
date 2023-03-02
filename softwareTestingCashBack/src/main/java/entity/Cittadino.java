package entity;

import java.util.ArrayList;

import database.DBCittadino;
import exceptions.IscrizioneNonTrovata;

public class Cittadino {

	private String nome;
	private String cognome;
	private String codiceFiscale;
	private String indirizzoMail;
	private ArrayList<Iscrizione> iscrizioni;
	
	public Cittadino() {
		iscrizioni = new ArrayList<Iscrizione>();
	}
	
	public Cittadino(String nome, String cognome, String codiceFiscale, String indirizzoMail,
		ArrayList<Iscrizione> iscrizioni) {
		
		iscrizioni = new ArrayList<Iscrizione>();
		
		this.nome = nome;
		this.cognome = cognome;
		this.codiceFiscale = codiceFiscale;
		this.indirizzoMail = indirizzoMail;
		this.iscrizioni = iscrizioni;
	}
	
	public Cittadino(String codiceFiscale) {
	
		this.codiceFiscale = codiceFiscale;
		iscrizioni = new ArrayList<Iscrizione>();
		
		DBCittadino cittadino = new DBCittadino(codiceFiscale);
		
		this.nome=cittadino.getNome();
		this.cognome=cittadino.getCognome();
		this.indirizzoMail=cittadino.getIndirizzoMail();
		
	}
	
	public Cittadino(DBCittadino cittadino) {
		
		iscrizioni = new ArrayList<Iscrizione>();
		
		this.codiceFiscale = cittadino.getCodiceFiscale();
		this.nome=cittadino.getNome();
		this.cognome=cittadino.getCognome();
		this.indirizzoMail=cittadino.getIndirizzoMail();
		
	}
	
	public void caricaIscrizioni(DBCittadino cittadino) throws IscrizioneNonTrovata { 
		//nel caso in cui si vogliano caricare dal database tutte le iscrizioni relative ad un cittadino
		
		for(int i =0; i<cittadino.getIscrizioni().size(); i++) {
			
		Iscrizione iscrizione = new Iscrizione(cittadino.getIscrizioni().get(i),this);
		cittadino.getIscrizioni().get(i).caricaAcquistiIscrizioneDaDB();
		cittadino.getIscrizioni().get(i).caricaCarteRegistrateIscrizioneDaDB();
		this.iscrizioni.add(iscrizione);
		
		}
	}
	
	
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	public String getIndirizzoMail() {
		return indirizzoMail;
	}
	public void setIndirizzoMail(String indirizzoMail) {
		this.indirizzoMail = indirizzoMail;
	}
	public ArrayList<Iscrizione> getIscrizioni() {
		return iscrizioni;
	}
	public void setIscrizioni(ArrayList<Iscrizione> iscrizioni) {
		this.iscrizioni = iscrizioni;
	}

	@Override
	public String toString() {
		return "Cittadino [nome=" + nome + ", cognome=" + cognome + ", codiceFiscale=" + codiceFiscale
				+ ", indirizzoMail=" + indirizzoMail + ", iscrizioni=" + iscrizioni + "]";
	}

	
	
	
}
