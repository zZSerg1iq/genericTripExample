package ru.quest_bot.telegram_message_dispatcher.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.quest_bot.telegram_message_dispatcher.bot.QuestBot;
import ru.quest_bot.telegram_message_dispatcher.service.ResponseDataService;

import java.util.List;


/**
 * конфигурация класса отправки ответа пользователю
 */
@Configuration
public class ResponseDataServiceConfig {

    @Bean
    public ResponseDataService defaultMessageHandlerListener(@Autowired QuestBot questBot) {
        ResponseDataService responseDataService = new ResponseDataService();
        System.out.println("defaultMessageHandlerListener");
        ResponseDataService.ResponseSender responseSender = new ResponseDataService.ResponseSender() {
            @Override
            public <T> void onNewMessageToSend(List<T> messageList) throws TelegramApiException {
                System.out.println("SENDING........");
                for (T t : messageList) {
                    if (t instanceof BotApiMethod<?>) {
                        questBot.execute((BotApiMethod<?>) t);
                    } else if (t instanceof SendPhoto) {
                        questBot.execute((SendPhoto) t);
                    } else if (t instanceof SendVoice) {
                        questBot.execute((SendVoice) t);
                    } else if (t instanceof SendAudio) {
                        questBot.execute((SendAudio) t);
                    } else if (t instanceof SendDocument) {
                        questBot.execute((SendDocument) t);
                    } else if (t instanceof SendSticker) {
                        questBot.execute((SendSticker) t);
                    } else if (t instanceof SendMediaGroup) {
                        questBot.execute((SendMediaGroup) t);
                    }
                }


            }
        };

        responseDataService.setListener(responseSender);
        return responseDataService;
    }
}
