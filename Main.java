import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
    	
    	Stage splash = new Stage();
    	Parent parent;
    	FXMLLoader loader = new FXMLLoader (getClass().getResource("view/SplashScreen.fxml"));
    	parent = loader.load();
    	splash.setScene(new Scene(parent));
    	splash.initStyle(StageStyle.UNDECORATED);
    	splash.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}