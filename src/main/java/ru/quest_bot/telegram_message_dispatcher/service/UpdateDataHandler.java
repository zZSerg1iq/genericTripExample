package ru.quest_bot.telegram_message_dispatcher.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public interface UpdateDataHandler {

    void processUpdateData(Update update);
}
