package objetosGerais;

import java.util.ArrayList;

public class ConjuntoDeSimbolos {
	ArrayList<Simbolo> simbolos = new ArrayList<Simbolo>();
	
	public ConjuntoDeSimbolos() {
		this.simbolos.clear();
		
	}
	
	public void add(Simbolo e) {
		this.simbolos.add(e);
		
	}
	
	public void addAll(ArrayList<Simbolo> e) {
		this.simbolos.addAll(e);
		
	}
	
	public void clear() {
		this.simbolos.clear();
		
	}
	
	public ArrayList<Simbolo> getAll() {
		return this.simbolos;
		
	}
	
	public Simbolo getSimbolo(String nome) {
		for (Simbolo s : simbolos) {
			if (s.nome.equals(nome))
				return s;
		}
		return null;
		
	}
	
	public String toString() {
		StringBuilder strb = new StringBuilder();
		for (Simbolo s : simbolos) {
			strb.append(s.toString()+"\n");
		}
		return strb.toString();
	}

}
