package com.example.boys_net_2.Other;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

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

    //static HashMap<Label,Label> nameSurnameFriends = new HashMap<>();
    static Pane friendsPane = new Pane();

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
            DataBaseHandler.friendsPane = pane;
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    public void SignUp(String name, String surname,
                       String login, String password,
                       String gender) throws SQLException {
        String INSERT = "INSERT INTO " + Const.USERS_TABLE + "(" +
                Const.name + "," + Const.surname + "," +
                Const.login + "," + Const.password + "," +
                Const.gender + ")" +
                "VALUES(?,?,?,?,?)";
        PreparedStatement preparedStatement = getDbConnect().prepareStatement(INSERT);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, surname);
        preparedStatement.setString(3, login);
        preparedStatement.setString(4, password);
        preparedStatement.setString(5, gender);

        preparedStatement.executeUpdate();
    }

    public boolean logIn(String login, String password) {
        ResultSet resultSet = null;
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
                y+=40;
                String friendName = resultSet.getString("name");
                String friendSurname = resultSet.getString("surname");
                Label friendNameLabel = new Label(friendName);
                Label friendSurnameLabel = new Label(friendSurname);
                Pane tempPane = new Pane(friendNameLabel,friendSurnameLabel);
                friendNameLabel.setFont(new Font("VAG World Bold",18));
                //friendNameLabel.setLayoutX(xName);
                //friendNameLabel.setLayoutY(y);



                friendSurnameLabel.setFont(new Font("VAG World Bold",18));
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
                if (y>=462){
                    y=150;
                    xName+=350;
                    xSurname+=350;
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
}
