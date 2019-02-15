package controller;

import framework.DialogStage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class ControllerLogin {

    @FXML
    private Button loginButton;

    @FXML
    private TextField txfUserName;

    @FXML
    private TextField txfPwd;

    public void init(Stage stage) {
        loginButton.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                showMainScene(stage);
            }
        });
        loginButton.setOnAction(event -> showMainScene(stage));
    }

    public static void showLoginScene(Stage stage) throws IOException {
        ControllerLogin controllerLogin = BaseController.showScene(stage, "layout_login.fxml", "Login");
        controllerLogin.init(stage);
    }

    private void showMainScene(Stage stage) {
        String userName = txfUserName.getText();
        String password = txfPwd.getText();

        if ("admin".equals(userName) && "123".equals(password)) {
            try {
                ControllerMain.showMainScene(stage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            DialogStage.showDialog("提示", "账号或者密码错误!!!");
        }
    }

}
