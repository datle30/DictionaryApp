package program;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxml/HomeGui.fxml")));
            Scene scene = new Scene(root);
            String css = Objects.requireNonNull(this.getClass().getResource("/GUI.css")).toExternalForm();
            scene.getStylesheets().add(css);
            primaryStage.setScene(scene);
            primaryStage.show();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
