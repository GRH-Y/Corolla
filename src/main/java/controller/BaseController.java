package controller;

import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class BaseController {
    private BaseController() {
    }

    public static <T> T showScene(Stage stage, String fxml, String title) throws IOException {
        FXMLLoader fxmlLoader = getFXMLLoader(fxml);
        Parent root = fxmlLoader.load();
        Scene loginScene = new Scene(root);
        stage.setTitle(title);
        stage.setScene(loginScene);
        return fxmlLoader.getController();
    }

    public static FXMLLoader getFXMLLoader(String fxml) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        URL loginUrl = ClassLoader.getSystemResource(fxml);
        fxmlLoader.setLocation(loginUrl);
        return fxmlLoader;
    }
}
