package ru.quest_bot.telegram_message_dispatcher.mapper.basic;

import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.quest_bot.telegram_message_dispatcher.jpa.entity.UpdateDataEntity;

import java.util.List;

/**
 * Базовый интерфейс маппинга, предоставляющий методы для реализации по маппингу Telegram Update в Entity,
 * Telegram Update в транспортный тип
 * и транспотный ответ в сообщение Telegram.
 * <p>
 * Маппинг Telegram Update -> Entity реализован в базовом абстрактном классе BasicDataMapper.
 * Для подключения новой реализации взаимодействия между микросервисами необходимо унаследоваться от него и реализовать
 * два оставшихся метода: Update -> RequestType, ResponseType -> TelegramMessage
 *
 * @param <Response> - тип, получаемый при десериализации ответа
 * @param <Request>  - тип, необходимый для сериализации запроса
 */

public interface BasicQuestDataMapper<Response, Request> {

    UpdateDataEntity toEntity(Update update);

    Request toTransportData(Update update);

    List<PartialBotApiMethod<?>> toTelegramMessage(Response responseData);
}
