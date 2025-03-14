package ru.quest_bot.telegram_message_dispatcher.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class KeyboardButtonData {

    private String text;
    private String url;
    private String callbackData;
}
