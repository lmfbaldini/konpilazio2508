package objetosGerais;

import automatoFinito.AutomatoFinito_v2;
import automatoFinitoEstruturado.AutomatoFinitoEstruturado_v2;

public class Regra {
	Estadov2 estadoInicial = null;
	Estadov2 estadoFinal = null;
	Simbolo simbolo = null;
	String retorno = null;
	String submaquina = null;
	
	public Regra(Estadov2 estado, Estadov2 estado2, Simbolo simbolo2,
			String textContent) {
		this.estadoInicial = estado;
		this.estadoFinal = estado2;
		this.simbolo = simbolo2;
		this.retorno = textContent;
		
	}
	
	public Regra(Estadov2 estado, Estadov2 estado2, String submaquina,
			String textContent) {
		this.estadoInicial = estado;
		this.estadoFinal = estado2;
		this.submaquina = submaquina;
		this.retorno = textContent;
		
	}

	public Estadov2 getEstadoInicial() {
		return estadoInicial;
	}
	
	public void setEstadoInicial(Estadov2 estadoInicial) {
		this.estadoInicial = estadoInicial;
	}
	
	public Estadov2 getEstadoFinal() {
		return estadoFinal;
	}
	
	public void setEstadoFinal(Estadov2 estadoFinal) {
		this.estadoFinal = estadoFinal;
	}
	
	public Simbolo getSimbolo() {
		return simbolo;
	}
	
	public void setSimbolo(Simbolo simbolo) {
		this.simbolo = simbolo;
	}
	
	public String getRetorno() {
		return retorno;
	}
	
	public void setRetorno(String retorno) {
		this.retorno = retorno;
	}
	
	public String getSubmaquina() {
		return submaquina;
	}

	public void setSubmaquina(String submaquina) {
		this.submaquina = submaquina;
	}

	public String toString() {
		return (simbolo!=null)? estadoInicial.toString()+"-- "+simbolo.toString()+" -->"+estadoFinal.toString(): estadoInicial.toString()+"=="+submaquina+"==>"+estadoFinal.toString();
	}
	
	public boolean chamada() {
		if (this.submaquina != null) {
			return true;
		}
		else
			return false;
		
	}

	public boolean vazio() {
		if (this.simbolo == null && this.submaquina == null) {
			return true;
		}
		else
			return false;
	}
	
	
	
}
