import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;

public class UIControls {

    private final Label label;
    private final Button removeButton;
    private final ProgressIndicator progressIndicator;

    public UIControls() {

        label = new Label("Drop an image here");
        label.setPrefWidth(400);
        label.setPrefHeight(150);
        label.setAlignment(Pos.CENTER);

        removeButton = new Button("Remove Background");
        removeButton.setDisable(true);

        progressIndicator = new ProgressIndicator();
        progressIndicator.setVisible(false);
    }

    public Label getLabel() {
        return label;
    }

    public Button getRemoveButton() {
        return removeButton;
    }

    public ProgressIndicator getProgressIndicator() {
        return progressIndicator;
    }
}