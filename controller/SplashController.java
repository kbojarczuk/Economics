package controller;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SplashController implements Initializable {
	
	@FXML AnchorPane anchorPane;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		new SplashScreen().start();
	}
	
	class SplashScreen extends Thread {

		@Override
		public void run(){
			try{
				Thread.sleep(3000);
				Platform.runLater(new Runnable() {

				@Override
				public void run() {
						
				   Stage GUI = new Stage();
				   Parent parent = null;
				   FXMLLoader loader = new FXMLLoader (getClass().getResource("/view/"+ "GUI.fxml"));
				   try {
					parent = loader.load();
				 } catch (IOException e) {
					e.printStackTrace();
				 }
				   GUI.setMinHeight(650.0);
				   GUI.setMinWidth(700.0);
				   GUI.setTitle("Macro-Economics");
				   GUI.setScene(new Scene(parent));	
				   GUI.show();
				   anchorPane.getScene().getWindow().hide();	
			    }	
				});
		 } catch (InterruptedException ex){
			 Logger.getLogger(SplashController.class.getName()).log(Level.SEVERE, null, ex);
		 }
	   } 
	}

}
