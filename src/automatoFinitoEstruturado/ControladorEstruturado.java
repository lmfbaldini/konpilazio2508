package automatoFinitoEstruturado;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import objetosGerais.EntradaAutomato;
import objetosGerais.ProcessadorDeEntrada;

public class ControladorEstruturado {
	ArrayList<AutomatoFinitoEstruturado_v2> maquinas = new ArrayList<AutomatoFinitoEstruturado_v2>();
	AutomatoFinitoEstruturado_v2 automatoPrincipal = new AutomatoFinitoEstruturado_v2();
	
	String arquivoDeEntrada = null;
	String arquivoDeDefinicao = null;
	
	
	
	public ControladorEstruturado(String definicao) {
		this.arquivoDeDefinicao = definicao;
		this.automatoPrincipal.copia(ProcessadorDeEntrada.constroiAFv2(definicao));
		
		maquinas.clear();
		maquinas.add(automatoPrincipal);
		for (AutomatoFinitoEstruturado_v2 a : automatoPrincipal.submaquinas.getAll()) {
			maquinas.add(a);
			
		}
		
	}
	
	public void processaCadeiaClassica(EntradaAutomato cadeia) {
		
		
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
				
				this.processaCadeiaClassica(new EntradaAutomato(linha));
				
				linha = buffer.readLine();
			}
			buffer.close();
		} catch (Exception e) {
			
		}
		
		
		
	}

}
