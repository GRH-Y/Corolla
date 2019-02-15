package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerJsonData {
    private Stage stage;

    public void init(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void onBtnTestInterface(ActionEvent event) {
        try {
            ControllerPostMan.showPostManScene(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onBtnBlack(ActionEvent event) {
        try {
            ControllerMain.showMainScene(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showJsonDataScene(Stage stage) throws IOException {
        ControllerJsonData controller = BaseController.showScene(stage, "layout_json_data.fxml", "接口文档");
        controller.init(stage);
    }
}
