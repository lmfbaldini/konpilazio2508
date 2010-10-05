package nucleo;

import objetosGerais.ProcessadorDeEntrada;
import automatoFinito.AutomatoFinito;
import automatoFinitoEstruturado.AutomatoFinitoEstruturado;
import automatoFinitoEstruturado.AutomatoFinitoEstruturado_v2;

public class main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		AutomatoFinitoEstruturado_v2 automato = new AutomatoFinitoEstruturado_v2();
		
		try {
			automato.copia(ProcessadorDeEntrada.constroiAFv2(args[0]));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(1);
		}
		
		System.out.println(automato.toString());
	/*	
		System.out.println("Fim da leitura");
		try {
			automato.processarArquivoDeEntrada(args[1],true);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(1);
		}

		System.out.println("Fim da execucao");*/
		
	}

}
