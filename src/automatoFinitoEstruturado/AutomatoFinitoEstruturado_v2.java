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
	AutomatoFinitoEstruturado_v2 submaquinaEmExecucao = null;
	
	boolean debug = true;
	
	
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
		this.submaquinaEmExecucao = null;
		
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
		if (r == null) {
			if (debug) System.out.println("Sem simbolo compativel: "+estadoAtual.toString()+" "+cadeia.espiaSimbolo());
			return false;
			
		}
		if (r.chamada()) {
			if ((submaquinas.getMaquina(r.getSubmaquina()) != null)) {
				if (debug) System.out.println("Iniciando chamada de submaquina\nRegra aplicada: "+r.toString());
				this.submaquinaEmExecucao = submaquinas.getMaquina(r.getSubmaquina());
				boolean resultado = this.submaquinaEmExecucao.processaChamadaClassica(cadeia);	
				if (debug) System.out.println("Fim da chamada de submaquina ("+resultado+")");
				if (resultado) {
					this.estadoAtual = r.getEstadoFinal();
					this.ultimaRegra = r;
				}
				
				return resultado;
			} else if (r.getSubmaquina().equals(this.nome)) {
				if (debug) System.out.println("Iniciando chamada de submaquina propria\nRegra aplicada: "+r.toString());				
				this.submaquinaEmExecucao = new AutomatoFinitoEstruturado_v2();
				this.submaquinaEmExecucao.copia(this);
				
				boolean resultado = this.submaquinaEmExecucao.processaChamadaClassica(cadeia);	
				if (debug) System.out.println("Fim da chamada de submaquina ("+resultado+")");
				if (resultado) {
					this.estadoAtual = r.getEstadoFinal();
					this.ultimaRegra = r;
				}
				
				return resultado;
				
			}
			
		} else if (r.getSimbolo() != null) {
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
				System.out.println("Erro");
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
	
	public boolean processaChamadaClassica(EntradaAutomato cadeia) {
		this.inicia();
		if (debug) System.out.println("Iniciando processamento em submaquina");
		
		while (!cadeia.vazia()) {
			if (this.estadoAtual.getTipo() == 1) {
				if (this.buscaRegra(estadoAtual, cadeia.espiaSimbolo()) == null) {
					return true;
				}
			}
			if (!(this.executaRegra(buscaRegra(estadoAtual, cadeia.espiaSimbolo()), cadeia))) {
				//erro
				System.out.println("Erro");
				break;
			} else {
				//tudo ok
			}
			
			if (debug) System.out.println("cadeia em sub: "+cadeia.toStringClassica());
			if (debug) System.out.println("estado em sub: "+estadoAtual.toString());
			
		}
		
		return false;
		
	}
	
	
}
