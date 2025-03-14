package ru.quest_bot.telegram_message_dispatcher.dto.request.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TelegramLocation {
    private Double longitude;
    private Double latitude;
    private Integer livePeriod;
}
