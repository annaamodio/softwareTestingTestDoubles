package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBCittadino {

	private String nome;
	private String cognome;
	private String codiceFiscale;
	private String indirizzoMail;
	private ArrayList<DBIscrizione> iscrizioni;
	
	public DBCittadino() {
		this.iscrizioni=new ArrayList<DBIscrizione>();
	}

	public DBCittadino(String codiceFiscale) {
		this.codiceFiscale=codiceFiscale;
		caricaDaDB();
	}
	
	
	public void caricaDaDB() {
		
		String query = "SELECT * FROM cittadini WHERE codiceFiscale='"+this.codiceFiscale+"';";
		//System.out.println(query); //DEGUB
		
		try {
			
			ResultSet rs = DBConnectionManager.selectQuery(query);
			
			if(rs.next()) { //se ho un risultato
				
				//mi vado a prendere i dati, accedendo tramite il nome dell'attributo-colonna
				this.setNome(rs.getString("Nome"));
				this.setCognome(rs.getString("cognome"));
				this.setIndirizzoMail(rs.getString("IndirizzoMail"));
				
			}
		
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void caricaIscrizioniCittadinoDaDB() {
		
		String query = "select * from iscrizioni where cittadino = '"+this.codiceFiscale+"';" ;
		//System.out.println(query); //stampo query per controllo in fase di DEBUG, poi posso commentare
		
		try {
			
			ResultSet rs = DBConnectionManager.selectQuery(query);
			
			while(rs.next()) {	//while perch� mi aspetto pi� risultati			
							
			
				DBIscrizione iscrizione = new DBIscrizione();
				iscrizione.setIdCittadino(rs.getString("idCittadino"));
				iscrizione.setPassword(rs.getString("Password"));
				iscrizione.setIban(rs.getString("Iban"));
				//DEVO CARICARE ANCHE ALTRE COSE? BOH
				
				this.iscrizioni.add(iscrizione);
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		
	}
	
		public int SalvaInDB() {
		
		int ret = 0;
		
		String query ="insert into cittadini (nome, cognome, codiceFiscale, indirizzoMail) values('"+this.nome+
				"','"+this.cognome+"','"+this.codiceFiscale+"','"+this.indirizzoMail+"');";
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
			
			String query = "delete from cittadini where codiceFiscale = " + this.codiceFiscale+";";
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

	public ArrayList<DBIscrizione> getIscrizioni() {
		return iscrizioni;
	}

	public void setIscrizioni(ArrayList<DBIscrizione> iscrizioni) {
		this.iscrizioni = iscrizioni;
	}
	
	
	
	
	
	
	
	
}
