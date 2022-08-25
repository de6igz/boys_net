package com.example.boys_net_2;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;

import com.example.boys_net_2.Other.Const;
import com.example.boys_net_2.Other.DataBaseHandler;
import javafx.animation.Timeline;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class DialogController implements Initializable {


    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane AnchorPaneContainsAllNodes;

    @FXML
    private AnchorPane AnchorPaneInScrollPane;

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
    private ScrollPane scrollPane;

    @FXML
    private Button sendButton;

    @FXML
    private TextArea textArea;
    static boolean inChat = false;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(Thread.currentThread() + "  Initialize");
        inChat = true;
        serviceStart.start();
        cursorOnFriends();
        cursorOnMessage();
        cursorOnProfile();


        profileIcon.setOnMouseClicked((event -> {
            switchSceneProfile(event);
            inChat=false;
        }));
        friendsIcon.setOnMouseClicked((event -> {
            switchSceneFriends(event);
            inChat=false;
        }));
        messageIcon.setOnMouseClicked((event -> {
            switchSceneMessage(event);
            inChat=false;
        }));
        sendButton.setOnMouseClicked((event -> {
            if (!textArea.getText().equals("")){
            DataBaseHandler dataBaseHandler = new DataBaseHandler();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String whoName =dataBaseHandler.getProfileSurname(Const.realLogin);
            String whoSurname = dataBaseHandler.getProfileName(Const.realLogin);
            String time  = dtf.format(now);
            String text = textArea.getText();
            textArea.clear();
            dataBaseHandler.sendMessage(whoName,whoSurname,time,text);
            updatePage();
            }
        }));

    }






    Service<Void> serviceStart = new Service<Void>() {
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

    void updatePage(){
        DataBaseHandler dataBaseHandler = new DataBaseHandler();
        dataBaseHandler.showDialog(AnchorPaneInScrollPane);
        dataBaseHandler.openDialog(dataBaseHandler.getChatId(Const.realLogin, dataBaseHandler.getLogin(Const.idToGo)));

    }
    void liveUpdate(){
        new DataBaseHandler().showDialog(AnchorPaneInScrollPane);
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
