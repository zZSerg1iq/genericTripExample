package ru.quest_bot.telegram_message_dispatcher.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.quest_bot.telegram_message_dispatcher.bot.QuestBot;
import ru.quest_bot.telegram_message_dispatcher.service.UpdateDataHandler;


/**
 * конфигурация самого бота
 */
@Slf4j
@Configuration
public class QuestBotConfig {

    @Value("${bot.name}")
    private String botName;
    @Value("${bot.token}")
    private String botToken;

    @Bean
    @ConditionalOnProperty(name = "rest.enabled", havingValue = "true")
    public QuestBot getRestMessageDispatcher(@Lazy @Qualifier("restAscyncUpdateDataHandler") UpdateDataHandler updateDataHandler) {
        log.info("REST Bot is active");
        System.out.println("REST Bot is active");

        QuestBot questBot = new QuestBot(botName, botToken, updateDataHandler);
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(questBot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        return questBot;
    }

    @Bean
    @ConditionalOnProperty(name = "grpc.enabled", havingValue = "true")
    public QuestBot grpcMessageDispatcher(@Lazy @Qualifier("grpcUpdateDataHandler") UpdateDataHandler updateDataHandler) {

        log.info("GRPC Bot is active");
        System.out.println("GRPC Bot is active");
        QuestBot questBot = new QuestBot(botName, botToken, updateDataHandler);
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(questBot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        return questBot;
    }


}
