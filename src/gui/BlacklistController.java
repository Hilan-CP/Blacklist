package gui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Arquivo;
import model.Blacklist;

public class BlacklistController implements Initializable{
	private Blacklist blacklist = new Blacklist();
	
	@FXML
	private Button procurarButton;
	
	@FXML
	private Button removerButton;
	
	@FXML
	private Button adicionarButton;
	
	@FXML
	private Button processarButton;
	
	@FXML
	private Button destinoButton;
	
	@FXML
	private TextField caminhoTextField;
	
	@FXML
	private TextField destinoTextField;
	
	@FXML
	private ListView<Arquivo> arquivosListView;

	@FXML
	public void procurarButtonAction(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Selecione o arquivo");
		
		File file = fileChooser.showOpenDialog(Utils.getElementWindow(event));
		
		if(file != null) {
			caminhoTextField.setText(file.getAbsolutePath());
		}
	}
	
	@FXML
	public void destinoButtonAction(ActionEvent event){
		DirectoryChooser dirChooser = new DirectoryChooser();
		
		File dir = dirChooser.showDialog(Utils.getElementWindow(event));
		
		if(dir != null) {
			destinoTextField.setText(dir.getAbsolutePath());
		}
	}

	@FXML
	public void removerButtonAction(ActionEvent event) {
		int index = arquivosListView.getSelectionModel().getSelectedIndex();
		blacklist.removeArquivo(index);
		preencherArquivosListView();
	}
	
	@FXML
	public void adicionarButtonAction(ActionEvent event) {
		if(caminhoTextField.getText().equals("")) {
			Utils.showAlert("Erro", "Nenhum arquivo selecionado.", AlertType.ERROR);
		}
		else {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/FileDialog.fxml"));
				Pane pane = loader.load();
				
				FileController fileController = loader.getController();
				fileController.setCaminhoLabel(caminhoTextField.getText());
				fileController.abrirArquivo();

				Stage fileDialog = new Stage();
				fileDialog.setResizable(false);
				fileDialog.setTitle("Estrutura do arquivo");
				fileDialog.setScene(new Scene(pane));
				fileDialog.initOwner(Utils.getElementWindow(event));
				fileDialog.initModality(Modality.WINDOW_MODAL);
				fileDialog.showAndWait();
				
				Arquivo arquivo = fileController.getArquivo();
				if(arquivo != null) {
					blacklist.addArquivo(arquivo);
					preencherArquivosListView();
				}
			}
			catch(IOException e) {
				Utils.showAlert("Erro", e.getMessage(), AlertType.ERROR);
				e.printStackTrace();
			}
		}
	}
	
	@FXML
	public void processarButtonAction(ActionEvent event) {
		if(destinoTextField.getText().strip().equals("")) {
			Utils.showAlert("Aviso", "Nenhuma pasta de destino selecionada.", AlertType.WARNING);
		}
		else {
			try {
				blacklist.importarArquivos();
				blacklist.validarTelefone();
				blacklist.exportarTudo(destinoTextField.getText());
				blacklist.exportarSeparado(destinoTextField.getText());
				blacklist.clearTelefoneSet();
				Utils.showAlert("Finalizado", "A exportação finalizada com sucesso.", AlertType.INFORMATION);
			}
			catch(IOException e) {
				Utils.showAlert("Erro ao exportar arquivo", e.getMessage(), AlertType.ERROR);
			}
			catch (RuntimeException e) {
				Utils.showAlert("Erro inesperado", e.getMessage(), AlertType.ERROR);
			}
		}
	}
	
	public void preencherArquivosListView() {
		ObservableList<Arquivo> obsList = FXCollections.observableArrayList(blacklist.getArquivos());
		arquivosListView.setItems(obsList);
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		arquivosListView.setCellFactory(new Callback<ListView<Arquivo>, ListCell<Arquivo>>(){
			@Override
			public ListCell<Arquivo> call(ListView<Arquivo> list) {
				ListCell<Arquivo> cell = new ListCell<>() {
					@Override
					protected void updateItem(Arquivo item, boolean empty) {
						super.updateItem(item, empty);
						if(empty || item == null) {
							setText(null);
							setGraphic(null);
						}
						else {
							setText(item.getCaminho());
						}
					}
				};
				return cell;
			}
			
		});
	}
}
