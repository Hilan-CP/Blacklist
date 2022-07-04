package gui;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import model.Arquivo;

public class FileController implements Initializable{
	private Arquivo arquivo;
	private List<String> linhas = new ArrayList<>();
	private List<String> colunas = new ArrayList<>();
	
	@FXML
	private Label caminhoLabel;
	
	@FXML
	private ComboBox<String> colunaComboBox;
	
	@FXML
    private ListView<String> amostra;
	
	@FXML
	private CheckBox cabecalhoCheckBox;
	
	@FXML
	private Button cancelarButton;
	
	@FXML
	private Button adicionarButton;
	
	@FXML
	public void cancelarButtonAction(ActionEvent event) {
		arquivo = null;
		Utils.getElementWindow(event).close();
	}
	
	@FXML
	public void adicionarButtonAction(ActionEvent event) {
		String caminho = caminhoLabel.getText();
		Integer coluna = null;
		if(colunas.size() > 1) {
			coluna = colunaComboBox.getSelectionModel().getSelectedIndex();
		}
		boolean cabecalho = cabecalhoCheckBox.isSelected();
		
		arquivo = new Arquivo(caminho, coluna, cabecalho);
		
		Utils.getElementWindow(event).close();
	}
	
	@FXML
	public void cabecalhoCheckBoxAction() {
		String[] col = null;
		
		if(linhas.get(0).contains(";")){
			col = linhas.get(0).split(";");
		}
		
		colunas.clear();
		
		if(cabecalhoCheckBox.isSelected()){
			if(col != null){
				for(int i = 0; i < col.length; ++i){
					colunas.add(col[i]);
				}
			}
			else{
				colunas.add(linhas.get(0));
			}
		}
		else{
			if(col != null){
				for(int i = 1; i <= col.length; ++i){
					colunas.add("Coluna" + i);
				}
			}
			else{
				colunas.add("Coluna1");
			}
		}
		
		preencherComboBox();
		preencherListView();
	}
	
	public void abrirArquivo() {
		try(BufferedReader br = new BufferedReader(new FileReader(caminhoLabel.getText()))){
			String linha = br.readLine();
			while(linha != null && linhas.size() <= 10) {
				linhas.add(linha);
				linha = br.readLine();
			}
			
			//preencher itens após a leitura da amostra
			cabecalhoCheckBoxAction();
		}
		catch(IOException e) {
			Utils.showAlert("Erro ao ler arquivo", e.getMessage(), AlertType.ERROR);
		}
	}
	
	public void preencherComboBox() {
		ObservableList<String> obsList = FXCollections.observableArrayList(colunas);
		colunaComboBox.setItems(obsList);
		colunaComboBox.getSelectionModel().select(0);
	}
	
	public void preencherListView() {
		ObservableList<String> obsList = FXCollections.observableArrayList(linhas);
		
		if(!cabecalhoCheckBox.isSelected()) {
			String tempColunas = colunas.get(0);
			for(int i = 1; i < colunas.size(); ++i) {
				tempColunas = tempColunas + ";" + colunas.get(i);
			}
			obsList.add(0, tempColunas);
		}
		
		amostra.setItems(obsList);
	}

	public void setCaminhoLabel(String caminho) {
		caminhoLabel.setText(caminho);
	}
	
	public Arquivo getArquivo() {
		return arquivo;
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {

	}
}
