package ru.quest_bot.telegram_message_dispatcher.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class PollData {
    private String question;
    private List<String> options;
    private Boolean isAnonymous;
    private String type;
    private Boolean allowMultipleAnswers;
    private Integer correctOptionId;
    private Boolean isClosed;
    private Integer openPeriod;
    private String explanation;
    private String explanationParseMode;
    private Boolean protectContent;
}
