package Controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DashboardFormController {

    public AnchorPane pane;
    public Label lblTime;

    public void initialize(){
        calTime();
    }

    private void calTime() {
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.ZERO,
                actionEvent -> lblTime.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")))
        ), new KeyFrame(Duration.seconds(1)));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void customerOnActionButton(ActionEvent actionEvent) {
        Stage stage=(Stage)pane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../View/CustomerForm.fxml"))));
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void itemButtonOnAction(ActionEvent actionEvent) {
        Stage stage=(Stage)pane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../View/ItemForm.fxml"))));
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void placeOrderbtnonAction(ActionEvent actionEvent) {
        Stage stage=(Stage)pane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../View/PlaceOrderForm.fxml"))));
            stage.setTitle("Place Order Form");
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
}
