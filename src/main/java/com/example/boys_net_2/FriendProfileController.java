package com.example.boys_net_2;

import java.io.*;
import java.net.URL;
import java.sql.SQLException;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

public class FriendProfileController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addFriendButton;

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
    private ImageView notificationIcon;

    @FXML
    private Label surnameLabel;
     String friendLogin="";



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updatePage();
        cursorOnFriends();
        cursorOnMessage();
        cursorOnProfile();
        cursorOnNotification();
        profileIcon.setOnMouseClicked((this::switchSceneProfile));
        friendsIcon.setOnMouseClicked((this::switchSceneFriends));
        messageIcon.setOnMouseClicked(this::switchSceneMessage);
        notificationIcon.setOnMouseClicked(this::switchSceneNotifications);
        addFriendButton.setOnMouseClicked((event -> {
            sendFriendRequest();
        }));
    }

    void sendFriendRequest(){
        new DataBaseHandler().sendFriendRequest(FriendsController.nameToGo,FriendsController.surnameToGo,Const.idToGo);
        addFriendButton.setText("Заявка отправлена");
        addFriendButton.setDisable(true);
    }

    void updatePage(){
        try {
            downloadPhoto();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        friendLogin = new DataBaseHandler().getUserLogin(FriendsController.nameToGo, FriendsController.surnameToGo);
        nameLabel.setText(new DataBaseHandler().getProfileName(friendLogin));
        surnameLabel.setText(new DataBaseHandler().getProfileSurname(friendLogin));
        if (new DataBaseHandler().doProfileHasPhoto(new DataBaseHandler().getMyId(friendLogin))) {
            try {
                String path = new File("src/main/resources/userPhotos/" + friendLogin + "profilePhoto.jpg").getAbsolutePath();
                Image image = new Image(path);
                profilePhoto.setImage(image);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (new DataBaseHandler().isFriendRequestSend(Const.myID, new DataBaseHandler().getMyId(friendLogin))) {
            addFriendButton.setText("Заявка отправлена");
            addFriendButton.setDisable(true);
        }
        if (new DataBaseHandler().isFriend(Const.myID,new DataBaseHandler().getMyId(friendLogin))){
            addFriendButton.setText("Друг");
            addFriendButton.setDisable(true);
        }


    }
    void downloadPhoto(){
        String server = "31.31.198.106";
        String user = "u1768436";
        String pass = "root12345678";

        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(server);
            ftpClient.login(user,pass);
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String remoteFile1 = Const.realLogin+"profilePhoto.jpg";
        File downloadFile1 = new File( "src/main/resources/userPhotos/"+friendLogin+"profilePhoto.jpg");
        OutputStream outputStream1 = null;
        try {
            outputStream1 = new BufferedOutputStream(new FileOutputStream(downloadFile1));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            outputStream1.close();
        } catch (IOException e) {
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
