package objetosGerais;

import java.util.ArrayList;

public class ConjuntoDeRegras {
	ArrayList<Regra> regras = new ArrayList<Regra>();

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
			if (r.estadoInicial.equals(e) && r.simbolo.equals(s))
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
