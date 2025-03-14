package ru.quest_bot.telegram_message_dispatcher.dto.request.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TelegramUser {
    private Long userId;
    private String firstName;
    private String lastName;
    private String userName;
    private String languageCode;
}
