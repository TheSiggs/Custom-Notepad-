package src;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Untitled - Ultimate Text Editor 9000");


        BorderPane layout = new BorderPane();
        Scene scene = new Scene(layout, 500, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }



}
