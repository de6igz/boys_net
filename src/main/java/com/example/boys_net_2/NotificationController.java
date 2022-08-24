package com.example.boys_net_2;

import java.io.IOException;
import java.net.URL;
import java.util.ListIterator;
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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class NotificationController implements Initializable {

    @FXML
    private ResourceBundle resources;
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private URL location;

    @FXML
    private ImageView friendsIcon;

    @FXML
    private ImageView messageIcon;

    @FXML
    private ImageView notificationIcon;

    @FXML
    private AnchorPane parentPane;

    @FXML
    private ImageView profileIcon;
    @FXML
    private Pane pane;

    @FXML
    private Label helpLabel;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        intro();
        cursorOnFriends();
        cursorOnMessage();
        cursorOnProfile();
        cursorOnNotification();
        profileIcon.setOnMouseClicked((this::switchSceneProfile));
        friendsIcon.setOnMouseClicked((this::switchSceneFriends));
        messageIcon.setOnMouseClicked(this::switchSceneMessage);
        pane.setOnMouseClicked((event -> {
            Pane temppane  = (Pane) event.getPickResult().getIntersectedNode();
            ListIterator<Node> listIterator= temppane.getChildren().listIterator();
            Label name = (Label) listIterator.next();
            Label surname = (Label) listIterator.next();
            DataBaseHandler dataBaseHandler = new DataBaseHandler();
            int id = dataBaseHandler.getFriendId(name.getText().trim(),surname.getText().trim());
            dataBaseHandler.addFriend(id);
            dataBaseHandler.deleteFriendRequest(Const.myID,id);







        }));


    }


    void intro(){
        DataBaseHandler dataBaseHandler = new DataBaseHandler();
        dataBaseHandler.showFriendRequests(Const.myID,pane);
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

    void cursorOnNotification(){
        notificationIcon.setOnMouseEntered(mouseEvent -> {
            notificationIcon.setOpacity(0.3);
        });
        notificationIcon.setOnMouseExited(mouseEvent -> {
            notificationIcon.setOpacity(1);
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
    void switchSceneFriends(MouseEvent event){
        try {
            root =FXMLLoader.load(Objects.requireNonNull(getClass().getResource("friends.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
    }
    void switchSceneMessage(MouseEvent event){
        try {
            root =FXMLLoader.load(Objects.requireNonNull(getClass().getResource("message.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
    }
}
