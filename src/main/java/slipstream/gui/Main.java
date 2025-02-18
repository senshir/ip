package slipstream.gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import slipstream.Slipstream;
import slipstream.SlipstreamException;

/**
 * A GUI for Slipstream using FXML.
 */
public class Main extends Application {

    private final Slipstream slipstream = new Slipstream("./data/slipstream.txt");

    public Main() throws SlipstreamException {
    }

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane root = fxmlLoader.load();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Slipstream");

            // Pass the Slipstream chatbot instance to the controller
            MainWindow controller = fxmlLoader.getController();
            controller.setSlipstream(slipstream);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
