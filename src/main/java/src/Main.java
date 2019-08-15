package src;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;

public class Main extends Application
{
    private CodeArea editor;
    private BorderPane layout;

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        // primaryStage Stuff
        primaryStage.setTitle("Untitled - Ultimate Text Editor 9000");
        layout = new BorderPane();
        Scene scene = new Scene(layout, 500, 800);

        // Menu
        MenuMain menu = new MenuMain();

        // Editor
        editor = new CodeArea();
        editor.setParagraphGraphicFactory(LineNumberFactory.get(editor));

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
        layout.setCenter(editor);
        layout.setTop(topPane);
        layout.setBottom(bottomPane);
        layout.setLeft(leftPane);
        layout.setRight(rightPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void refreshEditor()
    {
        // Set new code editor to avoid undo reverting a new file to previous
        this.editor = new CodeArea();
        this.editor.setParagraphGraphicFactory(LineNumberFactory.get(editor));
        this.layout.setCenter(this.editor);
    }

    private void setEditor(String string)
    {
        // Load a files contents into the editor
        refreshEditor();
        this.editor.appendText(string);
    }
}
