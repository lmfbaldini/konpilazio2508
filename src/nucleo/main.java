package nucleo;

import objetosGerais.EntradaAutomato;
import objetosGerais.ProcessadorDeEntrada;
import automatoFinito.AutomatoFinito;
import automatoFinito.AutomatoFinito_v2;
import automatoFinitoEstruturado.AutomatoFinitoEstruturado;
import automatoFinitoEstruturado.AutomatoFinitoEstruturado_v2;

public class main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		AutomatoFinito_v2 automato = new AutomatoFinito_v2();
		
		try {
			automato.copia(ProcessadorDeEntrada.constroiAFv2simples(args[0]));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(1);
		}
		
		System.out.println(automato.toString());
		
		System.out.println("Fim da leitura");
		try {
			EntradaAutomato in = new EntradaAutomato("a,a,a,aa");
			in.entradaClassica();
			automato.processaCadeiaClassica(in);
			
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

		System.out.println("Fim da execucao");
		
	}

}
