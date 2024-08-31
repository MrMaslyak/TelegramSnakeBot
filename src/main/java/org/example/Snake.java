package org.example;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;

import java.util.ArrayList;

public class Snake {
    static String[][] dataSnake = new String[10][10];
    static boolean isAlive;
    static Way currentWay = Way.RIGHT;
    static ArrayList<PointSnake> points = new ArrayList<>();

    static void init() {
        points.add(new PointSnake(4, 5)); // Head of the snake
        points.add(new PointSnake(3, 5));
        points.add(new PointSnake(2, 5)); // Tail of the snake
        update();
    }

    private static void update() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                dataSnake[i][j] = "⬜";
            }
        }
        for (int i = 0; i < points.size(); i++) {
            dataSnake[points.get(i).getY()][points.get(i).getX()] = "🟩";
        }
    }

    public static String parseSnake() {
        if (!isAlive) {
            init();
            isAlive = true;
        }

        String response = "";
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                response += dataSnake[i][j];
            }
            response += "\n";
        }
        return response;
    }

    public static void changeSide(String text) {
        switch (text) {
            case "⬅️":
                if (currentWay != Way.RIGHT) currentWay = Way.LEFT;
                break;
            case "⬆️":
                if (currentWay != Way.BOT) currentWay = Way.TOP;
                break;
            case "➡️":
                if (currentWay != Way.LEFT) currentWay = Way.RIGHT;
                break;
            case "⬇️":
                if (currentWay != Way.TOP) currentWay = Way.BOT;
                break;
        }
    }

    public static void move() {
        // Save the previous positions of all segments
        PointSnake prevPosition = new PointSnake(points.get(0).getX(), points.get(0).getY());
        PointSnake tempPosition;

        // Move the head
        switch (currentWay) {
            case RIGHT -> points.get(0).setX(points.get(0).getX() + 1);
            case LEFT -> points.get(0).setX(points.get(0).getX() - 1);
            case TOP -> points.get(0).setY(points.get(0).getY() - 1);
            case BOT -> points.get(0).setY(points.get(0).getY() + 1);
        }

        // Move the rest of the body
        for (int i = 1; i < points.size(); i++) {
            tempPosition = new PointSnake(points.get(i).getX(), points.get(i).getY());
            points.get(i).setX(prevPosition.getX());
            points.get(i).setY(prevPosition.getY());
            prevPosition = tempPosition;
        }

        update();
    }
}
