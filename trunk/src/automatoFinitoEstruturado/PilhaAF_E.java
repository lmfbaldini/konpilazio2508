package automatoFinitoEstruturado;

import java.util.Hashtable;
import java.util.Stack;

public class PilhaAF_E<T, U> {
	public Stack<T> pilhaInterna;
	public Hashtable<T, Stack<U>> mapa;
	
	public PilhaAF_E() {
		pilhaInterna = new Stack<T>();
		mapa = new Hashtable<T, Stack<U>>();
	}
	
	public void clear() {
		pilhaInterna.clear();
		mapa.clear();
	}
	
	public void push(T t, U u) {
		Stack<U> aux = new Stack<U>();
		pilhaInterna.push(t);
		if (mapa.get(t) == null) {
			aux.push(u);
			mapa.put(t, aux);
		} else {
			aux = mapa.get(t);
			aux.push(u);
			mapa.put(t, aux);
		}
	}
	
	public T pop_T() {
		return pilhaInterna.pop();
		
	}
	
	public U pop_U(T t) {
		return mapa.get(t).pop();
		
	}
	
	public T peek_T() {
		return pilhaInterna.peek();
		
	}
	
	public U peek_U(T t) {
		return mapa.get(t).peek();
		
	}	
	
}