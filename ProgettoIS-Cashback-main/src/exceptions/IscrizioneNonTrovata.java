package exceptions;

public class IscrizioneNonTrovata extends Exception {
	
	//Eccezione che verrà lanciata dal costruttore di DBIscrizione se nel database non è presente l'iscrizione che si sta cercando
	
	public IscrizioneNonTrovata(String message) {
		super(message);
	}
}
