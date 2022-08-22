package com.example.boys_net_2.Other;

import com.example.boys_net_2.FriendsController;
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
            connection = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/RHTsMJoorw", "RHTsMJoorw", "kVk4kiaycF");
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ошибка, не удалось подключиться к серверу");

        }

        return connection;
    }




    public String getProfileName(String login) throws SQLException {
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
    public String getProfileSurname(String login) throws SQLException {
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
                    Pane tempPane = new Pane(friendNameLabel, friendSurnameLabel);
                    friendNameLabel.setFont(new Font("VAG World Bold", 18));
                    //friendNameLabel.setLayoutX(xName);
                    //friendNameLabel.setLayoutY(y);


                    friendSurnameLabel.setFont(new Font("VAG World Bold", 18));
                    friendSurnameLabel.setLayoutX(xSurname);
                    //friendSurnameLabel.setLayoutY(y);

                /*Button addFriend = new Button("Добавить в друзья");
                addFriend.setLayoutX(xName+230);
                addFriend.setLayoutY(y);
                pane.getChildren().add(addFriend);*/


                    //pane.getChildren().add(friendNameLabel);
                    //pane.getChildren().add(friendSurnameLabel);

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

    public String getUserLogin(String name,String surname){
        String select = "SELECT login FROM " + Const.USERS_TABLE + " WHERE " + Const.name + "= '"+name+"' AND " + Const.surname + "= '"+surname+"'";
        try {
            Statement statement = getDbConnect().createStatement();
            ResultSet resultSet = statement.executeQuery(select);
            resultSet.next();
            return resultSet.getString(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getLastId(){
        String select = "SELECT id from " + Const.USERS_TABLE + " ORDER BY id DESC LIMIT 1;";
        try {
            Statement statement = getDbConnect().createStatement();
            ResultSet resultSet = statement.executeQuery(select);
            if (resultSet.next()) {
                System.out.println(resultSet.getInt(1));
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
                System.out.println(resultSet.getInt(1));

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
                System.out.println(resultSet.getString(1));

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
                System.out.println(resultSet.getString(1));

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
                System.out.println(resultSet.getInt(1));

                return resultSet.getInt(1);
            }
            else
                return 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public void sendFriendRequest(String wantToAddName, String wantToAddSurname, int idToGo){
        try{
            String insert = "INSERT INTO " + Const.FRIEND_REQUESTS_TABLE + " (fromWho,toWho,isAccepted)" + "VALUES(" +Const.myID+"," + idToGo+","+0+")";
            Statement statement =getDbConnect().createStatement();
            statement.executeUpdate(insert);
            System.out.println("Заявка отправлена");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public boolean isFriendRequestSend(int myId,int friendId){
        try {
            int hereId=0;
            String select = "SELECT toWho FROM " + Const.FRIEND_REQUESTS_TABLE;
            Statement statement = getDbConnect().createStatement();
            ResultSet resultSet = statement.executeQuery(select);
            if (resultSet.next()) {
                hereId = resultSet.getInt(1);
            }
            if (hereId==Const.idToGo)
                return true;
            else
                return false;


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
