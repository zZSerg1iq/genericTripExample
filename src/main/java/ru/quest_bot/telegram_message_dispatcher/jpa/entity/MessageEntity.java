package ru.quest_bot.telegram_message_dispatcher.jpa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ru.quest_bot.telegram_message_dispatcher.jpa.entity.message.BasicFileEntity;
import ru.quest_bot.telegram_message_dispatcher.jpa.entity.message.LocationEntity;
import ru.quest_bot.telegram_message_dispatcher.jpa.entity.message.PhotoSizeEntity;

import java.sql.Blob;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private Integer messageId;
    private boolean isEditedMessage;

    private Long chatId;
    private Integer date;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String text;

    @OneToOne(cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    private BasicFileEntity file;
    private Boolean hasMediaSpoiler;

    @OneToMany(cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    private List<PhotoSizeEntity> photo;
    private String mediaGroupId;
    private String caption;

    @OneToOne(cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    private LocationEntity location;

    private Boolean successfulPayment;
}
