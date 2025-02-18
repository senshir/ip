package slipstream.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import slipstream.Slipstream;
import slipstream.ui.UiMessages;

/**
 * The main controller for the GUI.
 */
public class MainWindow {
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Slipstream slipstream;
    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image botImage = new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));

    public void setSlipstream(Slipstream slipstream) {
        this.slipstream = slipstream;
        showWelcomeMessage();
    }

    /**
     * Displays the welcome message when Slipstream starts.
     */
    private void showWelcomeMessage() {
        UiMessages ui = new UiMessages();
        String welcomeMessage = ui.showWelcomeMessage();
        dialogContainer.getChildren().add(DialogBox.getSlipstreamDialog(welcomeMessage, botImage));
    }

    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = slipstream.getResponse(input);

        dialogContainer.getChildren().addAll(
            DialogBox.getUserDialog(input, userImage),
            DialogBox.getSlipstreamDialog(response, botImage)
        );

        userInput.clear();

        if (input.equalsIgnoreCase("bye")) {
            javafx.application.Platform.exit();
        }
    }
}
