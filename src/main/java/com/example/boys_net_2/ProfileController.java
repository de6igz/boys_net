package com.example.boys_net_2;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.example.boys_net_2.Other.Const;
import com.example.boys_net_2.Other.DataBaseHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class ProfileController implements Initializable {

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
    private AnchorPane parentPane;

    @FXML
    private BorderPane photoBorder;

    @FXML
    private ImageView profileIcon;

    @FXML
    private ImageView profilePhoto;

    @FXML
    private Label surnameLabel;

    @FXML
    private Button uploadPhotoButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cursorOnFriends();
        cursorOnMessage();
        cursorOnProfile();
        updatePage();
    }

    void updatePage(){

        try {
            nameLabel.setText(new DataBaseHandler().getProfileName(Const.realLogin));
            surnameLabel.setText(new DataBaseHandler().getProfileSurname(Const.realLogin));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

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
}
