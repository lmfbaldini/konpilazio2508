package nucleo;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import af.*;

public class main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		AutomatoFinito automato = new AutomatoFinito();
		
		try {
			automato.construirAutomato(args[0]);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(1);
		}

		try {
			automato.processarArquivoDeEntrada(args[1]);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(1);
		}
		
		System.out.println("");

		
		
	}

}
