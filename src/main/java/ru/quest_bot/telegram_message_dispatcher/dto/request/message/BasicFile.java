package ru.quest_bot.telegram_message_dispatcher.dto.request.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.quest_bot.telegram_message_dispatcher.dto.TelegramFileType;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BasicFile {
    private TelegramFileType telegramFileType;
    private String fileId;
    private String fileUniqueId;
    private Integer width;
    private Integer height;
    private Integer duration;
    private Long fileSize;
    private String fileName;
    private String mimeType;
    private String type;
    private String emoji;
    private String setName;
    private Boolean isAnimated;
    private Boolean isVideo;
    private TelegramPhotoSize thumbnail;
}
