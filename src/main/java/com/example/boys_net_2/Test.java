package com.example.boys_net_2;

import com.example.boys_net_2.Other.Const;
import javafx.scene.image.Image;

import java.io.File;

public class Test {
    public static void main(String[] args) {
        File file = new File("src/main/resources/userphotos/leomessiprofilePhoto.jpg");
        String path = file.getAbsolutePath();
        System.out.println(file.getAbsolutePath());
        Image image = new Image(path);
        System.out.println(image.getRequestedWidth());
    }
}
