package com.example.boys_net_2.Other;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.PrintWriter;
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
                Label friendSurnameLabel = new Label(friendSurname);
                friendSurnameLabel.setFont(new Font("VAG World Bold",18));
                friendSurnameLabel.setLayoutX(167);
                friendSurnameLabel.setLayoutY(y);
                pane.getChildren().add(friendNameLabel);
                pane.getChildren().add(friendSurnameLabel);
            }
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
}
