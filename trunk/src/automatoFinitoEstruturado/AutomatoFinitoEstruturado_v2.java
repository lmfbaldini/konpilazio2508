package automatoFinitoEstruturado;

import objetosGerais.ConjuntoDeEstados;
import objetosGerais.ConjuntoDeMaquinas;
import objetosGerais.ConjuntoDeRegras;
import objetosGerais.ConjuntoDeSimbolos;
import objetosGerais.EntradaAutomato;
import objetosGerais.Estadov2;
import objetosGerais.Regra;
import objetosGerais.Simbolo;

public class AutomatoFinitoEstruturado_v2 {
	String nome;
	public ConjuntoDeEstados estados = new ConjuntoDeEstados();
	public ConjuntoDeRegras regras = new ConjuntoDeRegras();
	public ConjuntoDeSimbolos simbolos = new ConjuntoDeSimbolos();
	public ConjuntoDeMaquinas submaquinas = new ConjuntoDeMaquinas();
	
	Estadov2 estadoAtual = null;
	Regra ultimaRegra = null;
	
	
	public AutomatoFinitoEstruturado_v2() {
		this.clear();
		
	}
	
	public AutomatoFinitoEstruturado_v2(String nome) {
		this.clear();
		this.nome = nome;
		
	}
	
	public void clear() {
		estados.clear();
		simbolos.clear();
		regras.clear();
		submaquinas.clear();
		estadoAtual = null;
		ultimaRegra = null;
		nome = null;
		
	}
	
	public void copia(AutomatoFinitoEstruturado_v2 a) {
		this.clear();
		this.nome = a.nome;
		this.estados.addAll(a.estados.getAll());
		this.regras.addAll(a.regras.getAll());
		this.simbolos.addAll(a.simbolos.getAll());
		this.submaquinas.addAll(a.submaquinas.getAll());
		
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
		return ultimaRegra;
	}

	/**
	 * @param regraAtual the regraAtual to set
	 */
	public void setRegraAtual(Regra regraAtual) {
		this.ultimaRegra = regraAtual;
	}
	
	public String toString() {
		return nome+"\n"+estados.toString()+simbolos.toString()+regras.toString()+submaquinas.toString();
	}
	
	public String toStringAll() {
		return nome+"\n"+estados.toString()+simbolos.toString()+regras.toString();
	}
	
	public void inicia() {
		this.estadoAtual = estados.getEstadoInicial();
		
	}
	
	public Regra buscaRegra(Estadov2 e, Simbolo s) {
		return regras.getRegra(e, s);
		
	}
	
	public boolean executaRegra(Regra r, EntradaAutomato cadeia) {
		if (r.chamada() && submaquinas.getMaquina(r.getSubmaquina()) != null) {
			this.estadoAtual = r.getEstadoFinal();
			this.ultimaRegra = r;
			
			return submaquinas.getMaquina(r.getSubmaquina()).processaChamadaClassica(cadeia);
			
		} else if (r.getSimbolo() != null) {
			cadeia.consomeSimbolo();
			this.estadoAtual = r.getEstadoFinal();
			this.ultimaRegra = r;
			return true;
			
		} else  if (r.vazio()) {
			this.estadoAtual = r.getEstadoFinal();
			this.ultimaRegra = r;
			return true;
			
		}
		
		return false;
		
	}
	
	
	public void processaCadeiaClassica(EntradaAutomato cadeia) {
		
		
	}
	
	public boolean processaChamadaClassica(EntradaAutomato cadeia) {
		
		
		return false;
		
	}
	
	
}
