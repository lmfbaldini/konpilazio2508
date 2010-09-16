package analisadorLexico;

import automatoFinito.AutomatoFinito;

public class AnalisadorLexico {
	public AutomatoFinito af = new AutomatoFinito();
	public String arquivoDeEntrada;
	public String palavrasReservadas;
	
	public AnalisadorLexico(String arquivoDeEspecificacao, String arquivoDeEntrada) {
		try {
			this.af.construir(arquivoDeEspecificacao);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.arquivoDeEntrada = arquivoDeEntrada;
		
	}
	
	
	
	
	
}
