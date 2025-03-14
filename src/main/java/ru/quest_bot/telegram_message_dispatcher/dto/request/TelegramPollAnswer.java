package ru.quest_bot.telegram_message_dispatcher.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TelegramPollAnswer {
    private String pollId;
    private List<Integer> optionIds;
}
