package org.example;

import org.telegram.telegrambots.meta.api.objects.Update;

public class Move extends Thread {
    private Bot bot;
    private Update update;

    public Move(Bot bot, Update update) {
        this.bot = bot;
        this.update = update;
    }

    @Override
    public void run() {
        try {
            while (!isInterrupted()) {
                Snake.move(bot, update);
                bot.delete();
                bot.send();
                Thread.sleep(1500);
            }
        } catch (InterruptedException e) {
            System.out.println("Game thread interrupted and stopped.");
        }
    }
}
