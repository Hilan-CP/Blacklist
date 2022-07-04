package gui;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class Utils {
	public static Stage getElementWindow(ActionEvent event) {
		return (Stage) ((Node) event.getSource()).getScene().getWindow();
	}
	
	public static void showAlert(String titulo, String texto, AlertType tipo) {
		Alert alerta = new Alert(tipo);
		alerta.setContentText(texto);
		alerta.setHeaderText(null);
		alerta.setTitle(titulo);
		alerta.show();
	}
}
