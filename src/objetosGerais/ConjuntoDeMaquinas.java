package objetosGerais;

import java.util.ArrayList;

import automatoFinito.AutomatoFinito_v2;
import automatoFinitoEstruturado.AutomatoFinitoEstruturado;
import automatoFinitoEstruturado.AutomatoFinitoEstruturado_v2;

public class ConjuntoDeMaquinas {
	ArrayList<AutomatoFinitoEstruturado_v2> AFs = new ArrayList<AutomatoFinitoEstruturado_v2>();

	public ConjuntoDeMaquinas() {
		AFs.clear();
		
	}
	
	public void novaMaquinaAFE(String nome) {
		AFs.add(new AutomatoFinitoEstruturado_v2(nome));
		
	}

	public AutomatoFinitoEstruturado_v2 getMaquina(String nome) {
		for (AutomatoFinitoEstruturado_v2 afe : AFs) {
			if (afe.getNome().equals(nome))
				return afe;
		}
		
		return null;
		
	}
	
	public ArrayList<AutomatoFinitoEstruturado_v2> getAll() {
		return AFs;
		
	}
	
	public void addAll(ArrayList<AutomatoFinitoEstruturado_v2> AF) {
		this.AFs.addAll(AF);
		
	}

	public void clear() {
		AFs.clear();
		
	}
	
	public String toString() {
		StringBuilder strb = new StringBuilder();
		for (AutomatoFinitoEstruturado_v2 af : AFs) {
			strb.append(af.toString()+"\n");
		}
		return strb.toString();
	}

	
	

}