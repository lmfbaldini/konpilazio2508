package automatoFinitoEstruturado;

import java.io.BufferedReader;
import java.io.FileReader;
import objetosGerais.EntradaAutomato;
import objetosGerais.ProcessadorDeEntrada;

public class ControladorEstruturado {
	AutomatoFinitoEstruturado_v2 automatoPrincipal = new AutomatoFinitoEstruturado_v2();
	
	String arquivoDeEntrada = null;
	String arquivoDeDefinicao = null;
	
	
	
	public ControladorEstruturado(String definicao) {
		this.arquivoDeDefinicao = definicao;
		this.automatoPrincipal.copia(ProcessadorDeEntrada.constroiAFv2(definicao));
		
	}
	
	public ControladorEstruturado(AutomatoFinitoEstruturado_v2 automato) {
		this.arquivoDeDefinicao = null;
		this.automatoPrincipal.copia(automato);
		
	}
	
	
	/**Cada simbolo atomico para o automato, as cadeias nao fogem ao escopo do automato de pilha
	 * cada linha contem uma cadeia para o automato
	 * 
	 * @param entrada
	 */
	public void processaEntradaClassica(String entrada) {
		this.arquivoDeEntrada = entrada;
		String linha = null;
		try {
			FileReader file = new FileReader(entrada);
			BufferedReader buffer = new BufferedReader(file);
			
			System.out.println("Inciando processamento do arquivo: "+entrada);
			
			linha = buffer.readLine();
			while(linha != null){
				EntradaAutomato in = new EntradaAutomato(linha);
				in.entradaClassica();
				
				automatoPrincipal.processaCadeiaClassica(in);
				
				linha = buffer.readLine();
			}
			buffer.close();
		} catch (Exception e) {
			
		}
		
		
		
	}

}
