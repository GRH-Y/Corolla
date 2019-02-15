package controller;

import connect.network.base.RequestEntity;
import connect.network.http.JavHttpConnect;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerPostMan {

    @FXML
    private ChoiceBox cbRequestMode;

    @FXML
    private ListView lvParams;

    @FXML
    private Button btnSend;

    @FXML
    private TextField tfUrl;

    @FXML
    private TextArea httpResponse;

    @FXML
    private Button btnAddItem;

    private String request = "GET";

    private Stage stage;

    public void initPostMan(Stage stage) {
        this.stage = stage;
        btnAddItem.setOnAction(event -> {
            ObservableList<Parent> observableList = lvParams.getItems();
            if (observableList == null) {
                observableList = FXCollections.emptyObservableList();
            }
            FXMLLoader fxmlLoader = BaseController.getFXMLLoader("item_params.fxml");
            try {
                observableList.add(fxmlLoader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
            lvParams.setItems(observableList);
        });

        cbRequestMode.setValue(request);
        cbRequestMode.setItems(FXCollections.observableArrayList("GET", "POST"));
        cbRequestMode.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() == 0) {
                request = "GET";
            } else {
                request = "POST";
            }
        });

        btnSend.setOnAction(event -> {
            RequestEntity entity = new RequestEntity();
            entity.setAddress(tfUrl.getText());
            entity.setResultType(byte[].class);
            entity.setScbMethodName("onHttpCallBack");
            entity.setCallBackTarget(this);
            if ("GET".equals(request)) {
                JavHttpConnect.getInstance().submitGet(entity);
            } else {
                JavHttpConnect.getInstance().submitPost(entity);
            }
        });
    }

    @FXML
    private void onBtnBlack(ActionEvent event) {
        try {
            ControllerJsonData.showJsonDataScene(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void onHttpCallBack(byte[] data) {
        Platform.runLater(() -> httpResponse.setText(new String(data)));
    }

    public static void showPostManScene(Stage stage) throws IOException {
        ControllerPostMan controller = BaseController.showScene(stage, "layout_postman.fxml", "PostMan");
        controller.initPostMan(stage);
    }
}
