package ru.quest_bot.telegram_message_dispatcher.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Содержит ответ сервиса обработки, полученный в результате обработки запроса пользователя
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseData {
    private List<ResponseMessageData> messageList;
}
