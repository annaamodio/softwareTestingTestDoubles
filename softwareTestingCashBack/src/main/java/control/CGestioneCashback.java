package control;

import entity.ApplicazioneCashback;
import exceptions.*;

public class CGestioneCashback {
    private static ApplicazioneCashback singleton;
	public static void gesticiRegistraCittadino(){

    }
	
	public static void gestisciRegistraAcquisto(){

    }
	
	public static float gestisciRichiediRimborso(String idCittadino, String password, int programma)
    throws IscrizioneNonTrovata,ProgrammaNonTrovato,PasswordErrata,ProgrammaNonTerminato, MinAcquistiNonRaggiunto, IllegalArgumentException
    {
        singleton = ApplicazioneCashback.getInstance();
        return singleton.richiediRimborso(idCittadino, password, programma,null);
	}
	
	
}