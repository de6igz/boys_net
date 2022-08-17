package com.example.boys_net_2;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        intro();
        loginButton.setOnAction(this::switchSceneLogin);
        registerButton.setOnAction(this::switchSceneRegister);
    }
    private void intro(){

        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(2000));
        fadeTransition.setNode(buttonsPane);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }
}
