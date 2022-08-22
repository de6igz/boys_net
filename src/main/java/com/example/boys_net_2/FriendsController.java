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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
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

    @FXML
    private ImageView searchIcon;
    @FXML
    private AnchorPane nameSurnamePane;
    @FXML
    private Pane searchPane;

    @FXML
    private Button addFriend;

    static String nameToGo ="";
    static String surnameToGo ="";




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        cursorOnFriends();
        cursorOnMessage();
        cursorOnProfile();
        cursorOnSearch();
        profileIcon.setOnMouseClicked((this::switchSceneProfile));
        searchIcon.setOnMouseClicked((this::showAllUsers));
        searchPane.setOnMouseClicked((event1 -> {
            Pane pane  = (Pane) event1.getPickResult().getIntersectedNode();
            ListIterator<Node> listIterator= pane.getChildren().listIterator();
            Label nameLabel = (Label) listIterator.next();
            String name = String.valueOf(nameLabel.getText());
            Label surnameLabel = (Label) listIterator.next();
            String surname = String.valueOf(surnameLabel.getText());
            nameToGo = name;
            surnameToGo = surname;
            Const.idToGo= new DataBaseHandler().getFriendId(name,surname);
            //System.out.println(pane.getChildren());
            //System.out.println("Имя = " + name + " Фамилия: " + surname);
            try {
                root =FXMLLoader.load(Objects.requireNonNull(getClass().getResource("friendProfile.fxml")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            stage = (Stage) ((Node)event1.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);

        }));
        updatePage();
    }




    private void showAllUsers(MouseEvent event) {


        nameSurnamePane.setDisable(true);
        nameSurnamePane.setOpacity(0);
        new DataBaseHandler().showAllUsers(searchPane);

    }

    public void updatePage(){

        DataBaseHandler dataBaseHandler = new DataBaseHandler();
        dataBaseHandler.checkFriends(Const.realLogin,nameSurnamePane);



    }
    void cursorOnFriends(){
        friendsIcon.setOnMouseEntered(mouseEvent -> {
            friendsIcon.setOpacity(0.3);
        });
        friendsIcon.setOnMouseExited(mouseEvent -> {
            friendsIcon.setOpacity(1);
        });
    }

    void cursorOnSearch(){
        searchIcon.setOnMouseEntered(mouseEvent -> {
            searchIcon.setOpacity(0.3);
        });
        searchIcon.setOnMouseExited(mouseEvent -> {
            searchIcon.setOpacity(1);
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

