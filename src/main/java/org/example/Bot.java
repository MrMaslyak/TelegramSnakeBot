package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {
    private Long chatID;
    private Integer lastMessageId;
    private boolean isAlive;
    private Move move;

    @Override
    public String getBotUsername() {
        return "MaslyakSnakebot";
    }

    @Override
    public String getBotToken() {
        return "7283118194:AAGP5QjmCicTM3DZXkA1ZWumMPAG-g442wU";
    }

    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String text = update.getMessage().getText();
            long userId = update.getMessage().getFrom().getId();

            if (text.equals("/start")) {
                sendWelcomeMessage(userId);
            } else if (text.equals("Start")) {
                start(update);
            } else if (text.equals("⬇️") || text.equals("⬅️") || text.equals("➡️") || text.equals("⬆️")) {
                Snake.changeSide(text);
                Snake.move();
                send();
            }
        }
    }

    private void sendWelcomeMessage(Long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(getWelcomeMessage());

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add(new KeyboardButton("Start"));
        keyboard.add(row);
        keyboardMarkup.setKeyboard(keyboard);
        message.setReplyMarkup(keyboardMarkup);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    void start(Update update) {
        Message message = update.getMessage();
        chatID = message.getChatId();
        String text = message.getText();

        if (!isAlive) {
            move = new Move(this);
            move.start();
            isAlive = true;
        }
        if (update.hasMessage() && update.getMessage().hasText()) {
            if (text.equals("⬇️") || text.equals("⬅️") || text.equals("➡️") || text.equals("⬆️")) {
                Snake.changeSide(text);
            }
        }
    }

     void send(){
        SendMessage response = new SendMessage();
        response.setChatId(String.valueOf(chatID));
        response.setText(Snake.parseSnake());
        response.setReplyMarkup(SettingGame.getKeyboard());
        ;
        try {
            Message sentMessage = execute(response);
            lastMessageId = sentMessage.getMessageId();
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }



    public void sendText(Long who, String what) {
        SendMessage sm = SendMessage.builder()
                .chatId(who.toString())
                .text(what)
                .parseMode("Markdown")
                .build();
        try {
            execute(sm);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    void delete() {
        if (lastMessageId == null)
            return;
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(String.valueOf(chatID));
        deleteMessage.setMessageId(lastMessageId);

        try {
            execute(deleteMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private String getWelcomeMessage() {
        return "🐍 Добро пожаловать в игру 'Змейка'! 🎮\n" +
                "\n" +
                "🎯 Управляй змейкой, собирай яблоки 🍎 и не врезайся в стены или в себя! 😱\n" +
                "\n" +
                "🕹️ Управление:\n" +
                "⬆️ *Вверх*\n" +
                "⬇️ *Вниз*\n" +
                "⬅️ *Влево*\n" +
                "➡️ *Вправо*\n" +
                "\n" +
                "⚡ Каждое яблоко делает твою змейку длиннее и игру сложнее! Попробуй набрать максимальное количество очков! 🌟\n" +
                "\n" +
                "🚀 Готов? Вперёд, к победе! 🏆\n";
    }
}
