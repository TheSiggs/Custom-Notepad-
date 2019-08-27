package src;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.commons.io.FilenameUtils;
import org.apache.tika.exception.TikaException;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.NavigationActions;
import org.xml.sax.SAXException;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class HeaderMenu
{

    private MenuBar menu; // Parent Menu
    private Stage mainStage;
    private CodeArea mainEditor;

    public HeaderMenu(Stage passedStage, CodeArea passedEditor)
    {
        this.mainStage = passedStage;
        this.mainEditor = passedEditor;
        // Parent Menu
        this.menu = new MenuBar();
        // File Menu
        Menu fileMenu = new Menu("File");
        MenuItem newFile = new MenuItem("New");
        MenuItem openFile = new MenuItem("Open");
        MenuItem saveFile = new MenuItem("Save");
        MenuItem printFile = new MenuItem("Print");
        MenuItem exportFile = new MenuItem("Export as PDF");
        MenuItem quitFile = new MenuItem("Quit");
        // Edit Menu
        Menu editMenu = new Menu("Edit");
        MenuItem timeEdit = new MenuItem("Insert Time and Date");
        MenuItem undoEdit = new MenuItem("Undo");
        MenuItem redoEdit = new MenuItem("Redo");
        MenuItem copyEdit = new MenuItem("Copy");
        MenuItem cutEdit = new MenuItem("Cut");
        MenuItem pasteEdit = new MenuItem("Paste");
        // Search Menu
        Menu searchMenu = new Menu("Search");
        MenuItem findSearch = new MenuItem("Find");
        // View Menu
        Menu viewMenu = new Menu("View");
        Menu ColourSchemes = new Menu("Colour Schemes");
        MenuItem Tron = new MenuItem("Tron");
        MenuItem Lavender = new MenuItem("Lavender");
        MenuItem Crisp = new MenuItem("Crisp");

        // Manage Menu
        //Menu
        Menu manageMenu = new Menu("Manage");
        // Help Menu
        // Help Menu
        Menu helpMenu = new Menu("Help");
        MenuItem about = new MenuItem("About");

        // Setting undo/redo to unavailable
        undoEdit.setDisable(true);
        redoEdit.setDisable(true);

        // Child Menu Assignments
        fileMenu.getItems().addAll(newFile, openFile, saveFile, printFile, exportFile, quitFile);
        editMenu.getItems().addAll(timeEdit, undoEdit, redoEdit, copyEdit, cutEdit, pasteEdit);
        viewMenu.getItems().addAll(ColourSchemes);
        ColourSchemes.getItems().addAll(Tron, Lavender, Crisp);
        helpMenu.getItems().addAll(about);
        searchMenu.getItems().addAll(findSearch);
        // Parent Menu Node Assignment
        menu.getMenus().addAll(fileMenu, editMenu, searchMenu, viewMenu, manageMenu, helpMenu);

        // Colour Schemes from View Menu
        Tron.setOnAction( e -> { mainEditor.setId("tron"); });
        Lavender.setOnAction( e -> { mainEditor.setId("lavender");});
        Crisp.setOnAction( e -> {mainEditor.setId("crisp");});

        // Setting actions
        newFile.setOnAction(event ->
        {
            CodeArea editor = getEditor();

            // Clearing editor and clearing undo history
            editor.clear();
            editor.getUndoManager().forgetHistory();
            mainStage.setTitle("Untitled - Ultimate Text Editor 9000");
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
            if (selectedFile == null)
            {
                return;
            }

            String toSet;
            try
            {
                toSet = new FileIO().Open(selectedFile);
            } catch (IOException | TikaException | SAXException e)
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
            mainStage.setTitle(FilenameUtils.getName(selectedFile.toString()) + " - Ultimate Text Editor 9000");
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
                mainStage.setTitle(FilenameUtils.getName(selectedFile.toString()) + " - Ultimate Text Editor 9000");
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
            File file = fileChooser.showSaveDialog(getEditor().getScene().getWindow());
            try
            {
                export.writePDF(getEditor().getText(), file);
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        });

        timeEdit.setOnAction(event ->
        {
            StringBuilder insert = new StringBuilder();
            SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
            Date dateTime = new Date(System.currentTimeMillis());
            insert.append(timeFormat.format(dateTime)).append("\n");
            getEditor().insertText(0, insert.toString());

        });

        findSearch.setOnAction(event ->
        {

            CodeArea editor = getEditor();
            Search search = new Search();

            // Create containers and controls
            VBox root = new VBox();
            HBox topRow = new HBox();
            HBox bottomRow = new HBox();
            TextField matchEntry = new TextField();
            Button searchButton = new Button();
            Button nextResult = new Button();
            Label numFound = new Label();

            //Set control text
            searchButton.setText("Search");
            nextResult.setText("Next");
            numFound.setText("");

            // Set layout settings
            root.setAlignment(Pos.CENTER);
            topRow.setAlignment(Pos.TOP_CENTER);
            bottomRow.setAlignment(Pos.BOTTOM_CENTER);
            root.setSpacing(10);
            topRow.setSpacing(6);
            bottomRow.setSpacing(6);

            // Set a listener to update search when user changes text in search dialog
            matchEntry.textProperty().addListener(new ChangeListener<String>()
            {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
                {
                    search.updateMatch(newValue, editor.getText());
                    int found = search.getResultNum();
                    if (found > 0)
                    {
                        numFound.setTextFill(Color.BLACK);
                        numFound.setText(found + " match(es) found");
                    } else
                    {
                        numFound.setTextFill(Color.RED);
                        numFound.setText("No matches found");
                    }
                }
            });

            // Go to the next result
            nextResult.setOnAction(event1 ->
            {
                int[] pos = search.nextPosition(new int[]{editor.getCurrentParagraph(), editor.getCaretColumn()});
                if (pos != null) // Guard against nullpointer if no results exist
                {
                    // Move the caret to highlight the word and set the editor to follow the caret
                    getEditor().moveTo(pos[0], pos[1]);
                    getEditor().moveTo(pos[0], pos[1] + pos[2], NavigationActions.SelectionPolicy.ADJUST);
                    getEditor().requestFollowCaret();
                }
            });

            // Add the controls to containers
            topRow.getChildren().addAll(matchEntry, nextResult);
            bottomRow.getChildren().addAll(numFound);
            root.getChildren().addAll(topRow, bottomRow);

            // Set the stage and display
            Stage stage = new Stage();
            stage.setTitle("Find Text");
            stage.setScene(new Scene(root, 240, 80));
            stage.initStyle(StageStyle.UTILITY);
            stage.setAlwaysOnTop(true);
            stage.show();
        });

        quitFile.setOnAction(event ->
        {
            Platform.exit();
        });

        copyEdit.setOnAction(event ->
        {
            toClipboard(getEditor().getSelectedText());
        });

        cutEdit.setOnAction(event ->
        {
            toClipboard(getEditor().getSelectedText());
            getEditor().replaceSelection("");
        });

        pasteEdit.setOnAction(event ->
        {
            Clipboard clipboard = Clipboard.getSystemClipboard();
            if(clipboard.getString() != null) getEditor().insertText(getEditor().getCaretPosition(), clipboard.toString());
        });

        undoEdit.setOnAction(event ->
        {
            getEditor().undo();
        });

        redoEdit.setOnAction(event ->
        {
            getEditor().redo();
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
        VirtualizedScrollPane sp = (VirtualizedScrollPane) bp.getCenter();
        return (CodeArea) sp.getContent();
    }

    private void toClipboard(String string)
    {
        ClipboardContent content = new ClipboardContent();
        content.putString(string);
    }
}
