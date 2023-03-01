package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

public class DBAcquisto {

	private int idAcquisto;
	private Date data;
	private float importo;
	private DBCartaDiCredito carta;

	public DBAcquisto() {}

	public DBAcquisto(int idAcquisto) {
		this.idAcquisto=idAcquisto;
		caricaDaDB();
	}
	 
	
	public void caricaDaDB() {
		
		String query = "SELECT * FROM acquisti WHERE idAcquisto='"+this.idAcquisto+"';";
		//System.out.println(query); //DEGUB
		
		try {
			
			ResultSet rs = DBConnectionManager.selectQuery(query);
			
			if(rs.next()) { //se ho un risultato
				
				//mi vado a prendere i dati, accedendo tramite il nome dell'attributo-colonna
				this.setData(rs.getDate("Data"));
				this.setImporto(rs.getFloat("Importo"));
				
			}
		
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public void caricaCartaAcquistoDaDB() {
		
		String query = "SELECT * FROM carteDiCredito WHERE numero IN(SELECT NumeroCarta FROM acquisti WHERE idAcquisto='"+this.idAcquisto+"');";
		//System.out.println(query); //DEGUB
		
		try {
			
			ResultSet rs = DBConnectionManager.selectQuery(query);
			
			if(rs.next()) { //se ho un risultato
				
				DBCartaDiCredito carta = new DBCartaDiCredito();
				carta.setNumero(rs.getString("numero"));
				carta.setScadenza(rs.getString("scadenza"));
				
				this.setCarta(carta);
			}
		
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
		
	public int SalvaInDB(int idCittadino) {
		
		int ret = 0;
		
		String query ="INSERT INTO Acquisti (idAcquisto, Data, Importo, NumeroCarta, Iscrizione) VALUES ("+this.idAcquisto+","+this.data+ 
				","+this.importo+","+this.carta.getNumero()+","+idCittadino+"); ";
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
		
		String query = "delete from acquisti where idAcquisto = " + this.idAcquisto+";";
		//System.out.println(query);
		try {
			
			ret = DBConnectionManager.updateQuery(query);
			
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			ret = -1; //per segnalare l'error
		}
		
		return ret;
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

	public DBCartaDiCredito getCarta() {
		return carta;
	}

	public void setCarta(DBCartaDiCredito carta) {
		this.carta = carta;
	}
	
}
