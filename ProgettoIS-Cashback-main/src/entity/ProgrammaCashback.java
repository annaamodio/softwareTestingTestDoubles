package entity;

import java.util.ArrayList;
import java.sql.Date;

import database.DBProgrammaCashback;
import exceptions.*;

public class ProgrammaCashback {
	
	private int idProgramma;
	private Date dataInizio;
	private Date dataFine;
	private int minAcquisti;
	private float maxTetto; //se è un float, modificarlo nel CD
	private float percRimborso;
	private ArrayList<Iscrizione> iscrizioni;

	public ProgrammaCashback() {
		iscrizioni = new ArrayList<Iscrizione>();
	}

	public ProgrammaCashback(int idProgramma, Date dataInizio, Date dataFine, int minAcquisti, float maxTetto,
			int percRimborso, ArrayList<Iscrizione> iscrizioni) {

		iscrizioni = new ArrayList<Iscrizione>();

		this.idProgramma = idProgramma;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.minAcquisti = minAcquisti;
		this.maxTetto = maxTetto;
		this.percRimborso = percRimborso;
		this.iscrizioni = iscrizioni;
	}
	
	public ProgrammaCashback(int idProgramma)  throws ProgrammaNonTrovato {
		iscrizioni = new ArrayList<Iscrizione>();

		this.idProgramma=idProgramma;
		
			DBProgrammaCashback programma = new DBProgrammaCashback(idProgramma);
		this.dataInizio=programma.getDataInizio();
		this.dataFine=programma.getDataFine();
		this.minAcquisti=programma.getMinAcquisti();
		this.maxTetto=programma.getMaxTetto();
		this.percRimborso=programma.getPercRimborso();
		
	}

	public ProgrammaCashback(DBProgrammaCashback programma ) throws ProgrammaNonTrovato{
		iscrizioni = new ArrayList<Iscrizione>();

		this.idProgramma=programma.getIdProgramma();
		this.dataInizio=programma.getDataInizio();
		this.dataFine=programma.getDataFine();
		this.minAcquisti=programma.getMinAcquisti();
		this.maxTetto=programma.getMaxTetto();
		this.percRimborso=programma.getPercRimborso();
		
	}
	
	public void caricaIscrizioni(DBProgrammaCashback programma) throws IscrizioneNonTrovata {
		
		//se si volessero caricare dal DB le iscrizioni relative a questo programma
		
		for(int i =0; i<programma.getIscrizioni().size(); i++) {
			
			Iscrizione iscrizione = new Iscrizione(programma.getIscrizioni().get(i),this);
			programma.getIscrizioni().get(i).caricaAcquistiIscrizioneDaDB();
			programma.getIscrizioni().get(i).caricaCarteRegistrateIscrizioneDaDB();
			this.iscrizioni.add(iscrizione);
			
			}
	}
	
	public void creaIscrizione() {}

	final String IdCittadinoRegEx = "^[a-zA-Z0-9]{15}$";
	final String PasswordRegEx = "^[a-zA-Z0-9]{10}$";


	public static boolean validate(String toValidate, String regEx) {
		return toValidate != null && toValidate.matches(regEx);
	}

	private Iscrizione verificaDati(String idCittadino, String password) 
			throws IscrizioneNonTrovata, PasswordErrata,ProgrammaNonTerminato, MinAcquistiNonRaggiunto, IllegalArgumentException{
		if(!validate(idCittadino,IdCittadinoRegEx)){ 
			throw new IllegalArgumentException("L'id del cittadino deve essere una stringa alfanumerica di 15 cifre.");
		}
		if (!validate(password,PasswordRegEx)){
			throw new IllegalArgumentException("La password deve essere una stringa alfanumerica di 10 cifre.");
		}
		
		Iscrizione daVerificare = new Iscrizione(idCittadino);
		
		if(Integer.compare(idProgramma, daVerificare.getProgramma().getIdProgramma())!=0) {
			throw new IscrizioneNonTrovata("i dati inseriti per l'iscrizione non sono relativi a questo programma.");
		}
		
		if(password.compareTo(daVerificare.getPassword())!=0) {
			throw new PasswordErrata("la password inserita non corrisponde al Cittadino con id " + idCittadino+".");
		}
		Date currentDate = new Date(System.currentTimeMillis());
		if(dataFine.after(currentDate)){
			throw new ProgrammaNonTerminato("il programma non è ancora terminato.");
		}
		if(daVerificare.getAcquistiRegistrati().size()<minAcquisti) {
			throw new MinAcquistiNonRaggiunto("Per richiedere il rimborso è necessario aver effettuato almeno "+
					minAcquisti + " acquisti.");
		}
		if(daVerificare.getRimborsoRicevuto()!=0) {
			throw new IscrizioneNonTrovata("Il rimborso per questa iscrizione è gia stato accreditato.");
		}
		
		return daVerificare;

	}


	private float assegnaRimborso(Iscrizione iscrizione) {
		float importo = iscrizione.calcolaImporto();
		if (importo > maxTetto) {
			iscrizione.salvaRimborso(maxTetto);
			return maxTetto;
		}
		else{
			iscrizione.salvaRimborso(importo);
			return importo;
		}
	
	}

	
	public float creaRimborso(String idCittadino, String password) 
			throws IscrizioneNonTrovata,PasswordErrata,ProgrammaNonTerminato,MinAcquistiNonRaggiunto, IllegalArgumentException{
		
		Iscrizione iscrizione = verificaDati(idCittadino,password); 
		return assegnaRimborso(iscrizione);
	}
	
	public int getIdProgramma() {
		return idProgramma;
	}

	public void setIdProgramma(int idProgramma) {
		this.idProgramma = idProgramma;
	}

	public Date getDataInizio() {
		return dataInizio;
	}

	public void setDataInizio(Date dataInizio) {
		this.dataInizio = dataInizio;
	}

	public Date getDataFine() {
		return dataFine;
	}

	public void setDataFine(Date dataFine) {
		this.dataFine = dataFine;
	}

	public int getMinAcquisti() {
		return minAcquisti;
	}

	public void setMinAcquisti(int minAcquisti) {
		this.minAcquisti = minAcquisti;
	}

	public float getMaxTetto() {
		return maxTetto;
	}

	public void setMaxTetto(float maxTetto) {
		this.maxTetto = maxTetto;
	}

	public float getPercRimborso() {
		return percRimborso;
	}

	public void setPercRimborso(float percRimborso) {
		this.percRimborso = percRimborso;
	}

	public ArrayList<Iscrizione> getIscrizioni() {
		return iscrizioni;
	}

	public void setIscrizioni(ArrayList<Iscrizione> iscrizioni) {
		this.iscrizioni = iscrizioni;
	}

	@Override
	public String toString() {
		return "ProgrammaCashback [idProgramma=" + idProgramma + ", dataInizio=" + dataInizio + ", dataFine=" + dataFine
				+ ", minAcquisti=" + minAcquisti + ", maxTetto=" + maxTetto + ", percRimborso=" + percRimborso * 100
				+ "%, iscrizioni=" + iscrizioni + "]";
	}

	
}
