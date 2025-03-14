package ru.quest_bot.telegram_message_dispatcher.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.quest_bot.telegram_message_dispatcher.client.ExchangeClient;
import ru.quest_bot.telegram_message_dispatcher.jpa.UpdateDataLogRepositoryService;
import ru.quest_bot.telegram_message_dispatcher.jpa.entity.UpdateDataEntity;
import ru.quest_bot.telegram_message_dispatcher.mapper.basic.BasicQuestDataMapper;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
public class UpdateDataService<Response, Request> {

    private final BasicQuestDataMapper<Response, Request> mapper;
    private final UpdateDataLogRepositoryService repositoryService;
    private final @Lazy ResponseDataService responseDataService;
    private final @Lazy ExchangeClient<Response, Request> client;

    @Transactional
    public void processUpdateData(Update update) {
        UpdateDataEntity entity = saveUserRequest(update);

        Request request = mapper.toTransportData(update);
        Response response = sendRequest(request, entity);
        deleteUserRequest(entity);

        if (response != null) {
            sendResponse(response);
        }
    }

    private void sendResponse(Response response) {
        List<PartialBotApiMethod<?>> messageList = mapper.toTelegramMessage(response);
        try {
            if (messageList != null) {
                responseDataService.sendMessage(messageList);
            }
        } catch (TelegramApiException e) {
            log.error("Ошибка Telegram при отправке ответа: []", e);
        }
    }

    private Response sendRequest(Request request, UpdateDataEntity entity) {
        Response response = null;
        try {
            response = client.sendMessage(request);
            repositoryService.delete(entity);
        } catch (Exception e) {
            log.error("Ошибка gRPC запроса: []", e);
        }
        return response;
    }

    private UpdateDataEntity saveUserRequest(Update update) {
        UpdateDataEntity entity = mapper.toEntity(update);

        try {
            entity = repositoryService.save(entity);
        } catch (Exception e) {
            log.error("Ошибка сохранения в базу данных: []", e);
            try {
                responseDataService.sendErrorMessage(update); //TODO нормальные сообщения (передавать или константы)
            } catch (TelegramApiException ex) {
                log.error("Ошибка Telegram при отправке сообщения об ошибке: []", e);
            }
        }
        return entity;
    }

    private void deleteUserRequest(UpdateDataEntity entity) {
        repositoryService.delete(entity);
    }

}
