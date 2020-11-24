package ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;


public class Controller implements Initializable {
    @FXML
    private HBox top;
    @FXML
    private ComboBox<String> type;
    @FXML
    private ComboBox<String> marque;
    @FXML
    private Label title;
    @FXML
    private BorderPane content;


    private double xOffSet = 0;
    private double yOffSet = 0;

    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        makeStageDragable();
        loadUI("user");
        //RulesCreation.rules.get(1);
        title.setText("User Panel");
    }

    private void makeStageDragable() {
        top.setOnMousePressed(event -> {
            xOffSet = event.getSceneX();
            yOffSet = event.getSceneY();

        });
        top.setOnMouseDragged(event -> {
            Launch.stage.setX(event.getScreenX() - xOffSet);
            Launch.stage.setY(event.getScreenY() - yOffSet);
            Launch.stage.setOpacity(0.8f);
        });
        top.setOnDragDone(event -> {
            Launch.stage.setOpacity(1.0f);
        });
        top.setOnMouseReleased(event -> {
            Launch.stage.setOpacity(1.0f);
        });
    }

    @FXML
    private void close_app(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    private void minimize_stage(MouseEvent event) {
        Launch.stage.setIconified(true);
    }
    @FXML
    private void maximize_stage(MouseEvent event) {
        Launch.stage.setFullScreen(true);
    }
    @FXML
    private void user(MouseEvent event) {
        content.setCenter(null);
        loadUI("user");
        title.setText("User Panel");
    }
    @FXML
    private void admin(MouseEvent event) {
        content.setCenter(null);
        loadUI("admin");

        title.setText("Admin Panel");
    }

    private void loadUI(String ui) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource(ui + ".fxml"));
            content.setCenter(root);
        } catch (Exception e) {
            System.out.println(e);
        }
    }


}
