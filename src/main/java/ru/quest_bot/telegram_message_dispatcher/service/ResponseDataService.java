package ru.quest_bot.telegram_message_dispatcher.service;

import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Сервис пересылки обработанных сообщений обратно пользователю
 */
public class ResponseDataService {

    @Value("${bot.admin.id}")
    private String adminID;

    private ResponseSender responseSender;

    public void setListener(ResponseSender responseSender) {
        this.responseSender = responseSender;
    }

    public <T> void sendMessage(List<T> messageList) throws TelegramApiException {
        responseSender.onNewMessageToSend(messageList);
    }

    public void sendErrorMessage(Update update) throws TelegramApiException {
        Long userChatId = update.hasMessage() ? update.getMessage().getChatId() : update.getCallbackQuery().getMessage().getChatId();
        List<SendMessage> errorMessageList = List.of(SendMessage.builder()
                        .chatId(userChatId)
                        .text("К сожалению, сервис в данное время недоступен.")
                        .build(),
                SendMessage.builder()
                        .chatId(adminID)
                        .text("База данных недступна: " + LocalDateTime
                                .now()
                                .format(DateTimeFormatter.ofPattern("yy.MM.dd hh:mm:ss")))
                        .build());

        responseSender.onNewMessageToSend(errorMessageList);
    }

    public interface ResponseSender {
        <T> void onNewMessageToSend(List<T> message) throws TelegramApiException;
    }
}