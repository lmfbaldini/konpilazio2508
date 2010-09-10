package nucleo;

import automatoFinito.AutomatoFinito;
import automatoFinitoEstruturado.AutomatoFinitoEstruturado;

public class main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		AutomatoFinitoEstruturado automato = new AutomatoFinitoEstruturado();
		
		try {
			automato.construir(args[0]);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(1);
		}
		
		System.out.println("Fim da leitura");
		try {
			automato.processarArquivoDeEntrada(args[1],true);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(1);
		}

		System.out.println("Fim da execucao");
		
	}

}
