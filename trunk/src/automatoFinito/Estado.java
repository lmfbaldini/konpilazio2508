package automatoFinito;

public class Estado {
	public String nome;
	public int tipo; /* 0: INICIAL NAO-FINAL, 1: INICIAL FINAL, 2: NAO-FINAL e 3: FINAL */
	
	public Estado(String nome, int tipo) {
		this.nome = nome;
		this.tipo = tipo;
	}
	
	public Estado(Estado e) {
		this.nome = e.nome;
		this.tipo = e.tipo;
	}

	public String toString() {
		return nome+"("+tipo+")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + tipo;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Estado other = (Estado) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (tipo != other.tipo)
			return false;
		return true;
	}
	
	
	
}