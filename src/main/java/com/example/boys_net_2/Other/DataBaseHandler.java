package com.example.boys_net_2.Other;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

import java.sql.*;

public class DataBaseHandler {
    public Connection getDbConnect() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://31.31.198.106:3306/u1768436_boysnet", DataBaseLogin.user, DataBaseLogin.password);
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ошибка, не удалось подключиться к серверу");

        }

        return connection;
    }




    public String getProfileName(String login)  {
        String select = "SELECT name FROM " + Const.USERS_TABLE + " WHERE " + Const.login + "=" + "'" +login+ "'";
        try {
            Statement statement = getDbConnect().createStatement();
            ResultSet resultSet = statement.executeQuery(select);
            while (resultSet.next()){
                return  resultSet.getString("name");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public String getProfileSurname(String login)  {
        String select = "SELECT surname FROM " + Const.USERS_TABLE + " WHERE " + Const.login + "=" + "'" +login+ "'";
        try {
            Statement statement = getDbConnect().createStatement();
            ResultSet resultSet = statement.executeQuery(select);
            while (resultSet.next()){
                return  resultSet.getString("surname");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }




    public void SignUp(String name, String surname,
                       String login, String password,
                       String gender) throws SQLException {
        int id = getLastId()+1;
        String INSERT = "INSERT INTO " + Const.USERS_TABLE + "(" +
                Const.name + "," + Const.surname + "," +
                Const.login + "," + Const.password + "," +
                Const.gender +","+"id"+ ")" +
                "VALUES(?,?,?,?,?,?)";
        PreparedStatement preparedStatement = getDbConnect().prepareStatement(INSERT);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, surname);
        preparedStatement.setString(3, login);
        preparedStatement.setString(4, password);
        preparedStatement.setString(5, gender);
        preparedStatement.setInt(6, id);

        preparedStatement.executeUpdate();
    }

    public boolean logIn(String login, String password) {
        ResultSet resultSet = null;
        Const.myID = getMyId(login);
        Const.myName=getMyName(login);
        Const.mySurname=getMySurname(login);
        String select = "SELECT * FROM " + Const.USERS_TABLE + " WHERE " + Const.login + "=? AND " + Const.password + "=?";
        try {
            PreparedStatement preparedStatement = getDbConnect().prepareStatement(select);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            if (resultSet.next())
                return true;
            else
                return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getUserLogin(String name,String surname){
        String select = "SELECT login FROM " + Const.USERS_TABLE + " WHERE " + Const.name + "= '"+name+"' AND " + Const.surname + "= '"+surname+"'";
        try {
            Statement statement = getDbConnect().createStatement();
            ResultSet resultSet = statement.executeQuery(select);
            resultSet.next();
            return resultSet.getString(1);
        } catch (SQLException e) {
            throw  new RuntimeException(e);
        }
    }

    public int getLastId(){
        String select = "SELECT id from " + Const.USERS_TABLE + " ORDER BY id DESC LIMIT 1;";
        try {
            Statement statement = getDbConnect().createStatement();
            ResultSet resultSet = statement.executeQuery(select);
            if (resultSet.next()) {

                Const.lastID = resultSet.getInt(1);
                return resultSet.getInt(1);
            }
            else
                return 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getMyId(String login){
        String select = "SELECT id from " + Const.USERS_TABLE+" WHERE "+Const.login+"="+"'"+login+"'" + ";";
        try {
            Statement statement = getDbConnect().createStatement();
            ResultSet resultSet = statement.executeQuery(select);
            if (resultSet.next()) {


                return resultSet.getInt(1);
            }
            else
                return 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getMyName(String login){
        String select = "SELECT name from " + Const.USERS_TABLE+" WHERE "+Const.login+"="+"'"+login+"'" + ";";
        try {
            Statement statement = getDbConnect().createStatement();
            ResultSet resultSet = statement.executeQuery(select);
            if (resultSet.next()) {


                return resultSet.getString(1);
            }
            else
                return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getMySurname(String login){
        String select = "SELECT surname from " + Const.USERS_TABLE+" WHERE "+Const.login+"="+"'"+login+"'" + ";";
        try {
            Statement statement = getDbConnect().createStatement();
            ResultSet resultSet = statement.executeQuery(select);
            if (resultSet.next()) {


                return resultSet.getString(1);
            }
            else
                return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getFriendId(String name,String surname){
        String select = "SELECT id from " + Const.USERS_TABLE+" WHERE "+Const.name+"="+"'"+name+"'"+"AND " + Const.surname + "=" + "'" +surname+"'" + ";";
        try {
            Statement statement = getDbConnect().createStatement();
            ResultSet resultSet = statement.executeQuery(select);
            if (resultSet.next()) {


                return resultSet.getInt(1);
            }
            else
                return 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void showFriendRequests(int myId,Pane pane){
        if (doIhaveFriendRequest(Const.myID)){

            String select = "SELECT fromWho FROM " + Const.FRIEND_REQUESTS_TABLE + " WHERE toWho="+myId;
            try {
                int id =-1;
                Statement statement = getDbConnect().createStatement();
                ResultSet resultSetIds = statement.executeQuery(select);
                double y = 30;
                double xName = 35;
                double xSurname=167;
                while (resultSetIds.next()){
                    y+=50;
                    id=resultSetIds.getInt(1);
                    String select1 = "SELECT name,surname FROM " + Const.USERS_TABLE + " WHERE id=" + id;
                    Statement statement1 = getDbConnect().createStatement();
                    ResultSet resultSetNamesSurnames = statement1.executeQuery(select1);
                    if (resultSetNamesSurnames.next()){
                        String friendName = resultSetNamesSurnames.getString(1);
                        String friendSurname = resultSetNamesSurnames.getString(2);
                        Label friendNameLabel = new Label(friendName);
                        Label friendSurnameLabel = new Label(friendSurname);
                        //Button addButton = new Button("Добавить");
                        friendNameLabel.setDisable(true);
                        friendSurnameLabel.setDisable(true);
                        Pane tempPane = new Pane(friendNameLabel, friendSurnameLabel);
                        friendNameLabel.setLayoutX(10);
                        //addButton.setLayoutX(tempPane.getLayoutX()+230);
                        //addButton.setLayoutY(tempPane.getLayoutY()+5);
                        friendNameLabel.setFont(new Font("VAG World Bold", 18));

                        friendSurnameLabel.setFont(new Font("VAG World Bold", 18));
                        friendSurnameLabel.setLayoutX(xSurname);

                        tempPane.setPrefHeight(40);
                        tempPane.setPrefWidth(450);
                        tempPane.setLayoutX(xName);
                        tempPane.setLayoutY(y);
                        tempPane.setStyle("-fx-border-color: black");

                        Label addButton = new Label("Добавить");
                        addButton.setFont(new Font("VAG World Bold", 18));
                        addButton.setDisable(true);
                        addButton.setLayoutX(300);

                        //paneButton.setPrefHeight(40);
                        tempPane.getChildren().add(addButton);
                        //paneButton.setPrefWidth(100);
                        //paneButton.setLayoutX(tempPane.getLayoutX()+300);
                        //paneButton.setStyle("-fx-border-color: black");



                        pane.getChildren().add(tempPane);
                        if (y >= 462) {
                            y = 150;
                            xName += 350;
                            xSurname += 350;
                        }

                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public void addFriend(int id){
        int newId = getLastChatId()+1;
        String insert1 = "INSERT INTO " + Const.FRIENDS_TABLE + "(user1,user2,chatID) " + "VALUES(" + "'" +getLogin(id) + "'" + ","+"'" +Const.realLogin + "'"+","+newId + ")";
        String insert2 = "INSERT INTO " + Const.FRIENDS_TABLE + "(user1,user2,chatID) " + "VALUES(" +"'"+ Const.realLogin+"'"+"," + "'"+getLogin(id)+"'"+","+newId +    ")";
        try {
            Statement statement = getDbConnect().createStatement();
            statement.executeUpdate(insert1);
            statement.executeUpdate(insert2);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public String getLogin(int id){
        String select = "SELECT login from " + Const.USERS_TABLE + " WHERE id=" + id;
        try {
            Statement statement = getDbConnect().createStatement();
            ResultSet resultSet = statement.executeQuery(select);
            if (resultSet.next())
                return resultSet.getString(1);
            else
                return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void checkFriends(String login, AnchorPane pane){
        String select = "SELECT user2 FROM " + Const.FRIENDS_TABLE + " WHERE " + "user1" + "=" + "'" +login+ "'";
        try {
            Statement statement = getDbConnect().createStatement();
            ResultSet resultSet = statement.executeQuery(select);
            double y = 62;
            while (resultSet.next()){
                y+=25;
                String friendName = getProfileName(resultSet.getString("user2"));
                String friendSurname = getProfileSurname(resultSet.getString("user2"));
                Label friendNameLabel = new Label(friendName);
                friendNameLabel.setFont(new Font("VAG World Bold",18));
                friendNameLabel.setLayoutX(29);
                friendNameLabel.setLayoutY(y);
                friendNameLabel.setId("text");

                Label friendSurnameLabel = new Label(friendSurname);
                friendSurnameLabel.setFont(new Font("VAG World Bold",18));
                friendSurnameLabel.setLayoutX(167);
                friendSurnameLabel.setLayoutY(y);
                Button deleteButton = new Button("Удалить");
                deleteButton.setLayoutX(260);
                deleteButton.setLayoutY(y);
                deleteButton.setStyle("-fx-background-color: black");
                deleteButton.setStyle("-fx-border-radius:30");

//                DataBaseHandler.nameSurnameFriends.put(friendNameLabel,friendSurnameLabel);
                pane.getChildren().add(friendNameLabel);
                pane.getChildren().add(friendSurnameLabel);
                pane.getChildren().add(deleteButton);
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void showAllUsers(Pane pane){
        String select = "SELECT name,surname FROM " + Const.USERS_TABLE;
        try {
            Statement statement = getDbConnect().createStatement();
            ResultSet resultSet = statement.executeQuery(select);
            pane.setDisable(false);
            double y = 150;
            double xName = 29;
            double xSurname=167;
            while (resultSet.next()){
                String friendName = resultSet.getString("name");
                String friendSurname = resultSet.getString("surname");
                if (friendName.equals(Const.myName) && friendSurname.equals(Const.mySurname))
                    continue;
                else {
                    y += 50;
                    Label friendNameLabel = new Label(friendName);
                    Label friendSurnameLabel = new Label(friendSurname);
                    friendNameLabel.setDisable(true);
                    friendSurnameLabel.setDisable(true);
                    Pane tempPane = new Pane(friendNameLabel, friendSurnameLabel);
                    friendNameLabel.setFont(new Font("VAG World Bold", 18));

                    friendSurnameLabel.setFont(new Font("VAG World Bold", 18));
                    friendSurnameLabel.setLayoutX(xSurname);

                    tempPane.setPrefHeight(40);
                    tempPane.setPrefWidth(300);

                    tempPane.setLayoutX(xName);
                    tempPane.setLayoutY(y);
                    tempPane.setStyle("-fx-border-color: black");
                    pane.getChildren().add(tempPane);
                    if (y >= 462) {
                        y = 150;
                        xName += 350;
                        xSurname += 350;
                    }
                }

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void checkFriendsMessage(String login, AnchorPane pane){
        String select = "SELECT user2 FROM " + Const.FRIENDS_TABLE + " WHERE " + "user1" + "=" + "'" +login+ "'";
        try {
            Statement statement = getDbConnect().createStatement();
            ResultSet resultSet = statement.executeQuery(select);
            double y = 30;
            double xName = 0;
            double xSurname=167;
            while (resultSet.next()){
                y += 50;
                Label friendNameLabel = new Label(getName(resultSet.getString(1)));
                Label friendSurnameLabel = new Label(getSurname(resultSet.getString(1)));
                friendNameLabel.setDisable(true);
                friendSurnameLabel.setDisable(true);
                Pane tempPane = new Pane(friendNameLabel, friendSurnameLabel);
                friendNameLabel.setFont(new Font("VAG World Bold", 18));

                friendSurnameLabel.setFont(new Font("VAG World Bold", 18));
                friendSurnameLabel.setLayoutX(xSurname);

                tempPane.setPrefHeight(40);
                tempPane.setPrefWidth(300);

                tempPane.setLayoutX(xName);
                tempPane.setLayoutY(y);
                tempPane.setStyle("-fx-border-color: black");
                pane.getChildren().add(tempPane);
                if (y >= 462) {
                    y = 150;
                    xName += 350;
                    xSurname += 350;
                }
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public String getName(String login){
        String select = "SELECT name FROM " + Const.USERS_TABLE + " WHERE login="+"'"+login+"'";
        try {
            Statement statement = getDbConnect().createStatement();
            ResultSet resultSet = statement.executeQuery(select);
            if (resultSet.next()){
                return resultSet.getString(1);
            }
            else
                return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public String getSurname(String login){
        String select = "SELECT surname FROM " + Const.USERS_TABLE + " WHERE login="+"'"+login+"'";
        try {
            Statement statement = getDbConnect().createStatement();
            ResultSet resultSet = statement.executeQuery(select);
            if (resultSet.next()){
                return resultSet.getString(1);
            }
            else
                return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }



    public void sendFriendRequest(String wantToAddName, String wantToAddSurname, int idToGo){
        try{
            String insert = "INSERT INTO " + Const.FRIEND_REQUESTS_TABLE + " (fromWho,toWho,isAccepted)" + "VALUES(" +Const.myID+"," + idToGo+","+0+")";
            Statement statement =getDbConnect().createStatement();
            statement.executeUpdate(insert);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }


    public boolean isFriendRequestSend(int myId,int friendId){
        try {
            int hereId=0;
            String select = "SELECT toWho FROM " + Const.FRIEND_REQUESTS_TABLE+" WHERE fromWho="+myId+" AND toWho="+friendId;
            Statement statement = getDbConnect().createStatement();
            ResultSet resultSet = statement.executeQuery(select);
            if (resultSet.next()) {
                hereId = resultSet.getInt(1);
            }
            if (hereId==friendId)
                return true;
            else
                return false;


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void deleteFriendRequest(int myId,int friendId){

        String delete = "DELETE FROM "+Const.FRIEND_REQUESTS_TABLE + " WHERE fromWho="+friendId+ " AND toWho="+myId;
        try {
            Statement statement = getDbConnect().createStatement();
            statement.executeUpdate(delete);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public boolean doIhaveFriendRequest(int myId){
        try {
            int hereId=-1;
            String select = "SELECT fromWho FROM " + Const.FRIEND_REQUESTS_TABLE + " WHERE toWho="+myId;
            Statement statement = getDbConnect().createStatement();
            ResultSet resultSet = statement.executeQuery(select);
            if (resultSet.next()) {
                hereId = resultSet.getInt(1);
            }
            if (hereId==-1)
                return false;
            else
                return true;


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean isFriend(int myId,int friendId){
        String select= "SELECT user1 FROM "+Const.FRIENDS_TABLE+" WHERE user1="+"'"+getLogin(myId)+"'"+" AND user2="+"'"+getLogin(friendId)+"'";
        try {
            Statement statement = getDbConnect().createStatement();
            ResultSet resultSet =statement.executeQuery(select);

            if (resultSet.next())
                return true;
            else
                return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void insertPhotoLocation(){
        String UPDATE = "UPDATE " + Const.USERS_TABLE + " SET photolocation = " + "'"+Const.realLogin+"profilePhoto' " +"WHERE login = " + "'"+Const.realLogin+"'";
        try {
            Statement statement = getDbConnect().createStatement();
            statement.executeUpdate(UPDATE);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean doProfileHasPhoto(int id){
        String select ="SELECT photolocation FROM " + Const.USERS_TABLE + " WHERE id="+id;
        try {
            Statement statement = getDbConnect().createStatement();
            ResultSet resultSet = statement.executeQuery(select);
            resultSet.next();
                if (resultSet.getString(1) == null)
                    return false;
                else
                    return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void openDialog(String chatID){
        try{
            String select ="SELECT whoName,whoSurname,time,text FROM DialogNumber" + chatID;
            Statement statement = getDbConnect().createStatement();
            ResultSet resultSet = statement.executeQuery(select);
        } catch (SQLException e) {
            String create = "CREATE TABLE DialogNumber"+chatID+" (" +
                    "`whoName` VARCHAR(64) NOT NULL , `whoSurname` VARCHAR(64) NOT NULL , `time` VARCHAR(64) NOT NULL , `text` TEXT NOT NULL ) ENGINE = InnoDB";  //CREATE TABLE `u1768436_boysnet`.`DialogNumber` ( `who` VARCHAR(64) NOT NULL , `time` VARCHAR(64) NOT NULL , `text` TEXT NOT NULL ) ENGINE = InnoDB;
            try {
                Statement statement = getDbConnect().createStatement();
                statement.executeUpdate(create);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

        }


    }

    public String getChatId(String login1,String login2){
        try {
            String select ="SELECT chatID FROM " + Const.FRIENDS_TABLE + " WHERE user1=" + "'"+login1+"'" +" AND user2=" + "'"+login2+"'";
            Statement statement = getDbConnect().createStatement();
            ResultSet resultSet = statement.executeQuery(select);
            if (resultSet.next())
                return resultSet.getString(1);
            else
                return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public int getLastChatId(){
        String select = "SELECT chatID FROM " + Const.FRIENDS_TABLE + " ORDER BY chatID DESC LIMIT 1";
        try {
            Statement statement = getDbConnect().createStatement();
            ResultSet resultSet = statement.executeQuery(select);

            if (resultSet.next())
                return resultSet.getInt(1);
            else
                return 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static double bottom;
    public void showDialog(AnchorPane anchorPane){
        try {
            String select = "SELECT whoName,whoSurname,time,text FROM DialogNumber" + getChatId(Const.realLogin,getLogin(Const.idToGo));
            Statement statement = getDbConnect().createStatement();
            ResultSet resultSet = statement.executeQuery(select);
            double height = anchorPane.getHeight();
            double x=10;
            double y=10;
            while (resultSet.next()){
                //friendNameLabel.setFont(new Font("VAG World Bold", 18));

                try {
                    if (getUserLogin(resultSet.getString(2),resultSet.getString(1)).equals(Const.realLogin))
                        x+=800;
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                height+=50;
                Label infoLabel = new Label();
                infoLabel.setFont(new Font("VAG World Bold", 15));
                infoLabel.setLayoutX(x);
                infoLabel.setLayoutY(y);
                infoLabel.setText(resultSet.getString(1) +" "+resultSet.getString(2)+" " +resultSet.getString(3));

                Label textLabel = new Label();
                textLabel.setFont(new Font("VAG World Bold", 20));
                textLabel.setLayoutX(x);
                textLabel.setLayoutY(y+15);
                textLabel.setText(resultSet.getString(4));

                anchorPane.setPrefHeight(height);
                y+=80;
                anchorPane.getChildren().add(infoLabel);
                anchorPane.getChildren().add(textLabel);

                x=10;


            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    public void sendMessage(String whoName,String whoSurname,String time,String text){
        try {
            //String insert1 = "INSERT INTO " + Const.FRIENDS_TABLE + "(user1,user2,chatID) " + "VALUES(" + "'" +getLogin(id) + "'" + ","+"'" +Const.realLogin + "'"+","+newId + ")";
            String insert = "INSERt INTO DialogNumber" + getChatId(Const.realLogin, getLogin(Const.idToGo)) + " (whoName,whoSurname,time,text) " + "VALUES(" +"'"+whoName+"'"+", "+"'"+whoSurname+"'"+", " + "'"+time+"'"+", " + "'"+text+"')";
            Statement statement = getDbConnect().createStatement();
            statement.executeUpdate(insert);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
