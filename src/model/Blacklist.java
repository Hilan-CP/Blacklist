package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Blacklist {
	private Set<String> telefone = new TreeSet<>();
	private List<Arquivo> arquivos = new ArrayList<>();
	
	public List<Arquivo> getArquivos(){
		return arquivos;
	}
	
	public void addArquivo(Arquivo arq) {
		arquivos.add(arq);
	}
	
	public void removeArquivo(int index) {
		arquivos.remove(index);
	}
	
	public void importarArquivos() {
		for(Arquivo arq : arquivos) {
			try(BufferedReader br = new BufferedReader(new FileReader(arq.getCaminho()))){
				String linha = br.readLine();
				String campo;
				
				//se possui cabeçalho, então pule para a segunda linha
				if(arq.getCabecalho() == true) {
					linha = br.readLine();
				}
				
				if(arq.getColuna() != null) {
					while(linha != null) {
						campo = linha.split(";")[arq.getColuna()];
						telefone.add(campo);
						linha = br.readLine();
					}
				}
				else {
					while(linha != null) {
						telefone.add(linha);
						linha = br.readLine();
					}
				}
			}
			catch(IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	public void exportarTudo(String destino) {
		destino = destino + "\\Blacklist_Total.txt";
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(destino))){
			for(String t : telefone) {
				bw.write(t);
				bw.newLine();
			}
		}
		catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void exportarSeparado(String destino) throws IOException {
		int nArquivo = 1;
		int cont = 1;
		String arq = destino + "\\blacklist_" + nArquivo + ".txt";
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(arq));
		
		for (String t : telefone) {
			if(cont > 500000) {
				cont = 1;
				bw.close();
				nArquivo++;
				arq = destino + "\\blacklist_" + nArquivo + ".txt";
				bw = new BufferedWriter(new FileWriter(arq));
			}
			
			bw.write(t);
			bw.newLine();
			cont++;
		}
		bw.close();
	}
}
