package src;

import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Screen;

public class FooterMenu {
    private BorderPane footer;
    private HBox carrotLocation;
    private HBox syntax;

    public FooterMenu() {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double screenWidth = screenBounds.getWidth();
        this.footer = new BorderPane();
        this.carrotLocation = new HBox();
        this.syntax = new HBox();

        // Left Side Node
        Label carrotLocationText = new Label();
        carrotLocationText.setText("Line (num) Column (num)");
        carrotLocationText.setPadding(new Insets(2,0,0,5));
        carrotLocation.getChildren().addAll(carrotLocationText);

        // Right Side Node
        Label syntaxText = new Label();
        syntaxText.setText("Plain Text");
        syntaxText.setPadding(new Insets(2,5,0,0));
        syntax.getChildren().addAll(syntaxText);

        // Parent Node
        footer.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        footer.setMinHeight(20);
        footer.setRight(syntax);
        footer.setLeft(carrotLocation);
    }

    public BorderPane getFooter() {
        return footer;
    }
}
