package com.example.boys_net_2.animations;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class MoveDown {

        private TranslateTransition translateTransition;


        public MoveDown(Node node){
            translateTransition = new TranslateTransition(Duration.millis(1000),node);
            translateTransition.setFromY(0f);
            translateTransition.setByY(10f);
            //translateTransition.setCycleCount(3);
            //translateTransition.setAutoReverse(true);
        }
        public void playAnim(){
            translateTransition.playFromStart();
        }


}
