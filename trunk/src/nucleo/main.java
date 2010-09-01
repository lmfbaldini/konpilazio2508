package nucleo;

import automatoFinito.AutomatoFinito;

public class main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		AutomatoFinito automato = new AutomatoFinito();
		
		try {
			automato.construir(args[0]);
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

		
		
	}

}
