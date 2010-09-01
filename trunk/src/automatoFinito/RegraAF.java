package automatoFinito;

import java.util.Hashtable;
import java.util.LinkedList;

public class RegraAF<T, U, V> {
	private Hashtable<T, Hashtable<U, LinkedList<V>>> tabela;
	
	public RegraAF() {
		tabela = new Hashtable<T, Hashtable<U,LinkedList<V>>>();
	}
	
	public void clear() {
		tabela.clear();
	}
	
	public void put(T t, U u, V v) {
		if (tabela.get(t) == null) {
			Hashtable<U,LinkedList<V>> aux = new Hashtable<U, LinkedList<V>>();
			LinkedList<V> aux2 = new LinkedList<V>();
			aux2.add(v);
			aux.put(u, aux2);
			tabela.put(t, new Hashtable<U, LinkedList<V>>(aux));
		} else {
			if (tabela.get(t).get(u) == null) {
				LinkedList<V> aux2 = new LinkedList<V>();
				aux2.add(v);
				tabela.get(t).put(u, aux2);
			} else {
				tabela.get(t).get(u).add(v);
			}
		}
	}
	
	public LinkedList<V> get(T t, U u) {
		if (tabela.get(t) != null) {
			if (tabela.get(t).get(u) != null) {
				return tabela.get(t).get(u);
			}
		}
		
		return null;
		
	}

	public void putAll(RegraAF<T, U, V> tabelaInterna) {
		this.tabela.putAll(tabelaInterna.tabela);
		
	}
	
	public String toString() {
		return tabela.toString().replace("=", ";");
		
	}
	
	
	
}
