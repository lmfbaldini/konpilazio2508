package automatoFinito;


import analisadorLexico.AnalisadorLexico;
import analisadorLexico.Token;
import objetosGerais.*;

public class AutomatoFinito_v2 {
	String nome;
	public ConjuntoDeEstados estados = new ConjuntoDeEstados();
	public ConjuntoDeRegras regras = new ConjuntoDeRegras();
	public ConjuntoDeSimbolos simbolos = new ConjuntoDeSimbolos();
	
	Estadov2 estadoAtual = null;
	Regra ultimaRegra = null;
	
	boolean debug = false;
	
	public AutomatoFinito_v2() {
		this.clear();
		
	}
	
	public void clear() {
		estados.clear();
		simbolos.clear();
		regras.clear();
		estadoAtual = null;
		ultimaRegra = null;
		
	}
	
	public void copia(AutomatoFinito_v2 a) {
		this.clear();
		this.estados.addAll(a.estados.getAll());
		this.regras.addAll(a.regras.getAll());
		this.simbolos.addAll(a.simbolos.getAll());
		this.nome = a.nome;
		
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
	public Regra getUltimaRegra() {
		return ultimaRegra;
	}

	/**
	 * @param regraAtual the regraAtual to set
	 */
	public void setUltimaRegra(Regra ultimaRegra) {
		this.ultimaRegra = ultimaRegra;
	}
	
	
	public String toString() {
		return nome+"\n"+estados.toString()+simbolos.toString()+regras.toString();
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
		if (r == null) {
			if (debug) System.out.println("Sem simbolo compativel: "+estadoAtual.toString()+" "+cadeia.espiaSimbolo());
			return false;
			
		}
		if (r.getSimbolo() != null) {
			if (r.getSimbolo().equals(cadeia.espiaSimbolo())) {
				Simbolo s = cadeia.consomeSimbolo();
				this.estadoAtual = r.getEstadoFinal();
				this.ultimaRegra = r;
				
				if (debug) System.out.println("Simbolo consumido: "+s.toString()+"\nRegra aplicada: "+r.toString());
				return true;
				
			} else {
				if (debug) System.out.println("Sem simbolo compativel: "+estadoAtual.toString()+" "+cadeia.espiaSimbolo());
				return false;
				
			}
			
		} else  if (r.vazio()) {
			this.estadoAtual = r.getEstadoFinal();
			this.ultimaRegra = r;
			
			if (debug) System.out.println("Transicao em vazio\nRegra aplicada: "+r.toString());
			
			return true;
			
		}
		
		if (debug) System.out.println("Nenhuma regra encontrada\n"+estadoAtual.toString()+ultimaRegra.toString()+"\n"+this.toString());
		
		return false;
		
	}
	
	
	public void processaCadeiaClassica(EntradaAutomato cadeia) {
		this.inicia();
		if (debug) System.out.println(cadeia.toStringClassica());
		
		while (!cadeia.vazia()) {
			if (!(this.executaRegra(buscaRegra(estadoAtual, cadeia.espiaSimbolo()), cadeia))) {
				//erro
				if (debug) System.out.println("Erro");
				break;
			} else {
				//tudo ok
			}
			if (debug) System.out.println("cadeia: "+cadeia.toStringClassica());
			if (debug) System.out.println("estado: "+estadoAtual.toString());
			
		}
		
		if (cadeia.vazia() && estadoAtual.getTipo() == 1) {
			System.out.println("cadeia: "+cadeia.entrada+" ACEITA");
		} else {
			System.out.println("cadeia: "+cadeia.entrada+" REJEITADA");
		}
		
	}
	
	public Token transduzCadeiaClassica(EntradaAutomato cadeia) {
		this.inicia();
		if (debug) System.out.println(cadeia.toStringClassica());
		
		while (!cadeia.vazia()) {
			if (!(this.executaRegra(buscaRegra(estadoAtual, cadeia.espiaSimbolo()), cadeia))) {
				//erro
				if (debug) System.out.println("Erro");
				break;
			} else {
				if (ultimaRegra.vazio() && ultimaRegra.getRetorno() != null) {
					return AnalisadorLexico.constroiToken(ultimaRegra.getRetorno());
				}
			}
			if (debug) System.out.println("cadeia: "+cadeia.toStringClassica());
			if (debug) System.out.println("estado: "+estadoAtual.toString());
			
		}
		
		if (cadeia.vazia() && estadoAtual.getTipo() == 1) {
			System.out.println("cadeia: "+cadeia.entrada+" ACEITA");
		} else {
			System.out.println("cadeia: "+cadeia.entrada+" REJEITADA");
		}
		
		return null;
	}
	
}

