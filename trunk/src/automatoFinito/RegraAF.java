package automatoFinito;

import java.util.Hashtable;

public class RegraAF<T, U, V> {
	private Hashtable<T, Hashtable<U, V>> tabela;
	
	public RegraAF() {
		tabela = new Hashtable<T, Hashtable<U,V>>();
	}
	
	public void clear() {
		tabela.clear();
	}
	
	public void put(T t, U u, V v) {
		if (tabela.get(t) == null) {
			Hashtable<U,V> aux = new Hashtable<U, V>();
			aux.put(u, v);
			tabela.put(t, new Hashtable<U, V>(aux));
		} else {
			tabela.get(t).put(u, v);
		}
		
	}
	
	public V get(T t, U u) {
		if (tabela.get(t) != null)
			if (tabela.get(t).get(u) != null)
				return tabela.get(t).get(u);
		
		return null;
		
	}

	public void putAll(RegraAF<T, U, V> tabelaInterna) {
		this.tabela.putAll(tabelaInterna.tabela);
		
	}
	
	public String toString() {
		return tabela.toString().replace("=", ";");
		
	}
	
	
	
}
