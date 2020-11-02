package MVC;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This is the class for Main,
 * This is where our program will run
 * 
 * @author Andy Rivera and Joseph Shamma
 *
 */

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("TransactionManager.fxml"));
        primaryStage.setTitle("TransactionManager");
        primaryStage.setScene(new Scene(root, 600, 500));
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}