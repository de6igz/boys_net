package com.example.boys_net_2;

import java.io.IOException;
import java.net.URL;
import java.util.ListIterator;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;

import com.example.boys_net_2.Other.Const;
import com.example.boys_net_2.Other.DataBaseHandler;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MessageController implements Initializable {


    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private ImageView friendsIcon;

    @FXML
    private ImageView messageIcon;

    @FXML
    private Label nameLabel;

    @FXML
    private AnchorPane nameSurnamePane;

    @FXML
    private ImageView notificationIcon;

    @FXML
    private AnchorPane parentPane;

    @FXML
    private ImageView profileIcon;

    @FXML
    private Pane searchPane;

    @FXML
    private Label surnameLabel;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        service.start();
        cursorOnFriends();
        cursorOnMessage();
        cursorOnProfile();
        cursorOnNotification();

        profileIcon.setOnMouseClicked((this::switchSceneProfile));
        friendsIcon.setOnMouseClicked((this::switchSceneFriends));
        notificationIcon.setOnMouseClicked((this::switchSceneNotifications));
        nameSurnamePane.setOnMouseClicked((event -> {
            Pane pane  = (Pane) event.getPickResult().getIntersectedNode();
            ListIterator<Node> listIterator= pane.getChildren().listIterator();
            Label nameLabel = (Label) listIterator.next();
            String name = String.valueOf(nameLabel.getText());
            Label surnameLabel = (Label) listIterator.next();
            String surname = String.valueOf(surnameLabel.getText());
            Const.idToGo= new DataBaseHandler().getFriendId(name,surname);

            try {
                root =FXMLLoader.load(Objects.requireNonNull(getClass().getResource("dialog.fxml")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
        }));
    }


    void updatePage(){
        DataBaseHandler dataBaseHandler = new DataBaseHandler();
        dataBaseHandler.checkFriendsMessage(Const.realLogin,nameSurnamePane);
    }
    void cursorOnFriends(){
        friendsIcon.setOnMouseEntered(mouseEvent -> {
            friendsIcon.setOpacity(0.3);
        });
        friendsIcon.setOnMouseExited(mouseEvent -> {
            friendsIcon.setOpacity(1);
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
    void switchSceneNotifications(MouseEvent event){
        try {
            root =FXMLLoader.load(Objects.requireNonNull(getClass().getResource("notification.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
    }
    Service<Void> service = new Service<Void>() {
        @Override
        protected Task<Void> createTask() {

            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    final CountDownLatch latch = new CountDownLatch(1);
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                updatePage();
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                            finally {
                                latch.countDown();
                            }
                        }
                    });
                    latch.await();
                    return null;
                }
            };
        }
    };
}
