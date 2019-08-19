package src;

import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Screen;

public class FooterMenu {
    private BorderPane footer;
    private HBox carrotLocation;
    private HBox syntax;
    private Label carrotLocationText;
    private Label syntaxText;

    public FooterMenu() {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double screenWidth = screenBounds.getWidth();
        this.footer = new BorderPane();
        this.carrotLocation = new HBox();
        this.syntax = new HBox();

        // Left Side Node
        this.carrotLocationText = new Label();
        carrotLocationText.setText("Line 0, Column 0");
        carrotLocationText.setPadding(new Insets(2,0,0,5));
        carrotLocation.getChildren().addAll(carrotLocationText);

        // Right Side Node
        this.syntaxText = new Label();
        syntaxText.setText("Plain Text");
        syntaxText.setPadding(new Insets(2,5,0,0));
        syntax.getChildren().addAll(syntaxText);

        // Parent Node
        footer.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        footer.setMinHeight(20);
        footer.setRight(syntax);
        footer.setLeft(carrotLocation);


    }

    public void setCurserLocation(int x, int y) {
        carrotLocationText.setText("Line " + x + ", Column " + y);
    }

    public BorderPane getFooter() {
        return footer;
    }
}
