module Blacklist {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.fxml;
	requires javafx.base;
	
	opens application to javafx.graphics, javafx.fxml;
	
	//permitir que o jaxafx.fxml consiga ver as classes controller do pacote gui
	opens gui to javafx.fxml;
}
