package com.example.boys_net_2;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class RegisterScreenController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private Button backButton;

    @FXML
    private Label boysNetLabel;

    @FXML
    private Label boysNetLabel1;

    @FXML
    private Label boysNetLabel11;

    @FXML
    private Label boysNetLabel111;

    @FXML
    private Label boysNetLabel12;

    @FXML
    private Label boysNetLabelShadow;

    @FXML
    private ToggleGroup gender;

    @FXML
    private TextField loginField;

    @FXML
    private TextField nameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private RadioButton radButtonFemale;
    @FXML
    private AnchorPane buttonsPane;
    @FXML
    private AnchorPane parentPane;

    @FXML
    private RadioButton radButtonMale;

    @FXML
    private Button registerButton;

    @FXML
    private TextField surnameField;


    void switchSceneStart(ActionEvent event)  {
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login_register_screen.fxml")));
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        intro();
        backButton.setOnAction(this::switchSceneStart);
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
