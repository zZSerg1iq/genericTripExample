package ru.quest_bot.telegram_message_dispatcher.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурация клиента пересылки данных в сервис обработки
 */
@Configuration
@ConditionalOnProperty(name = "rest.enabled", havingValue = "true")
@EnableFeignClients(basePackages = "ru.quest_bot.telegram_message_dispatcher.client")
public class FeignConfig {
}
