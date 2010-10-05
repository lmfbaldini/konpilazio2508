package automatoFinitoEstruturado;

import java.util.ArrayList;

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
	
	public void processaCadeiaClassica(String cadeia) {
		
		
	}
	
	/**Cada simbolo é atômico para o automato, as cadeias nao fogem ao escopo do automato de pilha
	 * cada linha contêm uma cadeia para o automato
	 * 
	 * @param entrada
	 */
	public void processaEntradaClassica(String entrada) {
		this.arquivoDeEntrada = entrada;
		
		
		
	}

}
