package ru.quest_bot.telegram_message_dispatcher.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.quest_bot.telegram_message_dispatcher.dto.request.message.BasicFile;
import ru.quest_bot.telegram_message_dispatcher.dto.request.message.TelegramLocation;
import ru.quest_bot.telegram_message_dispatcher.dto.request.message.TelegramPhotoSize;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TelegramMessage {
    private Integer messageId;
    private boolean isEditedMessage;
    private Long chatId;
    private Integer date;
    private String text;
    private BasicFile file;
    private Boolean hasMediaSpoiler;
    private List<TelegramPhotoSize> photo;
    private String mediaGroupId;
    private String caption;
    private TelegramLocation location;
    private Boolean successfulPayment;
}
