package objetosGerais;

import automatoFinito.AutomatoFinito_v2;
import automatoFinitoEstruturado.AutomatoFinitoEstruturado_v2;

public class Regra {
	Estadov2 estadoInicial = null;
	Estadov2 estadoFinal = null;
	Simbolo simbolo = null;
	AutomatoFinito_v2 automatoFinito = null;
	AutomatoFinitoEstruturado_v2 automatoEstruturado = null;
	String retorno = null;
	
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
	
	public AutomatoFinito_v2 getAutomatoFinito() {
		return automatoFinito;
	}
	
	public void setAutomatoFinito(AutomatoFinito_v2 automatoFinito) {
		this.automatoFinito = automatoFinito;
	}
	
	public AutomatoFinitoEstruturado_v2 getAutomatoEstruturado() {
		return automatoEstruturado;
	}
	
	public void setAutomatoEstruturado(
			AutomatoFinitoEstruturado_v2 automatoEstruturado) {
		this.automatoEstruturado = automatoEstruturado;
	}
	
	public String getRetorno() {
		return retorno;
	}
	
	public void setRetorno(String retorno) {
		this.retorno = retorno;
	}
	
	
	
}
