package tests;

import java.util.ArrayList;


public class Test {

	static final int NUM_TESTS = 11;
	
	public static void main(String args[]) {

		 TesterThread[] tests= new TesterThread[NUM_TESTS];
		 
		//Effettuiamo i test funzionali in parallelo
		
		//TEST 1
		tests[0] = new TesterThread(1, "121314151617181", "1234567890", "100003");
		//associata ad una registrazione per cui l'importoCalcolato è minore di maxTetto
		
		//TEST 2
		tests[1] = new TesterThread(2, "ABCDEFGHI123456", "qwerty7890", "162022");
		//associata ad una registrazione per cui l'importoCalcolato è maggiore di maxTetto
		
		//TEST 3
		tests[2] = new TesterThread(3, "HHHHHHH12123456", "56789lmnop", "262022");
		//programma non ancora terminato
		
		//TEST 4
		tests[3] = new TesterThread(4, "ABCDEFGHI12*45%", "qwerty7890", "162022");
		//idCittadino contiene simboli
	
		//TEST 5
		tests[4] = new TesterThread(5, "ABCDEFGHI123456", "qwer!y&890", "162022");
		//password contiene simboli
		
		//TEST 6
		tests[5] = new TesterThread(6, "ABCDEFGHI123456", "qwerty7890", "-162022");
		//idProgramma <0
		
		//TEST 7
		tests[6] = new TesterThread(7, "ABCDEFGHI123456", "querty7811", "162022");
		//password non associata a questa registrazione
		
		//TEST 8
		tests[7] = new TesterThread(8, "ABCDEFGHI1234", "querty7890", "162022");
		//idCittadino di lunghezza diversa da 15 caratteri
		
		//TEST 9
		tests[8] = new TesterThread(9, "ABCDEFGHI123456", "querty7890112", "162022");
		//password di lunghezza diversa da 10 caratteri
		
		//TEST 10
		tests[9] = new TesterThread(10, "ABCDEFGHI123456", "querty7890", "1620");
		//programma di lunghezza diversa da 6 cifre
		
		//TEST 11
		tests[10] = new TesterThread(11, "ABCDEFGHI123456", "querty7890", "16.22p");
		
		for(int i=0; i<NUM_TESTS; i++) tests[i].start();
	}
	
}
