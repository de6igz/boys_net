package com.example.boys_net_2.Other;
import java.sql.*;

public class DataBaseHandler {
        Connection getDbConnect(){
            Connection connection = null;
            try {
                 connection = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/RHTsMJoorw","RHTsMJoorw","kVk4kiaycF");
                return connection;
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Ошибка, не удалось подключиться к серверу");
            }

            return connection;
        }

    public void SignUp(String name, String surname,
                       String login, String password,
                       String gender) throws SQLException {
        String INSERT = "INSERT INTO "+ Const.USERS_TABLE + "("+
                Const.name + "," + Const.surname+","+
                Const.login +"," + Const.password+","+
                Const.gender  + ")"+
                "VALUES(?,?,?,?,?)";
        PreparedStatement preparedStatement = getDbConnect().prepareStatement(INSERT);
        preparedStatement.setString(1,name);
        preparedStatement.setString(2,surname);
        preparedStatement.setString(3,login);
        preparedStatement.setString(4,password);
        preparedStatement.setString(5,gender);

        preparedStatement.executeUpdate();
    }

    public boolean logIn(String login,String password){
        ResultSet resultSet = null;
        String select = "SELECT * FROM " + Const.USERS_TABLE + " WHERE " + Const.login + "=? AND " + Const.password + "=?";
        try {
            PreparedStatement preparedStatement = getDbConnect().prepareStatement(select);
            preparedStatement.setString(1,login);
            preparedStatement.setString(2,password);
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
