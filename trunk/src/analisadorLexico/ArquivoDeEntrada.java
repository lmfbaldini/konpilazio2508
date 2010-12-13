package analisadorLexico;

import java.io.*;

public class ArquivoDeEntrada {
	int MAX_READ_AHEAD = 2500;
	String nomeArquivo;
	FileInputStream fstream;
	DataInputStream in;
	BufferedReader br;
	BufferedReader brAux;

	public ArquivoDeEntrada(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}
	
	public void open() {
		try{
			fstream = new FileInputStream(nomeArquivo);
			in = new DataInputStream(fstream);
			br = new BufferedReader(new InputStreamReader(in));
			
		}catch (Exception e){//Catch exception if any
			System.err.println("Erro ao abrir o arquivo: " + e.getMessage());
		}
	}
	
	public void close() {
		try {
			in.close();
		} catch (Exception e) {
			System.err.println("Erro ao fechar o arquivo: " + e.getMessage());
		}
	}
	
	public String readLine() {
		String strLine = null;
		try {
			strLine = br.readLine();
		} catch (Exception e) {
			System.err.println("Erro ao ler o arquivo: " + e.getMessage());
		}
		return strLine;
	}

	public String peekLine() {
		String strLine = null;
		try {
			br.mark(MAX_READ_AHEAD);
			strLine = br.readLine();
			br.reset();
		} catch (IOException e) {
			System.err.println("Erro ao ler o arquivo: " + e.getMessage());
		}
		return strLine;
	}
	
	public char readChar() {
		char c = 0;
		try {
			c = (char) br.read();
		} catch (IOException e) {
			System.err.println("Erro ao ler o arquivo: " + e.getMessage());
		}
		return c;
	}

	public char peekChar() {
		char c = 0;
		try {
			br.mark(5);
			c = (char) br.read();
			br.reset();
		} catch (IOException e) {
			System.err.println("Erro ao ler o arquivo: " + e.getMessage());
		}
		return c;
	}
	
}
