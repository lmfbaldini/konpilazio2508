package nucleo;

import af.*;

public class main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		AutomatoFinito automato = new AutomatoFinito();
		
		try {
			automato.construir_ARQUIVO_TIPO_01("automato.txt");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(1);
		}
		
		if (automato.interpretar("TI,,")) {
			System.out.println("aceito");
		} else {
			System.out.println("nao aceito");
		}
		
		System.out.println("OIIII DEBUGGERRR");
		
		
	}

}
