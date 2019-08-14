package src;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class MenuMain {

    public MenuBar getMenu() {
        MenuBar menu = new MenuBar();
        Menu fileMenu = new Menu("File");
        MenuItem item1 = new MenuItem("Item1");
        MenuItem item2 = new MenuItem("Item2");
        MenuItem item3 = new MenuItem("Item3");
        fileMenu.getItems().addAll(item1, item2, item3);

        menu.getMenus().addAll(fileMenu);
        return menu;
    }


}
