package src;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;

public class Main extends Application
{

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        // primaryStage Stuff
        primaryStage.setTitle("Untitled - Ultimate Text Editor 9000");
        BorderPane layout = new BorderPane();
        Scene scene = new Scene(layout, 500, 800);

        // CSS
        String css = this.getClass().getResource("Style.css").toExternalForm();
        scene.getStylesheets().add(css);

        // Header
        HeaderMenu menu = new HeaderMenu();

        // Editor
        CodeArea editor = new CodeArea();
        editor.setId("editor");
        editor.setWrapText(true);
        editor.setParagraphGraphicFactory(LineNumberFactory.get(editor));
        
        // Footer
        FooterMenu footer = new FooterMenu();
        // Updates carret location every 100 milliseconds
        Task task = new Task<Void>() {
            @Override
            public Void call() throws Exception {
                int i = 0;
                while (true) {
                    final int finalI = i;
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            footer.setCurserLocation(editor.getCurrentParagraph(), editor.getCaretColumn());
                        }
                    });
                    i++;
                    Thread.sleep(100);
                }
            }
        };
        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();

        // Layouts for borderpane - feel free to change
        // Top BorderPane
        VBox topPane = new VBox();
        topPane.getChildren().addAll(menu.getMenu());
        // Bottom BorderPane
        VBox bottomPane = new VBox();
        bottomPane.getChildren().addAll(footer.getFooter());
        // Left BorderPane
        VBox leftPane = new VBox();
        // Right BorderPane
        VBox rightPane = new VBox();

        // Layout Setters
        layout.setCenter(new VirtualizedScrollPane<>(editor));
        layout.setTop(topPane);
        layout.setBottom(bottomPane);
        layout.setLeft(leftPane);
        layout.setRight(rightPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
