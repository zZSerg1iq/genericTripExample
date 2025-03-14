package ru.quest_bot.telegram_message_dispatcher.mapper;

import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.games.Animation;
import org.telegram.telegrambots.meta.api.objects.polls.PollAnswer;
import org.telegram.telegrambots.meta.api.objects.stickers.Sticker;
import ru.quest_bot.grpc.*;
import ru.quest_bot.telegram_message_dispatcher.mapper.basic.BasicDataMapper;

import java.time.LocalDateTime;
import java.util.List;

public class GrpcUpdateDataMapper extends BasicDataMapper<GrpcResponseData, GrpcUpdateData> {

    @Override
    public GrpcUpdateData toTransportData(Update update) {
        GrpcUpdateData.Builder builder = GrpcUpdateData.newBuilder()
                .setReceiveTime(LocalDateTime.now().toString())
                .setFrom(getFrom(update));

        if (update.hasMessage()) {
            builder.setMessage(getMessage(update));
        }
        if (update.hasCallbackQuery()) {
            builder.setCallbackQuery(getCallbackQuery(update));
        }
        if (update.hasPollAnswer()) {
            builder.setPollAnswer(getPollAnswer(update));
        }

        return builder.build();
    }

    private GrpcTelegramMessage getMessage(Update update) {
        Message message = update.hasMessage()? update.getMessage(): (Message) update.getCallbackQuery().getMessage();

        GrpcTelegramMessage.Builder builder = GrpcTelegramMessage.newBuilder()
                .setMessageId(message.getMessageId())
                .setChatId(message.getChatId())
                .setDate(message.getDate());

        if (message.hasText()) {
            builder.setText(message.getText());
        }
        if (message.getCaption() != null) {
            builder.setCaption(message.getCaption());
        }
        if (message.hasPhoto()) {
            builder.addAllPhoto(getPhoto(message));
        }
        if (message.hasSuccessfulPayment()) {
            builder.setSuccessfulPayment(message.hasSuccessfulPayment());
        }
        if (message.getHasMediaSpoiler() != null) {
            builder.setHasMediaSpoiler(message.getHasMediaSpoiler());
        }
        if (message.hasLocation()) {
            builder.setLocation(getLocation(message));
        }
        if (message.getMediaGroupId() != null) {
            builder.setMediaGroupId(message.getMediaGroupId());
        }

        getFile(builder, message);

        return builder.build();
    }

    private GrpcTelegramLocation getLocation(Message message) {
        if (!message.hasLocation()) return null;
        Location location = message.getLocation();
        GrpcTelegramLocation.Builder builder = GrpcTelegramLocation.newBuilder()
                .setLatitude(location.getLatitude())
                .setLongitude(location.getLongitude());

        if (location.getLivePeriod() != null) {
            builder.setLivePeriod(location.getLivePeriod());
        }
        return builder.build();
    }

    private List<GrpcTelegramPhotoSize> getPhoto(Message message) {
        if (!message.hasPhoto()) return null;
        return message.getPhoto().stream().map(this::mapPhoto).toList();
    }

    private GrpcTelegramPhotoSize mapPhoto(PhotoSize photo) {
        if (photo == null) return null;
        GrpcTelegramPhotoSize.Builder builder = GrpcTelegramPhotoSize.newBuilder()
                .setFileId(photo.getFileId())
                .setFileUniqueId(photo.getFileUniqueId())
                .setHeight(photo.getHeight())
                .setWidth(photo.getWidth())
                .setFileSize(photo.getFileSize());

        if (photo.getFilePath() != null) {
            builder.setFilePath(photo.getFilePath());
        }
        return builder.build();
    }

    private void getFile(GrpcTelegramMessage.Builder builder, Message message) {
        if (message.hasAnimation()) {
            builder.setFile(getAnimation(message.getAnimation()));
        } else if (message.hasAudio()) {
            builder.setFile(getAudio(message.getAudio()));
        } else if (message.hasVideo()) {
            builder.setFile(getVideo(message.getVideo()));
        } else if (message.hasDocument()) {
            builder.setFile(getDocument(message.getDocument()));
        } else if (message.hasSticker()) {
            builder.setFile(getSticker(message.getSticker()));
        } else if (message.hasVoice()) {
            builder.setFile(getVoice(message.getVoice()));
        }
    }

    private GrpcBasicFile getAnimation(Animation animation) {
        return GrpcBasicFile.newBuilder()
                .setFileId(animation.getFileId())
                .setFileUniqueId(animation.getFileUniqueId())
                .setFileName(animation.getFileName())
                .setHeight(animation.getHeight())
                .setWidth(animation.getWidth())
                .setDuration(animation.getDuration())
                .setThumbnail(mapPhoto(animation.getThumbnail()))
                .setMimeType(animation.getMimetype())
                .setTelegramFileType(GrpcTelegramFileType.ANIMATION)
                .build();
    }

    private GrpcBasicFile getAudio(Audio audio) {
        return GrpcBasicFile.newBuilder()
                .setFileId(audio.getFileId())
                .setFileUniqueId(audio.getFileUniqueId())
                .setFileName(audio.getFileName())
                .setThumbnail(mapPhoto(audio.getThumbnail()))
                .setDuration(audio.getDuration())
                .setMimeType(audio.getMimeType())
                .setTelegramFileType(GrpcTelegramFileType.AUDIO)
                .build();
    }

    private GrpcBasicFile getVideo(Video video) {
        GrpcBasicFile.Builder builder = GrpcBasicFile.newBuilder()
                .setFileId(video.getFileId())
                .setFileUniqueId(video.getFileUniqueId())
                .setWidth(video.getWidth())
                .setHeight(video.getHeight())
                .setDuration(video.getDuration())
                .setThumbnail(mapPhoto(video.getThumbnail()))
                .setMimeType(video.getMimeType())
                .setTelegramFileType(GrpcTelegramFileType.VIDEO);

        if (video.getFileName() != null) {
            builder.setFileName(video.getFileName());
        }
        return builder.build();
    }

    private GrpcBasicFile getDocument(Document document) {
        GrpcBasicFile.Builder builder = GrpcBasicFile.newBuilder()
                .setFileId(document.getFileId())
                .setFileUniqueId(document.getFileUniqueId())
                .setFileName(document.getFileName())
                .setFileSize(document.getFileSize())
                .setMimeType(document.getMimeType())
                .setTelegramFileType(GrpcTelegramFileType.DOCUMENT);

        if (document.getThumbnail() != null) {
            builder.setThumbnail(mapPhoto(document.getThumbnail()));
        }

        return builder.build();
    }

    private GrpcBasicFile getSticker(Sticker sticker) {
        return GrpcBasicFile.newBuilder()
                .setFileId(sticker.getFileId())
                .setThumbnail(mapPhoto(sticker.getThumbnail()))
                .setFileUniqueId(sticker.getFileUniqueId())
                .setWidth(sticker.getWidth())
                .setHeight(sticker.getHeight())
                .setIsAnimated(sticker.getIsAnimated())
                .setTelegramFileType(GrpcTelegramFileType.STICKER)
                .build();
    }

    private GrpcBasicFile getVoice(Voice voice) {
        return GrpcBasicFile.newBuilder()
                .setFileId(voice.getFileId())
                .setDuration(voice.getDuration())
                .setFileSize(voice.getFileSize())
                .setFileUniqueId(voice.getFileUniqueId())
                .setMimeType(voice.getMimeType())
                .setTelegramFileType(GrpcTelegramFileType.VOICE)
                .build();
    }

    private GrpcTelegramUser getFrom(Update update) {
        User user = update.hasMessage() ? update.getMessage().getFrom() : update.getCallbackQuery().getFrom();

        GrpcTelegramUser.Builder builder = GrpcTelegramUser.newBuilder()
                .setUserId(user.getId())
                .setFirstName(user.getFirstName());

        if (user.getLastName() != null) {
            builder.setLastName(user.getLastName());
        }
        if (user.getUserName() != null) {
            builder.setUserName(user.getUserName());
        }
        if (user.getLanguageCode() != null) {
            builder.setLanguageCode(user.getLanguageCode());
        }

        return builder.build();
    }

    private GrpcTelegramCallbackQuery getCallbackQuery(Update update) {
        if (!update.hasCallbackQuery()) return null;
        CallbackQuery query = update.getCallbackQuery();
        return GrpcTelegramCallbackQuery.newBuilder()
                .setData(query.getData())
                .build();
    }

    private GrpcTelegramPollAnswer getPollAnswer(Update update) {
        if (!update.hasPollAnswer()) return null;
        PollAnswer pollAnswer = update.getPollAnswer();
        return GrpcTelegramPollAnswer.newBuilder()
                .setPollId(pollAnswer.getPollId())
                .addAllOptionIds(pollAnswer.getOptionIds())
                .build();
    }

    @Override
    public List<PartialBotApiMethod<?>> toTelegramMessage(GrpcResponseData responseData) {
        return null;
    }
}
