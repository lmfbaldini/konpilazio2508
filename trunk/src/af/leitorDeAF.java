package af;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 * Esta classe realiza a leitura do arquivo e retorna atraves do metodo iniciarLeitura, que recebe como parametro o nome
 * do arquivo, um objeto AutomatoFinito equivalente ao descrito no arquivo de entrada.
 * 
 * @author Baldini
 *
 *
 * OBS: Podemos criar uma factory com pool de leitores.
 *
 */
public class leitorDeAF {
	private String arquivo;
	private AutomatoFinito automatoInterno = new AutomatoFinito();

	public leitorDeAF(String arquivo) {
		this.arquivo = arquivo;
		
	}

	/**
	 * controi o automato considerando o arquivo do tipo 01
	 * ARQUIVO DO TIPO 01: Cada linha contem uma informacao, 
	 * a palavra ESTADOS inidica que as proximas linha serao estados (cada estado tera um nome e um tipo, separados por um espaco,
	 * o tipo de um estado vale 0 p/ estado INICIAL, 1 para estado de REJEICAO e 2 para estado de ACEITACAO), 
	 * a palavra SIMBOLOS indica que as proximas linhas serao simbolos
	 * e por fim a palavra REGRAS indica que as proximas linhas serao regras (um nome de estado, um simbolo e um outro
	 * nome de estado, separados por espacos)
	 * 
	 * @return AF
	 */
	public AutomatoFinito construir_ARQUIVO_TIPO_01() {
		String ref = null;
		String linha = null;
		
		try {
			FileReader file = new FileReader(arquivo); // read a file
			BufferedReader buffer = new BufferedReader(file);
			StringTokenizer valores;
			linha = buffer.readLine();	/* Le  a primeira linha */
			

			while(linha != null){
				if (linha == "ESTADOS" || linha == "SIMBOLOS" || linha == "REGRAS") {
					ref = linha;
					linha = buffer.readLine();
					continue;
				}
				
				if (ref == "ESTADOS") {
					valores = new StringTokenizer(linha); /* separa a String linha em valores separados por \n,\t,\r ou \f */
					automatoInterno.estados.add(new Estado(valores.nextToken(), Integer.parseInt(valores.nextToken())));
					
				} else if (ref == "SIMBOLOS") {
					valores = new StringTokenizer(linha); /* separa a String linha em valores separados por \n,\t,\r ou \f */
					automatoInterno.simbolos.add(valores.nextToken());
					
				} else if (ref == "REGRAS") {
					valores = new StringTokenizer(linha); /* separa a String linha em valores separados por \n,\t,\r ou \f */
					automatoInterno.estados.add(new Estado(valores.nextToken(), Integer.parseInt(valores.nextToken().toString())));
						
				} else {
					
					
				}
				
				
				
				linha = buffer.readLine();
			}
			
			buffer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return automatoInterno;
		
	}

}
