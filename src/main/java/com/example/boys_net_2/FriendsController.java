package com.example.boys_net_2;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import com.example.boys_net_2.Other.Const;
import com.example.boys_net_2.Other.DataBaseHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class FriendsController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView friendsIcon;

    @FXML
    private ImageView messageIcon;

    @FXML
    private Label nameLabel;

    @FXML
    private AnchorPane FIOpane;
    @FXML
    private AnchorPane parentPane;

    @FXML
    private ImageView profileIcon;

    @FXML
    private Label surnameLabel;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        cursorOnFriends();
        cursorOnMessage();
        cursorOnProfile();
        profileIcon.setOnMouseClicked((this::switchSceneProfile));
       updatePage();
    }

    void updatePage(){

        DataBaseHandler dataBaseHandler = new DataBaseHandler();
        scene = FIOpane.getScene();
        dataBaseHandler.checkFriends(Const.realLogin,FIOpane);

        //FIOpane.getChildren().add(new Label("hello"));
    }
    void cursorOnFriends(){
        friendsIcon.setOnMouseEntered(mouseEvent -> {
            friendsIcon.setOpacity(0.3);
        });
        friendsIcon.setOnMouseExited(mouseEvent -> {
            friendsIcon.setOpacity(1);
        });
    }

    void cursorOnMessage(){
        messageIcon.setOnMouseEntered(mouseEvent -> {
            messageIcon.setOpacity(0.3);
        });
        messageIcon.setOnMouseExited(mouseEvent -> {
            messageIcon.setOpacity(1);
        });
    }

    void cursorOnProfile(){
        profileIcon.setOnMouseEntered(mouseEvent -> {
            profileIcon.setOpacity(0.3);
        });
        profileIcon.setOnMouseExited(mouseEvent -> {
            profileIcon.setOpacity(1);
        });
    }
    void switchSceneProfile(MouseEvent event){
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("profile.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
    }
}
