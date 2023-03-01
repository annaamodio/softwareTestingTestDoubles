package database;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DBCartaDiCredito {

	private String numero;
	private String scadenza;
	
	public DBCartaDiCredito() {}
	
	public DBCartaDiCredito(String numero) {
		this.numero= numero;
		caricaDaDB();
	}
	
	public void caricaDaDB() {
		
		String query = "SELECT * FROM carteDiCredito WHERE numero='"+this.numero+"';";
		//System.out.println(query); //DEGUB
		
		try {
			
			ResultSet rs = DBConnectionManager.selectQuery(query);
			
			if(rs.next()) { //se ho un risultato
				
				//mi vado a prendere i dati, accedendo tramite il nome dell'attributo-colonna
				this.setScadenza(rs.getString("scadenza"));
			}
		
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public int SalvaInDB(int idCittadino) {
		
		//prende in ingresso l'id dell'iscrizione a cui la carta di credito è relativa
		int ret = 0;
		
		String query ="INSERT INTO CarteDiCredito (numero, scadenza, iscrizione) VALUES ("+this.numero+",'"
		+this.scadenza+"',"+idCittadino+";"; 
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
		
		String query = "delete from carteDiCredito where numero = " + this.numero+";";
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
	
	
	
	
}
