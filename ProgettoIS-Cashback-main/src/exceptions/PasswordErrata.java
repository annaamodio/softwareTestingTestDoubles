package exceptions;

public class PasswordErrata extends Exception {
	
	//Eccezione che verrà lanciata durante la verifica dei dati se la password inserita fosse errata

    public PasswordErrata(String message) {
        super(message);
    }
}