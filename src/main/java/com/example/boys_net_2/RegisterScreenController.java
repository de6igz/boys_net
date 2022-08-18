package com.example.boys_net_2;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

import com.example.boys_net_2.Other.DataBaseHandler;
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
    @FXML
    private Label succesLabel;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        intro();
        backButton.setOnAction(this::switchSceneStart);
        registerButton.setOnAction(this::register);
    }

    void switchSceneStart(ActionEvent event) {
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login_register_screen.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setResizable(false);
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(1000));
        fadeTransition.setNode(buttonsPane);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setOnFinished((ActionEvent event1) -> {
            parentPane.getChildren().remove(buttonsPane);
            stage.setScene(scene);
        });
        fadeTransition.play();

    }



    private void intro() {

        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(2000));
        fadeTransition.setNode(buttonsPane);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(2000), buttonsPane);
        translateTransition.setByY(30f);
        translateTransition.setFromY(0f);
        new ParallelTransition(fadeTransition, translateTransition).play();
    }

    private void register(ActionEvent event)  {
        DataBaseHandler dbHandler = new DataBaseHandler();
        try {
            String gender = "";
            if (radButtonFemale.isSelected())
                gender = "Female";
            else {
                if (radButtonMale.isSelected())
                    gender = "Male";
            }
            if (!nameField.getText().equals("") && !surnameField.getText().equals("") && !loginField.getText().equals("") && !passwordField.getText().equals("") && !gender.equals("")) {
                dbHandler.SignUp(nameField.getText(), surnameField.getText(), loginField.getText(), passwordField.getText(), gender);
                showSucces(event);

            }
            else {
                System.out.println("Какое-то из полей пусто");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    void showSucces(ActionEvent event){
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(1000));
        fadeTransition.setNode(buttonsPane);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setOnFinished((ActionEvent event1) -> {
            parentPane.getChildren().remove(buttonsPane);
            FadeTransition fadeTransition1 = new FadeTransition();
            fadeTransition1.setNode(succesLabel);
            fadeTransition1.setDuration(Duration.millis(1000));
            fadeTransition1.setNode(succesLabel);
            fadeTransition1.setFromValue(0);
            fadeTransition1.setToValue(1);
            fadeTransition1.setDuration(Duration.millis(2000));
            fadeTransition1.play();
            fadeTransition1.setOnFinished((ActionEvent event2) -> {
                FadeTransition fadeTransition2 = new FadeTransition();
                fadeTransition2.setNode(succesLabel);
                fadeTransition2.setDuration(Duration.millis(1000));
                fadeTransition2.setFromValue(1);
                fadeTransition2.setToValue(0);
                fadeTransition2.play();
                fadeTransition2.setOnFinished((event3 -> {
                    try {
                        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login_register_screen.fxml")));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    stage = (Stage) succesLabel.getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                }));
                });



            });
            fadeTransition.play();
        }


    }


