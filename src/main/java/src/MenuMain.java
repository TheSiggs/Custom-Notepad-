package src;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import org.fxmisc.richtext.CodeArea;

public class MenuMain
{

    private MenuBar menu;
    private Menu fileMenu;
    private MenuItem newFile;
    private MenuItem openFile;
    private MenuItem saveFile;

    public MenuMain()
    {
        this.menu = new MenuBar();
        this.fileMenu = new Menu("File");
        this.newFile = new MenuItem("New");
        this.openFile = new MenuItem("Open");
        this.saveFile = new MenuItem("Save");

        fileMenu.getItems().addAll(newFile, openFile, saveFile);
        menu.getMenus().addAll(fileMenu);

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
            String toSet = new FileIO().Open(getEditor().getScene().getWindow());
            if(toSet == null)
            {
                return;
            }

            CodeArea editor = getEditor();

            editor.clear();
            editor.appendText(toSet);
            editor.getUndoManager().forgetHistory();
        });

        saveFile.setOnAction(event ->
        {
            new FileIO().Save(getEditor().getText(), getEditor().getScene().getWindow());
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
