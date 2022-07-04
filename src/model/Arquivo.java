package model;

public class Arquivo {
	private String caminho;
	private Integer coluna;
	private boolean cabecalho;
	
	public Arquivo(String caminho, Integer coluna, boolean cabecalho) {
		this.caminho = caminho;
		this.coluna = coluna;
		this.cabecalho = cabecalho;
	}

	public String getCaminho() {
		return caminho;
	}

	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}

	public Integer getColuna() {
		return coluna;
	}

	public void setColuna(Integer coluna) {
		this.coluna = coluna;
	}

	public boolean getCabecalho() {
		return cabecalho;
	}

	public void setCabecalho(Boolean cabecalho) {
		this.cabecalho = cabecalho;
	}

	@Override
	public String toString() {
		return "Arquivo [caminho=" + caminho + ", coluna=" + coluna + ", cabecalho=" + cabecalho + "]";
	}
}
