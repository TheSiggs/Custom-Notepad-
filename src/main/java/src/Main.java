package src;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
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




        // Layouts for borderpane - feel free to change
        VBox topPane = new VBox();
        topPane.getChildren().addAll(new MenuMain().getMenu());

        VBox bottomPane = new VBox();
        VBox leftPane = new VBox();
        VBox rightPane = new VBox();

        layout.setTop(topPane);
        layout.setBottom(bottomPane);
        layout.setLeft(leftPane);
        layout.setRight(rightPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
