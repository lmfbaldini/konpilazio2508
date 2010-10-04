package objetosGerais;

import java.util.ArrayList;

public class Estadov2 {
	String nome = null;
	/*
	 * 0 normal
	 * 1 aceitacao
	 * 2 inicial
	 */
	int tipo = 0;
	String retorno = null;
	ArrayList<Regra> regras = new ArrayList<Regra>();
	
	public Estadov2() {
		this.regras.clear();
		
	}
	
	public void clear() {
		this.nome = null;
		this.tipo = 0;
		this.retorno = null;
		this.regras.clear();
		
	}
	
	public void addRegra(Regra r) {
		regras.add(r);
		
	}
	
	public void parseRegras(ArrayList<Regra> rList) {
		this.regras.clear();
		for (Regra r : rList) {
			if (r.estadoInicial == this) {
				this.regras.add(r);
			}
		}
		
	}
	
	public ArrayList<Regra> getRegra(Simbolo s) {
		ArrayList<Regra> temp = new ArrayList<Regra>();
		temp.clear();
		for (Regra r : regras) {
			if (r.simbolo == s || r.simbolo == null) {
				temp.add(r);
			}
		}
		
		return temp;
		
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public String getRetorno() {
		return retorno;
	}

	public void setRetorno(String retorno) {
		this.retorno = retorno;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((retorno == null) ? 0 : retorno.hashCode());
		result = prime * result + tipo;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Estadov2 other = (Estadov2) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (retorno == null) {
			if (other.retorno != null)
				return false;
		} else if (!retorno.equals(other.retorno))
			return false;
		if (tipo != other.tipo)
			return false;
		return true;
	}
	
	
}
