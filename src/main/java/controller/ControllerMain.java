package controller;

import framework.LoadingStage;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import task.executor.BaseLoopTask;
import task.executor.TaskExecutorPoolManager;
import util.LogDog;

import java.io.IOException;

public class ControllerMain {

    @FXML
    private ImageView ivMain;

    @FXML
    private Button btnBlack;

    private Stage dialogStage;

    private ContextMenu contextMenu;

    private boolean isEditSate = false;
    private double oldX = 0;
    private double oldY = 0;


    public void initMain(Stage stage) {
        btnBlack.setOnAction(event -> {
            try {
                ControllerLogin.showLoginScene(stage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


        ivMain.setOnMousePressed(event -> {
            oldX = event.getX();
            oldY = event.getY();
        });

        ivMain.setOnMouseClicked(event -> {
            if (contextMenu != null) {
                contextMenu.hide();
                contextMenu = null;
            }
            if (event.getButton() == MouseButton.PRIMARY && event.getEventType() == MouseEvent.MOUSE_CLICKED && isEditSate) {
                AnchorPane pane = (AnchorPane) ivMain.getParent();
                Region region = new Region();
                double width = Math.abs(event.getX() - oldX);
                double height = Math.abs(event.getY() - oldY);
                if (event.getX() > oldX && event.getY() > oldY) {
                    region.setLayoutX(event.getSceneX() - width);
                    region.setLayoutY(event.getSceneY() - height);
                } else if (event.getX() > oldX && event.getY() < oldY) {
                    region.setLayoutX(event.getSceneX() - width);
                    region.setLayoutY(event.getSceneY());
                } else if (event.getX() < oldX && event.getY() > oldY) {
                    region.setLayoutX(event.getSceneX());
                    region.setLayoutY(event.getSceneY() - height);
                } else if (event.getX() < oldX && event.getY() < oldY) {
                    region.setLayoutX(event.getSceneX());
                    region.setLayoutY(event.getSceneY());
                }
                region.setPrefSize(width, height);
                pane.getChildren().add(region);
                region.setOnMouseClicked(event12 -> {
                    try {
                        ControllerJsonData.showJsonDataScene(stage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
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

    @FXML
    private void onBtnEditMouseEvent(ActionEvent event) {
        Button button = (Button) event.getSource();
        if (isEditSate) {
            button.setText("编辑");
            isEditSate = false;
        } else {
            button.setText("完成");
            isEditSate = true;
        }
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
