package objetosGerais;

import java.util.ArrayList;

public class ConjuntoDeEstados {
	ArrayList<Estadov2> estados = new ArrayList<Estadov2>();
	
	public ConjuntoDeEstados() {
		this.estados.clear();
		
	}
	
	public void add(Estadov2 e) {
		this.estados.add(e);
		
	}
	
	public void addAll(ArrayList<Estadov2> e) {
		this.estados.addAll(e);
		
	}
	
	public void clear() {
		this.estados.clear();
		
	}
	
	public ArrayList<Estadov2> getAll() {
		return this.estados;
		
	}
	
	public Estadov2 getEstado(String nome) {
		for (Estadov2 r : estados) {
			if (r.nome.equals(nome))
				return r;
		}
		return null;
		
	}
	
	public boolean naoDeterminismo() {
		ArrayList<Simbolo> tempSimbolos = new ArrayList<Simbolo>();
		tempSimbolos.clear();
		for (Estadov2 e : estados) {
			for (Regra r : e.regras) {
				if (tempSimbolos.contains(r.simbolo))
					return true;
				else
					tempSimbolos.add(r.simbolo);
			}
			tempSimbolos.clear();
		}
		return false;
		
	}
	
	public String toString() {
		StringBuilder strb = new StringBuilder();
		for (Estadov2 e : estados) {
			strb.append(e.toString()+"\n");
		}
		return strb.toString();
	}

	public Estadov2 getEstadoInicial() {
		for (Estadov2 e : estados) {
			if (e.tipo == 2) {
				return e;
			}
		}		
		return null;
	}
	

}
