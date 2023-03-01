package exceptions;

public class ProgrammaNonTerminato extends Exception {
	
	//Eccezione che verrà lanciata durante la verifica dei dati se il programma non è ancora terminato

    public ProgrammaNonTerminato(String message) {
        super(message);
    }
}