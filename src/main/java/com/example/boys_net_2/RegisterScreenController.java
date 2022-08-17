package com.example.boys_net_2;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class RegisterScreenController {
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
    private RadioButton radButtonMale;

    @FXML
    private Button registerButton;

    @FXML
    private TextField surnameField;

    @FXML
    void initialize() {
        backButton.setOnAction(this::switchSceneStart);

    }
    void switchSceneStart(ActionEvent event)  {
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login_register_screen.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

    }
}
