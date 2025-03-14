package ru.quest_bot.telegram_message_dispatcher.jpa.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ru.quest_bot.telegram_message_dispatcher.jpa.entity.message.UserEntity;

import java.time.LocalDateTime;


/**
 * Содержит данные Telegram Update, присланные пользователем для обработки
 * Так как в случае недоступности сервиса обработки данные отправленные пользователем могут теряться,
 * они записываются в базу и хранятся там до тех пор пока не будут успешно отправлены.
 */
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private LocalDateTime receiveTime;

    @OneToOne(cascade = CascadeType.ALL)
    private UserEntity from;

    @OneToOne(cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    private MessageEntity message;

    @OneToOne(cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    private CallbackQueryEntity callbackQuery;

    @OneToOne(cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    private PollAnswerEntity pollAnswer;

    @Override
    public String toString() {
        return "UpdateData{" +
                "id=" + id +
                ", receiveTime=" + receiveTime +
                '}';
    }
}
