package src;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Untitled - Ultimate Text Editor 9000");

        // Text Area
        CodeArea codeArea = new CodeArea();
        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));

        BorderPane layout = new BorderPane();
        layout.setCenter(codeArea);

        Scene scene = new Scene(layout, 500, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
