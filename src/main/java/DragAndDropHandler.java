import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;

import java.io.File;
import java.util.function.Consumer;

public class DragAndDropHandler {

    private final StackPane root;
    private final Consumer<File> onFileSelected;

    public DragAndDropHandler(
            StackPane root,
            Consumer<File> onFileSelected
    ) {
        this.root = root;
        this.onFileSelected = onFileSelected;
    }

    public void initialize() {

        root.setOnDragOver(event -> {

            if (event.getDragboard().hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY);
            }

            event.consume();
        });

        root.setOnDragDropped(event -> {

            var dragboard = event.getDragboard();

            if (dragboard.hasFiles()) {
                onFileSelected.accept(
                        dragboard.getFiles().get(0)
                );
            }

            event.setDropCompleted(true);
            event.consume();
        });
    }
}