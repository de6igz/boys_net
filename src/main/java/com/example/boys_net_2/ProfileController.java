package com.example.boys_net_2;

import java.io.*;
import java.net.URL;
import java.sql.SQLException;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

public class ProfileController implements Initializable {

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

    @FXML
    private Button uploadPhotoButton;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cursorOnFriends();
        cursorOnMessage();
        cursorOnProfile();
        cursorOnNotification();
        service.start();

        uploadPhotoButton.setOnMouseClicked((event -> {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(stage);
            if (file!=null){
                uploadPhoto(file);
                new DataBaseHandler().insertPhotoLocation();
                uploadPhotoButton.setDisable(true);
                uploadPhotoButton.setText("Успешно");
            }

        }));
        friendsIcon.setOnMouseClicked((this::switchSceneFriends));
        notificationIcon.setOnMouseClicked((this::switchSceneNotifications));
        messageIcon.setOnMouseClicked(this::switchSceneMessage);
    }

    void updatePage(){
        try {
            downloadPhoto();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        nameLabel.setText(new DataBaseHandler().getProfileName(Const.realLogin));
        surnameLabel.setText(new DataBaseHandler().getProfileSurname(Const.realLogin));
        if (new DataBaseHandler().doProfileHasPhoto(Const.myID)){
            try{
                String path = new File("src/main/resources/userPhotos/"+Const.realLogin+"profilePhoto.jpg").getAbsolutePath();
                Image image = new Image(path);
                profilePhoto.setImage(image);
            }
            catch (Exception e){
               e.printStackTrace();
            }
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

    void switchSceneFriends(MouseEvent event){
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("friends.fxml")));
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

    void uploadPhoto(File file){
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

        try {
            String fileToDelete = Const.realLogin+"profilePhoto";

            try {
                boolean deleted = ftpClient.deleteFile(fileToDelete);
            } catch (IOException ex) {
                System.out.println("Oh no, there was an error: " + ex.getMessage());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        File firstLocalFile = new File(file.getPath());

        String firstRemoteFile = Const.realLogin+"profilePhoto.jpg";
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(firstLocalFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


        try {
            boolean done = ftpClient.storeFile(firstRemoteFile, inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
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
        File downloadFile1 = new File( "src/main/resources/userPhotos/"+Const.realLogin+"profilePhoto.jpg");
        OutputStream outputStream1 = null;
        try {
            outputStream1 = new BufferedOutputStream(new FileOutputStream(downloadFile1));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            boolean success = ftpClient.retrieveFile(remoteFile1, outputStream1);
            if (success)
                System.out.println("Скачалось");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            outputStream1.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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

