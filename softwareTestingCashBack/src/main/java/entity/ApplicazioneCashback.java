package entity;

import java.util.ArrayList;

import exceptions.*;

public class ApplicazioneCashback {
	
	//ApplicazioneCashback è la classe façade per il nostro package entity
	//inoltre è dichiarata come Singleton, poichè si vuole imporre che nel sistema ce ne sia una sola istanza
	
	private static ApplicazioneCashback instance=null;
	private static ProgrammaCashback progCash;

	private ArrayList<Iscrizione> iscrizioni;
	private ArrayList<Cittadino> cittadini;
	
	protected ApplicazioneCashback() {
		//costruttore
		iscrizioni = new ArrayList<Iscrizione>();
		cittadini = new ArrayList<Cittadino>();
	}
	
	public static ApplicazioneCashback getInstance() {
		
		if(instance==null) {
			instance = new ApplicazioneCashback();
		}
		return instance;
			
	}
	
	public void registraCittadino() {}

	public void registraAcquisti() {}

	/*private ProgrammaCashback ricercaProgramma(int programma, ProgrammaCashback progCashback) throws ProgrammaNonTrovato, IllegalArgumentException{
		
		if( !(String.valueOf(programma).matches("[0-9]{6}$")) || programma<0){ 
			throw new IllegalArgumentException("Un programma deve essere un intero positivo su 6 cifre");	
		}

		if(progCashback == null)
			progCashback = new ProgrammaCashback(programma);

		return progCashback;
	}*/
	
	
	//STUB per il metodo ricercaProgramma
	private ProgrammaCashback ricercaProgramma(int programma, ProgrammaCashback progCashback) throws ProgrammaNonTrovato, IllegalArgumentException{
		
		if(programma != 162022) {
			throw new IllegalArgumentException("Un programma deve essere un intero positivo su 6 cifre");	
		}

		return progCashback;
		
	}
	
	public float richiediRimborso(String idCittadino, String password, int programma, ProgrammaCashback progCashback)
	throws ProgrammaNonTrovato, IscrizioneNonTrovata,PasswordErrata,
			ProgrammaNonTerminato,MinAcquistiNonRaggiunto, IllegalArgumentException {
		ProgrammaCashback programmaCashback = progCashback;
		programmaCashback = ricercaProgramma(programma, programmaCashback);

		return programmaCashback.creaRimborso(idCittadino,password);
	}
	
	
}
