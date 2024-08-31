package org.example;

public class Move extends Thread {
    private Bot bot;

    public Move(Bot bot) {
        this.bot = bot;
    }

    @Override
    public void run() {

        while (true) {
            Snake.move();
            bot.delete();
            bot.send();
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
