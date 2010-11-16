package objetosGerais;

import java.util.Stack;

public class EntradaAutomato {
	public String entrada = null;
	public Stack<Simbolo> simbolos = new Stack<Simbolo>();
	
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
