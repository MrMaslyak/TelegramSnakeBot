package org.example;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;

public class Snake {
    static String[][] dataSnake = new String[10][10];
    static boolean isAlive;
    static Way currentWay = Way.RIGHT;
    static ArrayList<PointSnake> points = new ArrayList<>();

    static void init(){
        points.add(new PointSnake(2,5));
        points.add(new PointSnake(3,5));
        points.add(new PointSnake(4,5));
        update();
    }

    private static void update(){

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                dataSnake[i][j]="⬜";
            }
        }
        for (int i = 0; i < points.size(); i++) {
            dataSnake[points.get(i).getY()][points.get(i).getX()] = "🟩";

        }

    }


    public static String parseSnake(){
        if(!isAlive){
            init();
            isAlive = true;
        }

        String response = "";
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                response+=dataSnake[i][j];
            }
            response+="\n";
        }
        return response;
    }

    public static void changeSide(String text) {
        switch (text) {
            case "⬅️": currentWay = Way.LEFT; break;
            case "⬆️": currentWay = Way.TOP; break;
            case "➡️": currentWay = Way.RIGHT; break;
            case "⬇️": currentWay = Way.BOT; break;
        }
    }

    public static void move() {
        switch (currentWay){
            case RIGHT -> {
                for (int i = 0; i < points.size(); i++) {
                    points.get(i).setX(points.get(i).getX()+1);
                }
            }

            case LEFT -> {
                for (int i = 0; i < points.size(); i++) {
                    points.get(i).setX(points.get(i).getX()-1);
                }
            }
            case TOP -> {
                for (int i = 0; i < points.size(); i++) {
                    points.get(i).setY(points.get(i).getY()-1);
                }
            }
            case BOT -> {
                for (int i = 0; i < points.size(); i++) {
                    points.get(i).setY(points.get(i).getY()+1);
                }

            }
        }

        update();
    }
}
