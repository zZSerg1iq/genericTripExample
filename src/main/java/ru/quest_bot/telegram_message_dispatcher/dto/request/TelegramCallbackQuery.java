package ru.quest_bot.telegram_message_dispatcher.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TelegramCallbackQuery {
    private String data;
}
