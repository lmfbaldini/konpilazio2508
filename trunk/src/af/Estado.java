package af;

public class Estado {
	public String nome;
	public int tipo; /* 0: INICIAL, 1: REJEICAO, 2: ACEITACAO */
	
	public Estado(String nome, int tipo) {
		this.nome = nome;
		this.tipo = tipo;
	}
}