package af;

import java.util.ArrayList;
import java.util.Hashtable;

public class AutomatoFinito {
	public ArrayList<Estado> estados;
	public ArrayList<String> simbolos;
	/*
	 * A tabela hash de transicoes utiliza como chave uma String construida como a concatenacao de um simbolo com o nome
	 * de um estado.
	 */
	public RegraAF<Estado, String, Estado> regras;
	
	public AutomatoFinito() {
		estados.clear();
		simbolos.clear();
		regras.clear();
	}
	
	public void clear() {
		estados.clear();
		simbolos.clear();
		regras.clear();
	}
	
	public void setarAutomato(AutomatoFinito af) {
		this.estados.addAll(af.estados);
		this.simbolos.addAll(af.simbolos);
		this.regras.putAll(af.regras);
	}
	
}
