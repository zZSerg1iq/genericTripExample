package ru.quest_bot.telegram_message_dispatcher.jpa.entity.message;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ru.quest_bot.telegram_message_dispatcher.dto.TelegramFileType;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BasicFileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

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

    @OneToOne(cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    private PhotoSizeEntity thumbnail;
}
