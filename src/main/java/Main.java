import controller.ControllerLogin;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import task.executor.TaskExecutorPoolManager;

import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        ControllerLogin.showLoginScene(primaryStage);
        primaryStage.setResizable(false);
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> TaskExecutorPoolManager.getInstance().destroyAll());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
