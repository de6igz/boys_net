package com.example.boys_net_2;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;


import com.example.boys_net_2.Other.WarningScreen;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class StartScreenController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label boysNetLabel;

    @FXML
    private Label boysNetLabelShadow;

    @FXML
    private AnchorPane buttonsPane;

    @FXML
    private AnchorPane parentPane;

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;



    void warn(String text) {

        Stage stage = (Stage) parentPane.getScene().getWindow();
        Alert.AlertType type = Alert.AlertType.WARNING;
        Alert alert = new Alert(type,"");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(stage);

        alert.getDialogPane().setHeaderText(text);
        alert.showAndWait();


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        intro();
        loginButton.setOnAction(this::switchSceneLogin);
        registerButton.setOnAction(this::switchSceneRegister);

    }
    void switchSceneRegister(ActionEvent event)  {
        try {
            root =FXMLLoader.load(Objects.requireNonNull(getClass().getResource("register_screen.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(1000));
        fadeTransition.setNode(buttonsPane);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setOnFinished((ActionEvent event1)->{
            parentPane.getChildren().remove(buttonsPane);
            stage.setScene(scene);
        });
        fadeTransition.play();


    }

    void switchSceneLogin(ActionEvent event)  {
        try {
            root =FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login_screen.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setResizable(false);
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(1000));
        fadeTransition.setNode(buttonsPane);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setOnFinished((ActionEvent event1)->{
            parentPane.getChildren().remove(buttonsPane);
            stage.setScene(scene);
        });
        fadeTransition.play();







    }
    private void intro(){

        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(2000));
        fadeTransition.setNode(buttonsPane);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(2000),buttonsPane);
        translateTransition.setByY(30f);
        translateTransition.setFromY(0f);
        new ParallelTransition(fadeTransition,translateTransition).play();
    }
}
