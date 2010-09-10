package automatoFinitoEstruturado;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;
import java.util.StringTokenizer;

import automatoFinito.Estado;
import automatoFinito.RegraAF;

/**M = (Q, A, S, G, P, q0, z0, F)
 * Q: ArrayList<Estado> estados
 * S: ArrayList<Estado> simbolos
 * P: RegraAF<Estado, String, Estado> regras
 * q0: Estado identificado como inicial em ArrayList<Estado> estados
 * F: Estados identificados como finais em ArrayList<Estado> estados
 * A: 
 * 
 * @author Baldini
 *
 */
public class AutomatoFinitoEstruturado {
	public ArrayList<Estado> estados;
	public ArrayList<String> simbolos;
	public ArrayList<AutomatoFinitoEstruturado> submaquinas;
	public PilhaAF_E<AutomatoFinitoEstruturado,Estado> pilha;
	/*
	 * A tabela hash de transicoes utiliza como chave uma String construida como a concatenacao de um simbolo com o nome
	 * de um estado.
	 */
	public RegraAF<Estado, String, Estado> regras;
	public String nome = null; //NOME DESTA MAQUINA
	private Integer tipo = null;
	private String arquivoDeOrigem;
	private String saidaIndividual;
	
	public AutomatoFinitoEstruturado() {
		estados = new ArrayList<Estado>();
		simbolos = new ArrayList<String>();
		regras = new RegraAF<Estado, String, Estado>();
		submaquinas = new ArrayList<AutomatoFinitoEstruturado>();
		pilha = new PilhaAF_E<AutomatoFinitoEstruturado, Estado>();
		this.clear();
	}
	
	public void clear() {
		estados.clear();
		simbolos.clear();
		regras.clear();
		submaquinas.clear();
		pilha.clear();
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
	 * O SIMBOLO ESPECIAL PARA "OUTROS" SIMBOLOS EH (*) (asteristico envolvido por dois parenteses)
	 * O SIMBOLO ESPECIAL PARA TRANSICOES EM VAZIO EH (@)
	 * 
	 * @return AF
	 * @throws Exception 
	 */
	public void construir(String arquivo) throws Exception {
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
			if (linha.equals("SUBMAQUINAS")) {
				linha = buffer.readLine();
				while (!linha.equals("---SUB---")) {
					valores = new StringTokenizer(linha); /* separa a String linha em valores separados por \n,\t,\r ou \f */
					if (valores.countTokens() != 1) {
						throw leituraException;
					}
					submaquinas.add(new AutomatoFinitoEstruturado());
					if (submaquinas.size() == 1) {
						this.tipo = 0;
						this.nome = valores.nextToken();
						submaquinas.set(submaquinas.size()-1, this);
					}
					else {
						submaquinas.get(submaquinas.size()-1).tipo = 1;
						submaquinas.get(submaquinas.size()-1).nome = valores.nextToken();
					}
					linha = buffer.readLine();
				}
			}
			linha = buffer.readLine();
			String maquinaAtual = linha;
			linha = buffer.readLine();
			while(!linha.equals("---EOF---") && linha != null){
				if (linha.equals("ESTADOS") || linha.equals("SIMBOLOS") || linha.equals("REGRAS")) {
					ref = linha;
					linha = buffer.readLine();
					continue;
				}
				
				if (linha.equals("---SUB---")) {
					estadoInicial = false;
					maquinaAtual = (linha = buffer.readLine());
					linha = buffer.readLine();
					continue;
				}
				
				if (ref.equals("ESTADOS")) {
					valores = new StringTokenizer(linha); /* separa a String linha em valores separados por ' ',\n,\t,\r ou \f */
					if (valores.countTokens() != 2) {
						throw leituraException;
					}
					String nome = valores.nextToken();
					Integer tipo = Integer.parseInt(valores.nextToken());
					if (submaquinas.get(0).nome.equals(maquinaAtual)) {
						estados.add(new Estado(nome, tipo));
						submaquinas.set(0, this);
					} else {
						for (AutomatoFinitoEstruturado a : submaquinas) {
							if (a.nome.equals(maquinaAtual)) {
								a.estados.add(new Estado(nome, tipo));
							}
						}
					}
					
				} else if (ref.equals("SIMBOLOS")) {
					valores = new StringTokenizer(linha); /* separa a String linha em valores separados por \n,\t,\r ou \f */
					if (valores.countTokens() != 1) {
						throw leituraException;
					}
					String simb = valores.nextToken();
					if (submaquinas.get(0).nome.equals(maquinaAtual)) {
						simbolos.add(simb);
						submaquinas.set(0, this);
					} else {
						for (AutomatoFinitoEstruturado a : submaquinas) {
							if (a.nome.equals(maquinaAtual)) {
								a.simbolos.add(simb);
							}
						}
					}
					
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
					if (simboloAux.equals("(@)"))
						simbolo = simboloAux;
					for (AutomatoFinitoEstruturado a : submaquinas) {
						if (a.nome.equals(simboloAux))
							simbolo = simboloAux;
					}
					if (simbolo == null && !submaquinas.get(0).nome.equals(maquinaAtual)) {
						for (AutomatoFinitoEstruturado a : submaquinas) {
							if (a.nome.equals(maquinaAtual)) {
								for (String e : a.simbolos) {
									if (e.equals(simboloAux))
										simbolo = simboloAux;
								}
							}
						}
					}
					String estadoAux2 = valores.nextToken();
					Estado estado2 = null;
					for (Estado e : estados) {
						if (e.nome.equals(estadoAux2))
							estado2 = new Estado(e);
					}
					if (estado1 != null & simbolo != null & estado2 != null) {
						if (submaquinas.get(0).nome.equals(maquinaAtual)) {
							regras.put(estado1, simbolo, estado2);
							submaquinas.set(0, this);
						} else {
							for (AutomatoFinitoEstruturado a : submaquinas) {
								if (a.nome.equals(maquinaAtual)) {
									a.regras.put(estado1, simbolo, estado2);
								}
							}
						}
					}
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
	
	public int interpretar(String w, String wOriginal, ArrayList<AutomatoFinitoEstruturado> submaquinasIn, StringBuffer saida2, boolean sub, int refCadeia) {
		Estado estadoAtual = null;
		ArrayList<String> simbolosDaCadeiaAux = new ArrayList<String>();
		StringBuffer saida = new StringBuffer();
		ArrayList<String> simbolosDaCadeia = new ArrayList<String>();
		char[] Saux = w.toCharArray();
		for (char d : Saux) {
			String S = String.valueOf(d);
			simbolosDaCadeiaAux.add(S);
		}
		Saux = wOriginal.toCharArray();
		for (char d : Saux) {
			String S = String.valueOf(d);
			simbolosDaCadeia.add(S);
		}
		if (sub == true) {
			saida.append(saida2);
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
		boolean retorno = false;
		while (simbolosDaCadeiaAux.size() != 0) {
			String s = simbolosDaCadeiaAux.get(0);
			if (regras.get(estadoAtual, s) != null) {
				saida.append(imprimeSaida(wOriginal,s,contador+refCadeia,estadoAtual,regras.get(estadoAtual, s).getFirst(), 0,null));
				estadoAtual = regras.get(estadoAtual, s).getFirst();
				simbolosDaCadeiaAux.remove(0);
			} else if (simbolos.contains(s) && regras.get(estadoAtual, "(*)") != null) {
				saida.append(imprimeSaida(wOriginal,s,contador+refCadeia,estadoAtual,regras.get(estadoAtual, "(*)").getFirst(), 0,null));
				estadoAtual = regras.get(estadoAtual, "(*)").getFirst();
				simbolosDaCadeiaAux.remove(0);
			} else if (simbolos.contains(s) && regras.get(estadoAtual, "(@)") != null) {
				saida.append(imprimeSaida(wOriginal,s,contador+refCadeia,estadoAtual,regras.get(estadoAtual, "(@)").getFirst(), 0,null));
				estadoAtual = regras.get(estadoAtual, "(@)").getFirst();
				contador--;
			} else {
				for (AutomatoFinitoEstruturado maq : submaquinasIn) {
					if (regras.get(estadoAtual, maq.nome) != null) {
						saida.append(imprimeSaida(wOriginal,"",-3,estadoAtual,null, -5, maq.nome));
						pilha.push(this, regras.get(estadoAtual, maq.nome).getFirst());
						saida.append(imprimePilha(pilha));
						int ret = maq.interpretar(parse(simbolosDaCadeiaAux), parse(simbolosDaCadeia), submaquinasIn, saida, true, contador+refCadeia);
						if (ret == -1) {
							if (sub == false) {
								saidaIndividual = saida.toString();
							}
							if (sub == true)
								return -1;
							else
								return 0;
						} else if (ret != 0){
							estadoAtual = pilha.pop_U(pilha.pop_T());
							saida.append(imprimeSaida(wOriginal,"",-3,estadoAtual,null, -6, maq.nome));
							saida.append(imprimePilha(pilha));
							for(int i = 0; i<ret; i++) {
								simbolosDaCadeiaAux.remove(0);
								contador++;
							}
							retorno = true;
							break;
						} else {
							estadoAtual = pilha.pop_U(pilha.pop_T());
							saida.append(imprimeSaida(wOriginal,"",-3,estadoAtual,null, -7, maq.nome));
							simbolosDaCadeiaAux.remove(0);
							retorno = true;
							return -1;
						}
					}
				}
				if (estadoAtual.tipo == 3 && sub == true) {
					return contador;
				}
				if (retorno == true) {
					if (simbolosDaCadeiaAux.size() != 0) {
						retorno = false;
						continue;
					} else if (sub == true) {
						return contador;
					} else if (simbolosDaCadeiaAux.size() == 0) {
						break;
					}
				}
				if (sub == false) {
					saida.append(imprimeSaida(wOriginal,s,contador+refCadeia,estadoAtual,null, -2, null));
					saidaIndividual = saida.toString();
				}
				return 0;
			}
			contador++;
		}
		
		if (estadoAtual.tipo == 3 || estadoAtual.tipo == 1) {
			if (sub == false) {
				saida.append(imprimeSaida(wOriginal,"",-3,estadoAtual,null, -3, null));
				saidaIndividual = saida.toString();
			}
			return contador;
		}
		if (sub == false || (estadoAtual.tipo != 3 && estadoAtual.tipo != 1)) {
			saida.append(imprimeSaida(wOriginal,"",-4,estadoAtual,null, -4, null));
			saidaIndividual = saida.toString();
		}
		return 0;

		
	}
	
	private String imprimePilha(PilhaAF_E<AutomatoFinitoEstruturado, Estado> pilha2) {
		StringBuffer aux = new StringBuffer();
		int i = 0;
		aux.append("Pilha:\n");
		
		for (String a : pilha2.pilhaToString) {
			aux.append(a+"\n");
			i++;
		}
		if (pilha.pilhaToString.isEmpty())
			aux.append("<vazia>\n");
		
		System.out.println(aux.toString());
		
		return aux.toString();
	}

	private String parse(ArrayList<String> simbolosDaCadeiaAux) {
		StringBuffer aux = new StringBuffer();
		for (String s : simbolosDaCadeiaAux)
			aux.append(s);
		return aux.toString();
	}

	private String imprimeSaida(String w, String s, int contador, Estado estadoAtual, Estado proxEstado, int tipo, String submaquina) {
		StringBuffer saida = new StringBuffer();

		if (tipo == -1) { //IMPRIME CONFIG INICIAL
			System.out.println("Configuracao inicial:");
			saida.append("Configuracao inicial:\n");
			System.out.println("w:\t"+w);
			saida.append("w:\t"+w+"\n");
			System.out.println("Estado: "+estadoAtual.nome+"\n");
			saida.append("Estado: "+estadoAtual.nome+"\n\n");
		} else if (tipo == -2) { //IMPRIME SAIDA DE FALHA POR FALTA DE REGRA
			System.out.println("Configuracao atual:");
			saida.append("Configuracao atual\n");
			System.out.println("w:\t"+w);
			saida.append("w:\t"+w+"\n");
			System.out.print("  \t");
			saida.append("  \t");
			for (int i = 0; i < contador; i++) {
				System.out.print(" ");
				saida.append(" ");
			}
			System.out.println("^"+estadoAtual.nome);
			saida.append("^"+estadoAtual.nome+"\n");
			
			System.out.println("Nao existe uma regra para a configuracao atual ("+estadoAtual.nome+", "+s+")");
			saida.append("Nao existe uma regra para a configuracao atual ("+estadoAtual.nome+", "+s+")\n");
		}  else if (tipo == -3) { //IMPRIME SAIDA DE SUCESSO
			System.out.println("A cadeia foi inteiramente consumida e o estado atual ("+estadoAtual.nome+") eh FINAL");
			saida.append("A cadeia foi inteiramente consumida e o estado atual ("+estadoAtual.nome+") eh FINAL\n");			
		
		}  else if (tipo == -4) { //IMPRIME SAIDA DE FALHA POR ESTADO FINAL NAO-FINAL
			System.out.println("A cadeia foi inteiramente consumida contudo o estado atual ("+estadoAtual.nome+") eh NAO-FINAL");
			saida.append("A cadeia foi inteiramente consumida contudo o estado atual ("+estadoAtual.nome+") eh NAO-FINAL\n");	
			
		}  else if (tipo == -5) { //ENTRADA DE SUBMAQUINA
			System.out.println("Iniciando chamada de submaquina no estado: "+estadoAtual.nome+". Submaquina: "+submaquina);
			saida.append("Iniciando chamada de submaquina no estado: "+estadoAtual.nome+". Submaquina: "+submaquina+"\n");	
			
		}  else if (tipo == -6) { //SAIDA DE SUBMAQUINA
			System.out.println("Retorno de chamada de submaquina no estado: "+estadoAtual.nome+". Submaquina: "+submaquina);
			saida.append("Retorno de chamada de submaquina no estado: "+estadoAtual.nome+". Submaquina: "+submaquina+"\n");	
			
		}  else if (tipo == -7) { //FALHA DE SUBMAQUINA
			System.out.println("A submaquina "+submaquina+" nao aceitou a cadeia em questao");
			saida.append("A submaquina "+submaquina+" nao aceitou a cadeia em questao\n");	
			
		} else {
			System.out.println("Configuracao atual:");
			saida.append("Configuracao atual\n");
			System.out.println("w:\t"+w);
			saida.append("w:\t"+w+"\n");
			System.out.print("  \t");
			saida.append("  \t");
			for (int i = 0; i < contador; i++) {
				System.out.print(" ");
				saida.append(" ");
			}
			System.out.println("^"+estadoAtual.nome);
			saida.append("^"+estadoAtual.nome+"\n");
			
			if (regras.get(estadoAtual, s) != null) {
				System.out.println("Transicao utilizada: ("+estadoAtual.nome+", "+s+")->"+proxEstado.nome);
				System.out.println();
				saida.append("Transicao utilizada: ("+estadoAtual.nome+", "+s+")->"+proxEstado.nome+"\n\n");
			} else if (simbolos.contains(s) && regras.get(estadoAtual, "(*)") != null) {
				System.out.println("Transicao utilizada: ("+estadoAtual.nome+", (*))->"+proxEstado.nome);
				System.out.println();
				saida.append("Transicao utilizada: ("+estadoAtual.nome+", (*))->"+proxEstado.nome+"\n\n");
			} else if (simbolos.contains(s) && regras.get(estadoAtual, "(@)") != null) {
				System.out.println("Transicao utilizada: ("+estadoAtual.nome+", (@))->"+proxEstado.nome);
				System.out.println();
				saida.append("Transicao utilizada: ("+estadoAtual.nome+", (@))->"+proxEstado.nome+"\n\n");
			}

		}
	

		
		return saida.toString();
		
	}
	
	/**
	 * processa um arquivo que contem a cada linha uma cadeia para ser interpretada pelo automato.
	 * ao final gera-se um arquivo de saida com a interpretacao de cada cadeia descrita.
	 * 
	 * @throws Exception 
	 */
	public void processarArquivoDeEntrada(String arquivo, boolean verbose) throws Exception {
		String linha = null;
		StringBuffer saida = new StringBuffer();
		try {
			FileReader file = new FileReader(arquivo); // read a file
			BufferedReader buffer = new BufferedReader(file);
			
			System.out.println("Inciando processamento do arquivo: "+arquivo);
			
			System.out.println("Saidas obtidas:");
			
			linha = buffer.readLine();	/* Le  a primeira linha */
			while(linha != null){
				saidaIndividual = null;
				
				if(this.interpretar(linha,linha,this.submaquinas, null, false, 0) > 0) {
					System.out.println(linha+" VALIDA");
					System.out.println("----------------------------------------------------------------------------");
					saida.append(linha+" VALIDA\n----------------------------------------------------------------------------\n");
				} else {
					System.out.println(linha+" NAO-VALIDA");
					System.out.println("----------------------------------------------------------------------------");
					saida.append(linha+" NAO-VALIDA\n----------------------------------------------------------------------------\n");
				}
				
				/*
				 * Imprime saidas individuais para cada cadeia, detalhando a execucao, passo a passo
				 
				if (verbose) {
					BufferedWriter out = new BufferedWriter(new FileWriter(arquivoDeOrigem+": "+linha)); 
					out.write(saidaIndividual); 
					out.close();
				}*/
				
				linha = buffer.readLine();
			}
			if (verbose) {
				BufferedWriter out = new BufferedWriter(new FileWriter("saida de "+arquivoDeOrigem+" para as entradas em "+arquivo)); 
				out.write(saida.toString()); 
				out.close();
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
	
	public String toString() {
		return this.nome;
	}
	
}
