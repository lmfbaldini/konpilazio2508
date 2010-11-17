package objetosGerais;

import java.util.Stack;
import java.util.ArrayList;

public class EntradaAutomato {
	public String entrada = null;
	public Stack<Simbolo> simbolos = new Stack<Simbolo>();
	public ArrayList<Simbolo> buffer = new ArrayList<Simbolo>();
	
	public EntradaAutomato(String s) {
		this.entrada = s;
	}
	
	public void entradaClassica() {
		char[] chars = entrada.toCharArray();
		Stack<Simbolo> aux = new Stack<Simbolo>();
		
		for (char c : chars) {
			aux.push(new Simbolo(String.valueOf(c)));
		}
		while (!aux.isEmpty()) {
			simbolos.push(aux.pop());
		}
		
	}
	
	public Simbolo consomeSimbolo() {
		return simbolos.pop();
		
	}
	
	public Simbolo bufferizaSimbolo() {
		buffer.add(simbolos.peek());
		return simbolos.pop();
		
	}
	
	public Simbolo espiaSimbolo() {
		return simbolos.peek();
		
	}

	public boolean vazia() {
		
		return simbolos.isEmpty();
		
	}

	public String toStringClassica() {
		String cadeiaAtual = null;
		return simbolos.toString();
	}
	
}
