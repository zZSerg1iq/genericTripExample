package ru.quest_bot.telegram_message_dispatcher.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.quest_bot.telegram_message_dispatcher.dto.request.message.TelegramUser;

import java.time.LocalDateTime;


/**
 * Содержит данные Telegram Update, присланные пользователем для обработки
 * Так как в случае недоступности сервиса обработки данные отправленные пользователем могут теряться,
 * они записываются в базу и хранятся там до тех пор пока не будут успешно отправлены.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateData {
    private LocalDateTime receiveTime;
    private TelegramUser from;
    private TelegramMessage message;
    private TelegramCallbackQuery callbackQuery;
    private TelegramPollAnswer pollAnswer;
}
