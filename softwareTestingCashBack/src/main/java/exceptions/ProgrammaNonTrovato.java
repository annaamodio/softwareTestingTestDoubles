package exceptions;

public class ProgrammaNonTrovato extends Exception {
	
	//Eccezione che verrà lanciata dal costruttore di DBProgramma se nel database non è presente il programma che si sta cercando

	public ProgrammaNonTrovato(String message) {
		super(message);
	}
	
	
	

}
