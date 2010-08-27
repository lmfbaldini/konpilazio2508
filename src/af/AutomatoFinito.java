package af;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Timer;

public class AutomatoFinito {
	public ArrayList<Estado> estados;
	public ArrayList<String> simbolos;
	/*
	 * A tabela hash de transicoes utiliza como chave uma String construida como a concatenacao de um simbolo com o nome
	 * de um estado.
	 */
	public RegraAF<Estado, String, Estado> regras;
	private String arquivoDeOrigem;
	private String saidaIndividual;
	
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
		StringBuffer saida = new StringBuffer();
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
		saida.append(imprimeSaida(w,-1,estadoAtual.nome));
		int contador = 0;
		for (String s : simbolosDaCadeia) {
			if (regras.get(estadoAtual, s) != null) {
				estadoAtual = regras.get(estadoAtual, s);
				saida.append(imprimeSaida(w,contador,estadoAtual.nome));
			} else if (simbolos.contains(s) && regras.get(estadoAtual, "(*)") != null) {
				estadoAtual = regras.get(estadoAtual, "(*)");
				saida.append(imprimeSaida(w,contador,estadoAtual.nome));
			} else {
				saida.append("O automato em questao NAO reconhece a cadeia '"+w+"', pois nao existem transicoes validas para a configuracao atual, e a cadeia nao foi totalmente consumida.");
				saidaIndividual = saida.toString();
				return false;
			}
			contador++;
		}
		
		if (estadoAtual.tipo == 3 || estadoAtual.tipo == 1) {
			saida.append("O automato em questao reconhece a cadeia '"+w+"'");
			saidaIndividual = saida.toString();
			return true;
		}
		saida.append("O automato em questao NAO reconhece a cadeia '"+w+"', pois para em um estado nao-final");
		saidaIndividual = saida.toString();
		return false;
		
	}
	
	private String imprimeSaida(String w, int contador, String nome) {
		StringBuffer saida = new StringBuffer();
		saida.append("w:\t"+w+"\n");
		if (contador == -1) {
			saida.append("Estado inicial: "+nome+"\n");
		} else {
			saida.append("  \t");
			for (int i = 0; i < contador; i++) {
				saida.append(" ");
			}
			saida.append("^"+nome+"\n\n");
		}
		return saida.toString();
		
	}

	/**
	 * controi o automato considerando o arquivo do tipo 01
	 * ARQUIVO DO TIPO 01: Cada linha contem uma informacao, 
	 * a palavra ESTADOS inidica que as proximas linha serao estados (cada estado tera um nome e um tipo, separados por um espaco,
	 * o tipo de um estado vale 0 p/ estado INICIAL NAO-FINAL, 1 para estado de INICIAL FINAL, 2 para estado de NAO-FINAL
	 * e 3 para FINAL), 
	 * a palavra SIMBOLOS indica que as proximas linhas serao simbolos
	 * e por fim a palavra REGRAS indica que as proximas linhas serao regras (um nome de estado, um simbolo e um outro
	 * nome de estado, separados por espacos).
	 * O SIMBOLO ESPECIAL PARA "OUTROS" SIMBOLOS É (*) (asteristico envolvido por dois parenteses)
	 * 
	 * @return AF
	 * @throws Exception 
	 */
	public void construirAutomato(String arquivo) throws Exception {
		this.arquivoDeOrigem = arquivo;
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
	
	/**
	 * processa um arquivo que contem a cada linha uma cadeia para ser interpretada pelo automato.
	 * ao final gera-se um arquivo de saida com a interpretacao de cada cadeia descrita.
	 * 
	 * @throws Exception 
	 */
	public void processarArquivoDeEntrada(String arquivo) throws Exception {
		String linha = null;
		StringBuffer saida = new StringBuffer();
		try {
			FileReader file = new FileReader(arquivo); // read a file
			BufferedReader buffer = new BufferedReader(file);
			
			linha = buffer.readLine();	/* Le  a primeira linha */
			while(linha != null){
				saidaIndividual = null;
				
				if(this.interpretar(linha)) {
					saida.append(linha+" VALIDA\n\n");
				} else {
					saida.append(linha+" NAO-VALIDA\n\n");
				}
				
				/*
				 * Imprime saidas individuais para cada cadeia, detalhando a execucao, passo a passo
				 */
				BufferedWriter out = new BufferedWriter(new FileWriter(arquivoDeOrigem+": "+linha)); 
				out.write(saidaIndividual); 
				System.out.println(saidaIndividual);
				out.close();
				
				linha = buffer.readLine();
			}
			BufferedWriter outFinal = new BufferedWriter(new FileWriter("saida de "+arquivoDeOrigem+" para as entradas em "+arquivo)); 
			outFinal.write(saida.toString()); 
			System.out.println(saida.toString());
			outFinal.close();
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
