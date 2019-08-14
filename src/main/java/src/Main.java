package src;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        // primaryStage Stuff
        primaryStage.setTitle("Untitled - Ultimate Text Editor 9000");
        BorderPane layout = new BorderPane();
        Scene scene = new Scene(layout, 500, 800);

        // Menu
        MenuMain menu = new MenuMain();

        // Text Area
        CodeArea codeArea = new CodeArea();
        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));



        // Layouts for borderpane - feel free to change
        // Top BorderPane
        VBox topPane = new VBox();
        topPane.getChildren().addAll(menu.getMenu());
        // Bottom BorderPane
        VBox bottomPane = new VBox();
        // Left BorderPane
        VBox leftPane = new VBox();
        // Right BorderPane
        VBox rightPane = new VBox();

        // Layout Setters
        layout.setCenter(codeArea);
        layout.setTop(topPane);
        layout.setBottom(bottomPane);
        layout.setLeft(leftPane);
        layout.setRight(rightPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
