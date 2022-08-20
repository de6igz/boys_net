package com.example.boys_net_2;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import com.example.boys_net_2.Other.Const;
import com.example.boys_net_2.Other.DataBaseHandler;
import com.example.boys_net_2.animations.Shake;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LoginScreenController implements Initializable {
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
    private Button authentificatorButton;

    @FXML
    private AnchorPane buttonsPane;
    @FXML
    private AnchorPane parentPane;

    @FXML
    private TextField login_field;

    @FXML
    private PasswordField password_field;


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
        backButton.setOnAction(this::switchSceneStart);
        authentificatorButton.setOnAction((this::login));

    }

    private void switchSceneStart(ActionEvent event)  {
        try {
            root =FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login_register_screen.fxml")));
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

    void login(ActionEvent event){
        String loginText = login_field.getText().trim();
        String passwordText = password_field.getText().trim();
        if (!loginText.equals("") && !passwordText.equals("")){
            if (loginUser(loginText,passwordText)){
                AudioClip audioClip = new AudioClip(this.getClass().getResource("assets/succes_sound.mp3").toString());
                audioClip.play();
                switchSceneMenu(event);
                Const.realLogin = loginText;
            }
        }
        else
            warn("Нужно заполнить оба поля");

    }

    private boolean loginUser(String login , String password)  {
        DataBaseHandler dataBaseHandler = new DataBaseHandler();
        if (dataBaseHandler.logIn(login, password))
            return true;
        else{
            Shake userLogin = new Shake(login_field);
            Shake passwordField = new Shake(password_field);
            AudioClip audioClip = new AudioClip(this.getClass().getResource("assets/wrongLogin_sound.mp3").toString());
            audioClip.play();
            userLogin.playAnim();
            passwordField.playAnim();
            return false;
        }
    }

    private void switchSceneMenu(ActionEvent event){
        try {
            root =FXMLLoader.load(Objects.requireNonNull(getClass().getResource("menu.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setResizable(false);
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(1000));
        fadeTransition.setNode(parentPane);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setOnFinished((ActionEvent event1)->{
            parentPane.getChildren().remove(buttonsPane);
            stage.setScene(scene);
        });
        fadeTransition.play();
    }

}
