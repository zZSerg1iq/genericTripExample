package ru.quest_bot.telegram_message_dispatcher.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.quest_bot.telegram_message_dispatcher.jpa.entity.message.BasicFileEntity;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMessageData {

    //Тип сообщения
    private MessageType messageType;

    //body
    private Integer messageId;
    private Long chatId;
    private String inlineMessageId;
    private String text;
    private String parseMode;
    private KeyboardMarkupData replyMarkup;

    //голосовалка
    private PollData pollDTO;

    //Приложение
    private BasicFileEntity file;
    private Boolean hasMediaSpoiler;
}
