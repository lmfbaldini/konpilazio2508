package automatoFinito;


import objetosGerais.*;

public class AutomatoFinito_v2 {
	String nome;
	public ConjuntoDeEstados estados = new ConjuntoDeEstados();
	public ConjuntoDeRegras regras = new ConjuntoDeRegras();
	public ConjuntoDeSimbolos simbolos = new ConjuntoDeSimbolos();
	
	Estadov2 estadoAtual = null;
	Regra regraAtual = null;
	
	public AutomatoFinito_v2() {
		this.clear();
		
	}
	
	public void clear() {
		estados.clear();
		simbolos.clear();
		regras.clear();
		estadoAtual = null;
		regraAtual = null;
		
	}
	
	public void copia(AutomatoFinito_v2 a) {
		this.clear();
		this.estados.addAll(a.estados.getAll());
		this.regras.addAll(a.regras.getAll());
		this.simbolos.addAll(a.simbolos.getAll());
		
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the estadoAtual
	 */
	public Estadov2 getEstadoAtual() {
		return estadoAtual;
	}

	/**
	 * @param estadoAtual the estadoAtual to set
	 */
	public void setEstadoAtual(Estadov2 estadoAtual) {
		this.estadoAtual = estadoAtual;
	}

	/**
	 * @return the regraAtual
	 */
	public Regra getRegraAtual() {
		return regraAtual;
	}

	/**
	 * @param regraAtual the regraAtual to set
	 */
	public void setRegraAtual(Regra regraAtual) {
		this.regraAtual = regraAtual;
	}
	
	
	
}

