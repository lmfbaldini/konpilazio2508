package automatoFinitoEstruturado;

import java.util.Hashtable;
import java.util.Stack;

import automatoFinito.Estado;

public class PilhaAF_E<T, U> {
	public Stack<T> pilhaMaquina;
	public Stack<U> pilhaEstado;
	public Stack<String> pilhaToString;
	
	public PilhaAF_E() {
		pilhaMaquina = new Stack<T>();
		pilhaEstado = new Stack<U>();
		pilhaToString = new Stack<String>();
	}
	
	public void clear() {
		pilhaMaquina.clear();
		pilhaEstado.clear();
		pilhaToString.clear();
	}
	
	public void push(T t, U u) {
		pilhaMaquina.push(t);
		pilhaEstado.push(u);
		pilhaToString.push(t.toString() + " - " + u.toString());
	}
	
	public T pop_T() {
		return pilhaMaquina.pop();
		
	}
	
	public U pop_U(T t) {
		pilhaToString.pop();
		return pilhaEstado.pop();
		
	}
	
	public T peek_T() {
		return pilhaMaquina.peek();
		
	}
	
	public U peek_U(T t) {
		return pilhaEstado.peek();
		
	}	
	
}