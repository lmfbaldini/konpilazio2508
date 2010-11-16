package objetosGerais;

import java.util.ArrayList;

public class ConjuntoDeRegras {
	ArrayList<Regra> regras = new ArrayList<Regra>();
	boolean debug = true;
	
	public ConjuntoDeRegras() {
		this.regras.clear();
		
	}
	
	public void add(Regra e) {
		this.regras.add(e);
		
	}
	
	public void addAll(ArrayList<Regra> e) {
		this.regras.addAll(e);
		
	}
	
	public void clear() {
		this.regras.clear();
		
	}
	
	public ArrayList<Regra> getAll() {
		return this.regras;
		
	}
	
	public Regra getRegra(Estadov2 e, Simbolo s) {
		for (Regra r : regras) {
			if (r.estadoInicial.equals(e)) {
				if (r.simbolo != null) {
					if (r.simbolo.equals(s)) {
						return r;
					}
				}
			}
		}
		for (Regra r : regras) {
			if (r.estadoInicial.equals(e) && r.chamada()) {
				return r;
			}
		}
		for (Regra r : regras) {
			if (r.estadoInicial.equals(e) && r.vazio())
				return r;
		}
		return null;
		
	}
	
	public String toString() {
		StringBuilder strb = new StringBuilder();
		for (Regra r : regras) {
			strb.append(r.toString()+"\n");
		}
		return strb.toString();
	}
	
	
	
}
