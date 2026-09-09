import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;

public class BackgroundRemoverApp extends Application {

    private File selectedFile;
    private Label label;
    private Button removeButton;
    private ProgressIndicator progressIndicator;
    private final ImageProcessorService imageProcessor = new ImageProcessorService();

    private VBox createContent() {
        VBox content = new VBox(15);
        content.setAlignment(Pos.CENTER);

        content.getChildren().addAll(
                label,
                removeButton,
                progressIndicator
        );
        return content;
    }

    private StackPane createRoot(VBox content) {

        StackPane root = new StackPane(content);

        root.setStyle("""
                -fx-padding: 20;
                -fx-background-color: #202124;
                """);

        return root;
    }

    private void configureRemoveButton() {
        removeButton.setOnAction(event -> removeBackground());
    }

    private void removeBackground() {

        if (selectedFile == null) {
            label.setText("No image selected");
            return;
        }

        label.setText("Processing...");

        progressIndicator.setVisible(true);
        removeButton.setDisable(true);

        Thread worker = new Thread(this::processImage);
        worker.setDaemon(true);
        worker.start();
    }

    private String getFileNameWithoutExtension(File file) {
        return file.getName()
                .replaceFirst("[.][^.]+$", "");
    }

    private void processImage() {

        try {

            String originalName =
                    getFileNameWithoutExtension(selectedFile);

            String outputPath =
                    System.getProperty("user.home")
                            + "\\Downloads\\"
                            + originalName
                            + "_nobg.png";

            int exitCode =
                    imageProcessor.removeBackground(
                            selectedFile,
                            outputPath
                    );

            javafx.application.Platform.runLater(() -> {

                progressIndicator.setVisible(false);
                removeButton.setDisable(false);

                if (exitCode == 0) {
                    label.setText("Saved to Downloads folder");
                } else {
                    label.setText("Failed");
                }
            });

        } catch (Exception e) {

            e.printStackTrace();

            javafx.application.Platform.runLater(() -> {

                progressIndicator.setVisible(false);
                removeButton.setDisable(false);
                label.setText("Error");
            });
        }
    }


    @Override
    public void start(Stage stage) {

        UIControls controls = new UIControls();
        label = controls.getLabel();
        removeButton = controls.getRemoveButton();
        progressIndicator = controls.getProgressIndicator();

        VBox content = createContent();
        StackPane root = createRoot(content);

        new DragAndDropHandler(root, file -> {
            selectedFile = file;
            label.setText("Selected: " + selectedFile.getName());
            removeButton.setDisable(false);
        }).initialize();

        configureRemoveButton();

        Scene scene = new Scene(root, 600, 400);

        label.getStyleClass().add("drop-zone");

        removeButton.getStyleClass().add("primary-button");

        scene.getStylesheets().add(
                getClass()
                        .getResource("/style.css")
                        .toExternalForm()
        );

        stage.setScene(scene);
        stage.setTitle("Background Remover");
        stage.getIcons().add(
                new Image(getClass().getResourceAsStream("/icon.png"))
        );
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}