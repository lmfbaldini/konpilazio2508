package af;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class AutomatoFinito {
	public ArrayList<Estado> estados;
	public ArrayList<String> simbolos;
	/*
	 * A tabela hash de transicoes utiliza como chave uma String construida como a concatenacao de um simbolo com o nome
	 * de um estado.
	 */
	public RegraAF<Estado, String, Estado> regras;
	
	public AutomatoFinito() {
		estados = new ArrayList<Estado>();
		simbolos = new ArrayList<String>();
		regras = new RegraAF<Estado, String, Estado>();
	}
	
	public void clear() {
		estados.clear();
		simbolos.clear();
		regras.clear();
	}
	
	public void setarAutomato(AutomatoFinito af) {
		this.estados.addAll(af.estados);
		this.simbolos.addAll(af.simbolos);
		this.regras.putAll(af.regras);
	}
	
	public boolean interpretar(String w) {
		Estado estadoAtual = null;
		/* Converte a sequencia de simbolos de entrada (todos caracteres, concatenados em uma string)
		 * para um Array de elementos na mesma ordem, para facilitar a compracacao no algoritimo e evitar casts explicitos
		 */
		ArrayList<String> simbolosDaCadeia = new ArrayList<String>();
		char[] Saux = w.toCharArray();
		for (char d : Saux) {
			String S = String.valueOf(d);
			simbolosDaCadeia.add(S);
		}
		/*
		 * Seta o estado inicial
		 */
		for (Estado q : estados) {
			if (q.tipo == 0 || q.tipo == 1)
				estadoAtual = q;
		}
		/*
		 * Comeca a leitura da cadeia
		 */
		int contador = 0;
		for (String s : simbolosDaCadeia) {
			if (regras.get(estadoAtual, s) != null) {
				estadoAtual = regras.get(estadoAtual, s);
				System.out.println("w:\t"+w);
				System.out.print("  \t");
				for (int i = 0; i < contador; i++) {
					System.out.print(" ");
				}
				System.out.print("^");
				System.out.print(estadoAtual.nome);
				System.out.println();
				System.out.println();
			} else if (simbolos.contains(s) && regras.get(estadoAtual, "(*)") != null) {
				estadoAtual = regras.get(estadoAtual, "(*)");
				System.out.println("w:\t"+w);
				System.out.print("  \t");
				for (int i = 0; i < contador; i++) {
					System.out.print(" ");
				}
				System.out.print("^");
				System.out.print(estadoAtual.nome);
				System.out.println();
				System.out.println();
			} else {
				return false;
			}
			contador++;
		}
		
		if (estadoAtual.tipo == 3 || estadoAtual.tipo == 1)
			return true;
		
		return false;
		
	}
	
	/**
	 * controi o automato considerando o arquivo do tipo 01
	 * ARQUIVO DO TIPO 01: Cada linha contem uma informacao, 
	 * a palavra ESTADOS inidica que as proximas linha serao estados (cada estado tera um nome e um tipo, separados por um espaco,
	 * o tipo de um estado vale 0 p/ estado INICIAL c/ REJEICAO, 1 para estado de INICIAL c/ ACEITACAO, 2 para estado de REJEICAO
	 * e 3 para ACEITACAO), 
	 * a palavra SIMBOLOS indica que as proximas linhas serao simbolos
	 * e por fim a palavra REGRAS indica que as proximas linhas serao regras (um nome de estado, um simbolo e um outro
	 * nome de estado, separados por espacos).
	 * O SIMBOLO ESPECIAL PARA "OUTROS" SIMBOLOS É (*) (asteristico envolvido por dois parenteses)
	 * 
	 * @return AF
	 * @throws Exception 
	 */
	public void construir_ARQUIVO_TIPO_01(String arquivo) throws Exception {
		String ref = null;
		String linha = null;
		
		try {
			FileReader file = new FileReader(arquivo); // read a file
			BufferedReader buffer = new BufferedReader(file);
			StringTokenizer valores;
			boolean estadoInicial = false; /* booleano para verificar se o estado inicial está preenchido */
			Exception leituraException = new Exception("Erro lendo arquivo, formato inválido");
			Exception a01Exception = new Exception("O automato contem mais de um estado inicial");

			
			linha = buffer.readLine();	/* Le  a primeira linha */
			while(linha != null){
				if (linha.equals("ESTADOS") || linha.equals("SIMBOLOS") || linha.equals("REGRAS")) {
					ref = linha;
					linha = buffer.readLine();
					continue;
				}
				
				if (ref.equals("ESTADOS")) {
					valores = new StringTokenizer(linha); /* separa a String linha em valores separados por \n,\t,\r ou \f */
					if (valores.countTokens() != 2) {
						throw leituraException;
					}
					String nome = valores.nextToken();
					Integer tipo = Integer.parseInt(valores.nextToken());
					if ((tipo == 0 || tipo == 1) && estadoInicial)
						throw a01Exception;
					else
						estadoInicial = true;
					estados.add(new Estado(nome, tipo));
					
				} else if (ref.equals("SIMBOLOS")) {
					valores = new StringTokenizer(linha); /* separa a String linha em valores separados por \n,\t,\r ou \f */
					if (valores.countTokens() != 1) {
						throw leituraException;
					}
					simbolos.add(valores.nextToken());
					
				} else if (ref.equals("REGRAS")) {
					valores = new StringTokenizer(linha); /* separa a String linha em valores separados por \n,\t,\r ou \f */
					if (valores.countTokens() != 3) {
						throw leituraException;
					}
					String estadoAux1 = valores.nextToken();
					Estado estado1 = null;
					for (Estado e : estados) {
						if (e.nome.equals(estadoAux1))
							estado1 = new Estado(e);
					}
					String simboloAux = valores.nextToken();
					String simbolo = null;
					for (String e : simbolos) {
						if (e.equals(simboloAux))
							simbolo = e;
					}
					if (simboloAux.equals("(*)"))
						simbolo = simboloAux;
					String estadoAux2 = valores.nextToken();
					Estado estado2 = null;
					for (Estado e : estados) {
						if (e.nome.equals(estadoAux2))
							estado2 = new Estado(e);
					}
					if (estado1 != null & simbolo != null & estado2 != null)
						regras.put(estado1, simbolo, estado2);
					else
						throw a01Exception;
					
				} else {
					throw leituraException;
				}
				
				
				
				linha = buffer.readLine();
			}
			
			buffer.close();
		} catch (FileNotFoundException e) {
			System.out.println("Arquivo nao encontrado. Coloque-o na mesma pasta do programa.");
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			System.out.println("Nao foi possivel ler do arquivo.");
			e.printStackTrace();
			System.exit(1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(1);
		}
		
	}
	
}
