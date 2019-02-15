package controller;

import framework.LoadingStage;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import task.executor.BaseLoopTask;
import task.executor.TaskExecutorPoolManager;

import java.io.IOException;

public class ControllerMain {

    @FXML
    private ImageView ivMain;

    @FXML
    private Button btnBlack;

    private Stage dialogStage;

    private ContextMenu contextMenu;


    public void initMain(Stage stage) {
        btnBlack.setOnAction(event -> {
            try {
                ControllerLogin.showLoginScene(stage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        ivMain.setOnMouseClicked(event -> {
            if (contextMenu != null) {
                contextMenu.hide();
                contextMenu = null;
            }
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                try {
                    ControllerPostMan.showPostManScene(stage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (event.getButton() == MouseButton.SECONDARY) {
                contextMenu = new ContextMenu();
                MenuItem jsonData = new MenuItem("查看接口数据");
                jsonData.setOnAction(event1 -> {
                    try {
                        contextMenu.hide();
                        ControllerJsonData.showJsonDataScene(stage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                contextMenu.getItems().addAll(jsonData);
                contextMenu.show(ivMain, event.getScreenX(), event.getScreenY());
            }
        });

        dialogStage = LoadingStage.createLoad(stage);
//        ivMain.setImage(new Image("http://img3.3lian.com/2013/s1/51/d/114.jpg"));
        ShowImageTask showImageTask = new ShowImageTask("http://img3.3lian.com/2013/s1/51/d/114.jpg");
        TaskExecutorPoolManager.getInstance().runTask(showImageTask, null);
    }

    private class ShowImageTask extends BaseLoopTask {

        private String path;

        public ShowImageTask(String path) {
            this.path = path;
        }

        @Override
        protected void onRunLoopTask() {
            Platform.runLater(() -> dialogStage.show());
            ivMain.setImage(new Image(path));
            TaskExecutorPoolManager.getInstance().removeTask(this);
            Platform.runLater(() -> dialogStage.close());
        }
    }


    public static void showMainScene(Stage stage) throws IOException {
        ControllerMain controller = BaseController.showScene(stage, "layout_main.fxml", "TimeLine");
        controller.initMain(stage);
    }


}
