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
        points.clear();
        points.add(new PointSnake(4, 5));
        points.add(new PointSnake(3, 5));
        points.add(new PointSnake(2, 5));
        points.add(new PointSnake(1, 5));
        points.add(new PointSnake(0, 5));
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

    public static void move(Bot bot, Update update) {
        PointSnake head = points.get(0);
        int newX = head.getX();
        int newY = head.getY();

        switch (currentWay) {
            case RIGHT -> newX++;
            case LEFT -> newX--;
            case TOP -> newY--;
            case BOT -> newY++;
        }

        if (newX < 0 || newX >= 10 || newY < 0 || newY >= 10) {
            bot.sendLose(update);
            return;
        }

        for (PointSnake point : points) {
            if (point.getX() == newX && point.getY() == newY) {
                bot.sendLose(update);
                return;
            }
        }

        points.add(0, new PointSnake(newX, newY));
        points.remove(points.size() - 1);

        update();
    }


}
