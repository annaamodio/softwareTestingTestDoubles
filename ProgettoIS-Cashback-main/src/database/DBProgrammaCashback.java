package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import exceptions.ProgrammaNonTrovato;

public class DBProgrammaCashback {

	private int idProgramma;
	private Date dataInizio;
	private Date dataFine;
	private int minAcquisti;
	private float maxTetto; //se è un float, modificarlo nel CD
	private float percRimborso; //per me dovrebbe essere un float
	private ArrayList<DBIscrizione> iscrizioni;
	
	
	public DBProgrammaCashback() {
		this.iscrizioni = new ArrayList<DBIscrizione>(); 
	}
	
	public DBProgrammaCashback(int idProgramma) throws ProgrammaNonTrovato{
		
		this.idProgramma = idProgramma;
		this.iscrizioni = new ArrayList<DBIscrizione>(); 
		caricaDaDB(); //accedo solo alla tabella programmi
	}
	
	public void caricaDaDB() throws ProgrammaNonTrovato {
		
		String query = "SELECT * FROM programmi WHERE idProgramma='"+this.idProgramma+"';";
		//System.out.println(query); //DEGUB
		
		try {
			
			ResultSet rs = DBConnectionManager.selectQuery(query);
			
			if(rs.next()) { //se ho un risultato
				
				//mi vado a prendere i dati, accedendo tramite il nome dell'attributo-colonna
				this.setDataInizio(rs.getDate("Inizio"));
				this.setDataFine(rs.getDate("Fine"));
				this.setMinAcquisti(rs.getInt("AcquistiMinimi"));
				this.setMaxTetto(rs.getFloat("TettoMassimo"));
				this.setPercRimborso(rs.getInt("PercentualeRimborso"));
			
			}
			
			else {
				throw new ProgrammaNonTrovato("Errore: l'ID: "+ String.valueOf(idProgramma)+" non è corretto");
			}
		
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void caricaIscrizioniProgrammaDaDB() {

		String query = "select * from iscrizioni where programma = '"+this.idProgramma+";" ;
		//System.out.println(query); //stampo query per controllo in fase di DEBUG, poi posso commentare
		
		try {
			
			ResultSet rs = DBConnectionManager.selectQuery(query);
			
			while(rs.next()) {	//while perch� mi aspetto pi� risultati			
							
				//NB: non dimenticare di istanziare l'oggetto Corso
				//altrimenti non potremmo salvare i suoi dati				
				DBIscrizione iscrizione = new DBIscrizione();
				iscrizione.setIdCittadino(rs.getString("IdCittadino"));
				iscrizione.setIban(rs.getString("Iban"));
				iscrizione.setPassword(rs.getString("Password"));
				iscrizione.setRimborsoRicevuto(rs.getFloat("RimborsoRicevuto"));
				
				iscrizione.caricaAcquistiIscrizioneDaDB();
				iscrizione.caricaCarteRegistrateIscrizioneDaDB();
				
				this.iscrizioni.add(iscrizione);
				
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public int SalvaInDB() {
		
		int ret = 0;
		
		String query ="insert into programmi (idProgramma, inizio, fine, aquistiMinimi, tettoMassimo"
				+ "percentualeRimborso values ("+this.idProgramma+",'"+this.dataInizio+"','"+this.dataFine+"',"
				+this.minAcquisti+","+this.maxTetto+","+this.percRimborso+";"; 
		//System.out.println(query);
		
		try {
			ret = DBConnectionManager.updateQuery(query);
			
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ret = -1; //per segnalare l'errore di scrittura
		}
		
		return ret;
	}
	
	public int eliminaDaDB() {
		
		int ret = 0;
		
		String query = "delete from programmi where idProgramma = " + this.idProgramma+";";
		//System.out.println(query);
		try {
			
			ret = DBConnectionManager.updateQuery(query);
			
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ret = -1; //per segnalare l'error
		}
		
		return ret;
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

	public ArrayList<DBIscrizione> getIscrizioni() {
		return iscrizioni;
	}

	public void setIscrizioni(ArrayList<DBIscrizione> iscrizioni) {
		this.iscrizioni = iscrizioni;
	}
	
}

