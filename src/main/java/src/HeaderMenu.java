package src;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.tika.exception.TikaException;
import org.fxmisc.richtext.CodeArea;

import java.io.File;
import java.io.IOException;


public class HeaderMenu
{

    private MenuBar menu;
    private Menu fileMenu;
    private MenuItem newFile;
    private MenuItem openFile;
    private MenuItem saveFile;
    private MenuItem printFile;
    private MenuItem exportFile;
    private Menu helpMenu;
    private MenuItem about;

    public HeaderMenu()
    {
        this.menu = new MenuBar();
        this.fileMenu = new Menu("File");
        this.newFile = new MenuItem("New");
        this.openFile = new MenuItem("Open");
        this.saveFile = new MenuItem("Save");
        this.printFile = new MenuItem("Print");
        this.exportFile = new MenuItem("Export as PDF");
        this.helpMenu = new Menu("Help");
        this.about = new MenuItem("About");

        fileMenu.getItems().addAll(newFile, openFile, saveFile, printFile, exportFile);
        helpMenu.getItems().addAll(about);
        menu.getMenus().addAll(fileMenu, helpMenu);

        // Setting actions
        newFile.setOnAction(event ->
        {
            CodeArea editor = getEditor();

            // Clearing editor and clearing undo history
            editor.clear();
            editor.getUndoManager().forgetHistory();

        });

        openFile.setOnAction(event ->
        {

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open a File");
            fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("OpenOffice Text", "*.odt"),
                new FileChooser.ExtensionFilter("All", "*.*"));

            File selectedFile = fileChooser.showOpenDialog(getEditor().getScene().getWindow());

            // If user presses cancel or closes dialog return null to cancel loading process
            if(selectedFile == null)
            {
                return;
            }

            String toSet = null;
            try
            {
                toSet = new FileIO().Open(selectedFile);
            } catch (IOException | TikaException e)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error attempting to load file");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
                return;
            }

            CodeArea editor = getEditor();

            editor.clear();
            editor.appendText(toSet);
            editor.getUndoManager().forgetHistory();
        });

        saveFile.setOnAction(event ->
        {
            // Setting up file chooser
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save File");
            fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"));

            File selectedFile = fileChooser.showSaveDialog(getEditor().getScene().getWindow());

            if (selectedFile == null)
            {
                return;
            }
            try
            {
                new FileIO().Save(getEditor().getText(), selectedFile);
            } catch (IOException e)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error attempting to save");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        });

        printFile.setOnAction(event ->
        {
            Print print = new Print();
            if (print.setPrintSettings(getEditor().getScene().getWindow()))
            {
                print.print(getEditor().getScene().getWindow(), getEditor().getText());
            }
        });

        about.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent event)
            {
                try
                {
                    VBox root = new VBox();
                    root.setAlignment(Pos.TOP_CENTER);
                    Label title = new Label("Ultimate Text Editor 9000");
                    title.setFont(new Font(20.0));
                    title.setPadding(new Insets(10, 0, 5, 0));
                    Label createdBy = new Label("Creators:\nJurgen\nSam Siggs");
                    createdBy.setFont(new Font(12.0));
                    Label description = new Label("breif message");
                    description.setPadding(new Insets(10, 0, 0, 0));

                    root.getChildren().addAll(title, createdBy, description);

                    Stage stage = new Stage();
                    stage.setTitle("Ultimate Text Editor 9000");
                    stage.setScene(new Scene(root, 325, 150));
                    stage.show();
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });

        exportFile.setOnAction(event ->
        {
            Export export = new Export();
            FileChooser fileChooser = new FileChooser();
            File file =  fileChooser.showSaveDialog(getEditor().getScene().getWindow());
            try
            {
                export.writePDF(getEditor().getText(), file);
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        });
    }

    public MenuBar getMenu()
    {
        return menu;
    }

    // Helper Function to retrieve editor
    private CodeArea getEditor()
    {
        BorderPane bp = (BorderPane) menu.getParent().getParent();
        return (CodeArea) bp.getCenter();
    }
}
