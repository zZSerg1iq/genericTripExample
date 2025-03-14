package ru.quest_bot.telegram_message_dispatcher.bot;

import org.springframework.context.annotation.Lazy;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.quest_bot.telegram_message_dispatcher.service.UpdateDataHandler;
import ru.quest_bot.telegram_message_dispatcher.service.UpdateDataService;

public class QuestBot extends TelegramLongPollingBot {
    private final String botName;
   private final @Lazy UpdateDataHandler updateDataHandler; //Сервис отправки сообщений

    public QuestBot(String botName, String botToken, UpdateDataHandler updateDataHandler) {
        super(botToken);
        this.botName = botName;
        this.updateDataHandler = updateDataHandler;
    }

    @Override
    public void onUpdateReceived(Update update) {
        System.out.println(update);
        updateDataHandler.processUpdateData(update);
    }

    @Override
    public String getBotUsername() {
        return botName;
    }
}
