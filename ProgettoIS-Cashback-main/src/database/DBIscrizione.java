package database;

import java.sql.ResultSet;

import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;

import exceptions.IscrizioneNonTrovata;

public class DBIscrizione {
	
	private String idCittadino;
	private String password;
	private String iban;
	private float rimborsoRicevuto;
	private ArrayList<DBAcquisto> acquistiRegistrati;
	private ArrayList<DBCartaDiCredito> carteRegistrate;
	private DBCittadino cittadino;
	private DBProgrammaCashback programma;
	
	public DBIscrizione() {
		acquistiRegistrati = new ArrayList<DBAcquisto>();
		carteRegistrate = new ArrayList<DBCartaDiCredito>();
	}
	
	public DBIscrizione(String idCittadino) throws IscrizioneNonTrovata{
		
		this.idCittadino = idCittadino;
		this.acquistiRegistrati= new ArrayList<DBAcquisto>(); 
		this.carteRegistrate = new ArrayList<DBCartaDiCredito>();
		caricaDaDB(); //accedo solo alla tabella programmi
	}

	
	public void caricaDaDB() throws IscrizioneNonTrovata{
		
		String query = "SELECT * FROM iscrizioni WHERE idCittadino='"+this.idCittadino+"';";
		//System.out.println(query); //DEGUB
		
		try {
			
			ResultSet rs = DBConnectionManager.selectQuery(query);
			
			if(rs.next()) { //se ho un risultato
				
				//mi vado a prendere i dati, accedendo tramite il nome dell'attributo-colonna
				this.setIdCittadino(rs.getString("IdCittadino"));
				this.setRimborsoRicevuto(rs.getFloat("RimborsoRicevuto"));
				this.setIban(rs.getString("Iban"));
				this.setPassword(rs.getString("Password"));
							
			}
			else {
				throw new IscrizioneNonTrovata("Errore: l'ID: "+ String.valueOf(idCittadino)+"Non è corretto");
			}
		
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void caricaAcquistiIscrizioneDaDB() {
		
		String query = "select * from acquisti where iscrizione = '"+this.idCittadino+"';" ;
		//System.out.println(query); //stampo query per controllo in fase di DEBUG, poi posso commentare
		
		try {
			
			ResultSet rs = DBConnectionManager.selectQuery(query);
			
			while(rs.next()) {	//while perch� mi aspetto pi� risultati			
										
				DBAcquisto acquisto = new DBAcquisto();
				acquisto.setIdAcquisto(rs.getInt("idAcquisto"));
				acquisto.setImporto(rs.getFloat("Importo"));
				acquisto.setData(rs.getDate("Data"));
				
				acquisto.caricaCartaAcquistoDaDB();
				
				this.acquistiRegistrati.add(acquisto);
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
	}
	


	public void caricaCarteRegistrateIscrizioneDaDB() {

		
		String query =  "select * from carteDiCredito where iscrizione ='"+this.idCittadino+"';";
		//System.out.println(query); //stampo query per controllo in fase di DEBUG, poi posso commentare
		
		try {
			
			ResultSet rs = DBConnectionManager.selectQuery(query);
			
			while(rs.next()) {				
				DBCartaDiCredito carta = new DBCartaDiCredito();
				carta.setNumero(rs.getString("Numero"));
				carta.setScadenza(rs.getString("Scadenza"));
				
				this.carteRegistrate.add(carta);
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void caricaProgrammaIscrizioneDaDB() {
		
		String query = "select * from programmi where idProgramma in (select programma "
				+ "from iscrizioni where idCittadino ='"+this.idCittadino+"');";
		//System.out.println(query); //stampo query per controllo in fase di DEBUG, poi posso commentare
		
		try {
			
			ResultSet rs = DBConnectionManager.selectQuery(query);
			
			if(rs.next()){
				
				DBProgrammaCashback programma = new DBProgrammaCashback();
				programma.setIdProgramma(rs.getInt("idProgramma"));
				programma.setDataInizio(rs.getDate("Inizio"));
				programma.setDataFine(rs.getDate("Fine"));
				programma.setMinAcquisti(rs.getInt("AcquistiMinimi"));
				programma.setMaxTetto(rs.getFloat("TettoMassimo"));
				programma.setPercRimborso(rs.getFloat("PercentualeRimborso"));
				
				this.setProgramma(programma);
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	

	public void caricaCittadinoIscrizioneDaDB() {
		
		String query ="select * from cittadini where codiceFiscale in (select cittadino from iscrizioni where idCittadino ='"+this.idCittadino+"');";
		//System.out.println(query); //stampo query per controllo in fase di DEBUG, poi posso commentare
		
		try {
			
			ResultSet rs = DBConnectionManager.selectQuery(query);
			
			if(rs.next()){
				
				DBCittadino cittadino = new DBCittadino();
				cittadino.setCodiceFiscale(rs.getString("CodiceFiscale"));
				cittadino.setNome(rs.getString("Nome"));
				cittadino.setCognome(rs.getString("cognome"));
				cittadino.setIndirizzoMail(rs.getString("IndirizzoMail"));
			
				this.setCittadino(cittadino);
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	public int SalvaInDB() {
		
		int ret = 0;
		
		String query ="insert into iscrizioni (idCittadino, Password, Iban, ProgrammaCashback, Cittadino) values ("
		 +this.idCittadino+",'"+this.password+"','"+this.iban+"',"+this.programma.getIdProgramma()+",'"
				+this.cittadino.getCodiceFiscale()+"');"; 
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
	
	public int aggiornaRimborsoInDB() {
	
		int ret = 0;
		
		String query ="update iscrizioni set rimborsoRicevuto = "+this.rimborsoRicevuto+" where idCittadino = '"+this.idCittadino+"';";
		//System.out.println(query);
		try {
			ret = DBConnectionManager.updateQuery(query);
			
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			ret = -1; //per segnalare l'errore di scrittura
		}
		
		return ret;
	}
		
	public int eliminaDaDB() {
		
		int ret = 0;
		
		String query = "delete from iscrizioni where idCittadino = " + this.idCittadino+";";
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

	
	public String getIdCittadino() {
		return idCittadino;
	}
	public void setIdCittadino(String idCittadino) {
		this.idCittadino = idCittadino;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getIban() {
		return iban;
	}
	public void setIban(String iban) {
		this.iban = iban;
	}
	public float getRimborsoRicevuto() {
		return rimborsoRicevuto;
	}
	public void setRimborsoRicevuto(float rimborsoRicevuto) {
		this.rimborsoRicevuto = rimborsoRicevuto;
	}
	public ArrayList<DBAcquisto> getAcquistiRegistrati() {
		return acquistiRegistrati;
	}
	public void setAcquistiRegistrati(ArrayList<DBAcquisto> acquistiRegistrati) {
		this.acquistiRegistrati = acquistiRegistrati;
	}
	public ArrayList<DBCartaDiCredito> getCarteRegistrate() {
		return carteRegistrate;
	}
	public void setCarteRegistrate(ArrayList<DBCartaDiCredito> carteRegistrate) {
		this.carteRegistrate = carteRegistrate;
	}
	public DBCittadino getCittadino() {
		return cittadino;
	}
	public void setCittadino(DBCittadino cittadino) {
		this.cittadino = cittadino;
	}
	public DBProgrammaCashback getProgramma() {
		return programma;
	}
	public void setProgramma(DBProgrammaCashback programma) {
		this.programma = programma;
	}

}
