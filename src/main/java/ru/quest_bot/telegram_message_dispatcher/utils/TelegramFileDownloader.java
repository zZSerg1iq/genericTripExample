package ru.quest_bot.telegram_message_dispatcher.utils;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;

/**
 * сервис скачивания файлов из телеги
 */
@Component
public class TelegramFileDownloader {

    private Listener listener;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public File downloadFile(String fileId) throws TelegramApiException {
        return listener.downloadFile(fileId);
    }

    public interface Listener {
        File downloadFile(String fileId) throws TelegramApiException;
    }
}