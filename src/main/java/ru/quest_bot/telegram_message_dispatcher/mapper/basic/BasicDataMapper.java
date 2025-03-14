package ru.quest_bot.telegram_message_dispatcher.mapper.basic;

import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.games.Animation;
import org.telegram.telegrambots.meta.api.objects.polls.PollAnswer;
import org.telegram.telegrambots.meta.api.objects.stickers.Sticker;
import ru.quest_bot.telegram_message_dispatcher.dto.TelegramFileType;
import ru.quest_bot.telegram_message_dispatcher.jpa.entity.CallbackQueryEntity;
import ru.quest_bot.telegram_message_dispatcher.jpa.entity.MessageEntity;
import ru.quest_bot.telegram_message_dispatcher.jpa.entity.PollAnswerEntity;
import ru.quest_bot.telegram_message_dispatcher.jpa.entity.UpdateDataEntity;
import ru.quest_bot.telegram_message_dispatcher.jpa.entity.message.BasicFileEntity;
import ru.quest_bot.telegram_message_dispatcher.jpa.entity.message.LocationEntity;
import ru.quest_bot.telegram_message_dispatcher.jpa.entity.message.PhotoSizeEntity;
import ru.quest_bot.telegram_message_dispatcher.jpa.entity.message.UserEntity;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Базовый класс маппера, реазизующий Telegram Update -> Entity,
 * <p>
 * Для подключения новой реализации взаимодействия между микросервисами необходимо унаследоваться от него и реализовать
 * два оставшихся метода: Update -> RequestType, ResponseType -> TelegramMessage
 *
 * @param <Response> - тип, получаемый при десериализации ответа
 * @param <Request>  - тип, необходимый для сериализации запроса
 */
public abstract class BasicDataMapper<Response, Request> implements BasicQuestDataMapper<Response, Request> {

    protected TelegramMessageBuilder telegramMessageBuilder;

    public UpdateDataEntity toEntity(Update update) {
        return UpdateDataEntity.builder()
                .receiveTime(LocalDateTime.now())
                .from(getFrom(update))
                .message(getMessage(update))
                .callbackQuery(getCallbackQuery(update))
                .pollAnswer(getPollAnswer(update))
                .build();
    }

    private MessageEntity getMessage(Update update) {
        Message message = update.hasMessage() ? update.getMessage() : (Message) update.getCallbackQuery().getMessage();

        return MessageEntity.builder()
                .text(message.getText())
                .messageId(message.getMessageId())
                .chatId(message.getChatId())
                .caption(message.getCaption())
                .date(message.getDate())
                .file(getFile(message))
                .hasMediaSpoiler(message.getHasMediaSpoiler())
                .photo(getPhoto(message))
                .location(getLocation(message))
                .mediaGroupId(message.getMediaGroupId())
                .successfulPayment(message.hasSuccessfulPayment())
                .build();
    }

    private LocationEntity getLocation(Message message) {
        if (!message.hasLocation()) return null;

        Location location = message.getLocation();
        return LocationEntity.builder()
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .livePeriod(location.getLivePeriod())
                .build();
    }

    private List<PhotoSizeEntity> getPhoto(Message message) {
        if (!message.hasPhoto()) return null;
        return message.getPhoto().stream().map(this::mapPhoto).toList();
    }

    private PhotoSizeEntity mapPhoto(PhotoSize photo) {
        if (photo == null) return null;
        return PhotoSizeEntity.builder()
                .fileId(photo.getFileId())
                .fileUniqueId(photo.getFileUniqueId())
                .filePath(photo.getFilePath())
                .height(photo.getHeight())
                .width(photo.getWidth())
                .fileSize(photo.getFileSize())
                .build();
    }

    private BasicFileEntity getFile(Message message) {
        if (message.hasAnimation()) {
            return getAnimation(message.getAnimation());
        } else if (message.hasAudio()) {
            return getAudio(message.getAudio());
        } else if (message.hasVideo()) {
            return getVideo(message.getVideo());
        } else if (message.hasDocument()) {
            return getDocument(message.getDocument());
        } else if (message.hasSticker()) {
            return getSticker(message.getSticker());
        } else if (message.hasVoice()) {
            return getVoice(message.getVoice());
        }
        return null;
    }

    private BasicFileEntity getAnimation(Animation animation) {
        return BasicFileEntity.builder()
                .fileId(animation.getFileId())
                .fileUniqueId(animation.getFileUniqueId())
                .fileName(animation.getFileName())
                .height(animation.getHeight())
                .width(animation.getWidth())
                .duration(animation.getDuration())
                .thumbnail(mapPhoto(animation.getThumbnail()))
                .mimeType(animation.getMimetype())
                .telegramFileType(TelegramFileType.ANIMATION)
                .build();
    }

    private BasicFileEntity getAudio(Audio audio) {
        return BasicFileEntity.builder()
                .fileId(audio.getFileId())
                .fileUniqueId(audio.getFileUniqueId())
                .fileName(audio.getFileName())
                .thumbnail(mapPhoto(audio.getThumbnail()))
                .duration(audio.getDuration())
                .mimeType(audio.getMimeType())
                .telegramFileType(TelegramFileType.AUDIO)
                .build();
    }

    private BasicFileEntity getVideo(Video video) {
        return BasicFileEntity.builder()
                .fileId(video.getFileId())
                .fileUniqueId(video.getFileUniqueId())
                .fileName(video.getFileName())
                .width(video.getWidth())
                .height(video.getHeight())
                .duration(video.getDuration())
                .thumbnail(mapPhoto(video.getThumbnail()))
                .mimeType(video.getMimeType())
                .telegramFileType(TelegramFileType.VIDEO)
                .build();
    }

    private BasicFileEntity getDocument(Document document) {
        return BasicFileEntity.builder()
                .fileId(document.getFileId())
                .fileUniqueId(document.getFileUniqueId())
                .fileName(document.getFileName())
                .thumbnail(mapPhoto(document.getThumbnail()))
                .fileSize(document.getFileSize())
                .mimeType(document.getMimeType())
                .telegramFileType(TelegramFileType.DOCUMENT)
                .build();
    }

    private BasicFileEntity getSticker(Sticker sticker) {
        return BasicFileEntity.builder()
                .fileId(sticker.getFileId())
                .thumbnail(mapPhoto(sticker.getThumbnail()))
                .fileUniqueId(sticker.getFileUniqueId())
                .width(sticker.getWidth())
                .height(sticker.getHeight())
                .isAnimated(sticker.getIsAnimated())
                .telegramFileType(TelegramFileType.STICKER)
                .build();
    }

    private BasicFileEntity getVoice(Voice voice) {
        return BasicFileEntity.builder()
                .fileId(voice.getFileId())
                .duration(voice.getDuration())
                .fileSize(voice.getFileSize())
                .fileUniqueId(voice.getFileUniqueId())
                .mimeType(voice.getMimeType())
                .telegramFileType(TelegramFileType.VOICE)
                .build();
    }

    private UserEntity getFrom(Update update) {
        User user = update.hasMessage() ? update.getMessage().getFrom() : update.getCallbackQuery().getFrom();
        return UserEntity.builder()
                .userId(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .userName(user.getUserName())
                .languageCode(user.getLanguageCode())
                .build();
    }

    private CallbackQueryEntity getCallbackQuery(Update update) {
        if (!update.hasCallbackQuery()) return null;

        CallbackQuery query = update.getCallbackQuery();
        return CallbackQueryEntity.builder()
                .data(query.getData())
                .build();
    }

    private PollAnswerEntity getPollAnswer(Update update) {
        if (!update.hasPollAnswer()) return null;

        PollAnswer pollAnswer = update.getPollAnswer();
        return PollAnswerEntity.builder()
                .pollId(pollAnswer.getPollId())
                .optionIds(pollAnswer.getOptionIds())
                .build();
    }
}
