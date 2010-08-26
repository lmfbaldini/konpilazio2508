package af;

import java.util.Hashtable;

public class RegraAF<T, U, V> {
	Hashtable<T,V> tabelaUm;
	Hashtable<U,V> tabelaDois;

	public void putAll(RegraAF<? extends T, ? extends U, ? extends V> regra) {
		this.tabelaUm.putAll(regra.tabelaUm);
		this.tabelaDois.putAll(regra.tabelaDois);
	}
	
	public void clear() {
		this.tabelaUm.clear();
		this.tabelaDois.clear();
	}

	public void put(T t, U u, V v) {
		tabelaUm.put(t, v);
		tabelaDois.put(u, v);
	}
	
}
