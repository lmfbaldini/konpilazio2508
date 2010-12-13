package analisadorLexico;

import objetosGerais.Token;
import automatoFinito.AutomatoFinito;

public class AnalisadorLexico {
	public AutomatoFinito af = new AutomatoFinito();
	public String arquivoDeEntrada;
	public String palavrasReservadas;
	
	public AnalisadorLexico(String arquivoDeEspecificacao, String arquivoDeEntrada, String palavrasReservadas) {
		try {
			this.af.construir(arquivoDeEspecificacao);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.arquivoDeEntrada = arquivoDeEntrada;
		this.palavrasReservadas = palavrasReservadas;
		
		/* TODO 
		 * 
		 * Gerar tabela de palavras reservadas
		 * inicializar tabela de simbolos e tokens
		 *  
		 * 
		 * */
	}
	
	public static Token constroiToken(String parsed) {
		
		
		return null;
	}
	
	
	
	
	
	
}
