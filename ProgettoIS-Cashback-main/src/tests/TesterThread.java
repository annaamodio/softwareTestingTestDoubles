package tests;

import exceptions.*;
import javax.swing.JOptionPane;

import boundary.BCittadino;

import exceptions.IscrizioneNonTrovata;
import exceptions.MinAcquistiNonRaggiunto;
import exceptions.PasswordErrata;
import exceptions.ProgrammaNonTerminato;
import exceptions.ProgrammaNonTrovato;

public class TesterThread extends Thread {

	private int index;
	private String idCittadino;
	private String password;
	private String idProgramma;
	


	public TesterThread(int index, String idCittadino, String password, String idProgramma) {
		super();
		this.index = index;
		this.idCittadino = idCittadino;
		this.password = password;
		this.idProgramma = idProgramma;
	}




	public void start() {
	
		try {
			
			BCittadino cittadino = new BCittadino();
			float rimborso=cittadino.richiediRimborso(Integer.parseInt(idProgramma), idCittadino, password);
			System.out.println("Test ["+ index +"] completato. Rimborso accreditato: "+rimborso);

			
			
		}catch(ProgrammaNonTrovato | MinAcquistiNonRaggiunto | PasswordErrata | 
				IscrizioneNonTrovata| ProgrammaNonTerminato | IllegalArgumentException  e1){
			System.out.println("Test ["+ index +"]. Errore: "+ e1.toString());
		}
	}
	
}
