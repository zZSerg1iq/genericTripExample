package ru.quest_bot.telegram_message_dispatcher.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import ru.quest_bot.grpc.GrpcResponseData;
import ru.quest_bot.grpc.GrpcUpdateData;
import ru.quest_bot.telegram_message_dispatcher.client.ExchangeClient;
import ru.quest_bot.telegram_message_dispatcher.dto.request.UpdateData;
import ru.quest_bot.telegram_message_dispatcher.dto.response.ResponseData;
import ru.quest_bot.telegram_message_dispatcher.jpa.UpdateDataLogRepositoryService;
import ru.quest_bot.telegram_message_dispatcher.mapper.basic.BasicQuestDataMapper;
import ru.quest_bot.telegram_message_dispatcher.service.ResponseDataService;
import ru.quest_bot.telegram_message_dispatcher.service.UpdateDataService;


/**
 * конфигурация интерфейса отправки сообщений
 */
@Slf4j
@Configuration
public class UpdateDataServiceConfig {

    @Bean
    @ConditionalOnProperty(name = "rest.enabled", havingValue = "true")
    public UpdateDataService<ResponseData, UpdateData> restAsyncUpdateDataService(
            @Qualifier("restDataMapper") BasicQuestDataMapper<ResponseData, UpdateData> mapper,
            UpdateDataLogRepositoryService repositoryService,
            ResponseDataService responseDataService,
            ExchangeClient<ResponseData, UpdateData> feignClient) {

        log.info("restAsyncUpdateDataService is enabled");
        System.out.println("restAsyncUpdateDataService");

        return new UpdateDataService<>(mapper, repositoryService, responseDataService, feignClient);
    }

    @Bean
    @ConditionalOnProperty(name = "grpc.enabled", havingValue = "true")
    public UpdateDataService<GrpcResponseData, GrpcUpdateData> grpcUpdateDataService(
            @Qualifier("grpcDataMapper") BasicQuestDataMapper<GrpcResponseData, GrpcUpdateData> mapper,
            UpdateDataLogRepositoryService repositoryService,
            ResponseDataService responseDataService,
            ExchangeClient<GrpcResponseData, GrpcUpdateData> grpcClient) {

        log.info("grpcUpdateDataService is enabled");
        System.out.println("grpcUpdateDataService");
        return new UpdateDataService<>(mapper, repositoryService, responseDataService, grpcClient);
    }


    /**
     * Основной класс обмена сообщениями. Принимает сообщения из телеги, отправляет в другой сервис и в случае синхронной
     * реализации возвращает ответ пользователю
     * <p>
     * Представляет собой некоторую адаптацию паттерна Outbox: входяшие от телеграмма сообщения записываются в базу и
     * отправляются в сервис обработки. Если отправка успешна - данные удаляются из базы, в противном случае - остаются
     * и пытаются отправиться по таймеру спустя какое-то время.
     * <p>
     * Оперирует сущностями базы данных поскольку запросы не подвергаются изменению. Только запись. Следовательно,
     * сериализуемые данные не могут оказаться неперсистентными
     *
     * @param mapper            - класс маппинга данных
     * @param questFeignClient  - клиент отправки сообщений сервису обработки
     * @param repositoryService - репозиторий для взаимодействия с БД
     * @return бин скласса управления передачей соообщений
     */
//    @Bean("asyncRestService")
//    @ConditionalOnProperty(name = "async.enabled", havingValue = "true")
//    UpdateDataService getAsyncRestUpdateDataService(@Qualifier("restDataMapper") BasicQuestDataMapper<ResponseData, UpdateData> mapper,
//                                                    QuestFeignClient questFeignClient,
//                                                    UpdateDataLogRepositoryService repositoryService,
//                                                    @Lazy ResponseDataService responseDataService) {
//
//        UpdateDataService.UpdateDataHandler handler = new UpdateDataService.UpdateDataHandler() {
//            @Override
//            @Transactional
//            public void processUpdateData(Update update) {
//                UpdateDataEntity updateDataEntity = mapper.toEntity(update);
//                UpdateData updateData = mapper.toTransportData(update);
//
//                try {
//                    updateDataEntity = repositoryService.save(updateDataEntity);
//                } catch (Exception e) {
//                    log.error("Ошибка сохранения в базу данных: []", e);
//
//                    try {
//                        responseDataService.sendMessage(responseDataService.sendErrorMessage(update));
//                    } catch (TelegramApiException ex) {
//                        throw new RuntimeException(ex);
//                    }
//                    return;
//                }
//
//                try {
//                    questFeignClient.sendUpdateData(updateDataEntity); // Отправляем данные сервису обработки
//                    repositoryService.delete(updateDataEntity); // Если всё прошло успешно, удаляем запись из базы
//                } catch (FeignException e) {
//                    log.error("Ошибка при вызове Feign-клиента: []", e);
//                }
//            }
//        };
//
//        return new UpdateDataService(handler, mapper, repositoryService, responseDataService);
//    }
//
//    @Bean("grpcService")
//    @ConditionalOnProperty(name = "grpc.enabled", havingValue = "true")
//    UpdateDataService getGrpsSyncUpdateDataService(@Qualifier("grpcDataMapper") BasicQuestDataMapper<GrpcResponseData, GrpcUpdateData> mapper,
//                                                   UpdateDataLogRepositoryService repositoryService,
//                                                   QuestGrpcClient client,
//                                                   @Lazy ResponseDataService responseDataService) {
//
//
//        UpdateDataService.UpdateDataHandler handler = new UpdateDataService.UpdateDataHandler() {
//
//            @Override
//            @Transactional
//            public void processUpdateData(Update update) {
//                UpdateDataEntity updateDataEntity = mapper.toEntity(update);
//
//                try {
//                    updateDataEntity = repositoryService.save(updateDataEntity);
//                } catch (Exception e) {
//                    log.error("Ошибка сохранения в базу данных: []", e);
//
//                    try {
//                        responseDataService.sendMessage(responseDataService.sendErrorMessage(update));
//                    } catch (TelegramApiException ex) {
//                        log.error("Ошибка Telegram при отправке сообщения об ошибке: []", e);
//                    }
//
//                    return;
//                }
//
//                GrpcUpdateData grpcUpdateData = mapper.toTransportData(update);
//                GrpcResponseData responseData = null;
//
//                try {
//                    responseData = client.sendMessage(grpcUpdateData); // Отправляем данные сервису обработки
//                    repositoryService.delete(updateDataEntity); // Если всё прошло успешно, удаляем запись из базы
//                } catch (Exception e) {
//                    log.error("Ошибка gRPC запроса: []", e);
//                    return;
//                }
//
//                List<PartialBotApiMethod<?>> messageList = mapper.toTelegramMessage(responseData);
//                try {
//                    if (messageList != null) {
//                        responseDataService.sendMessage(messageList);
//                    }
//                } catch (TelegramApiException e) {
//                    log.error("Ошибка Telegram при отправке ответа: []", e);
//                }
//            }
//        };
//
//        return new UpdateDataService(handler, mapper, repositoryService, responseDataService);
//    }
//
//
////    @Bean("asyncRestService")
////    @ConditionalOnProperty(name = "async.enabled", havingValue = "true")
////    UpdateDataService getAsyncRestUpdateDataService(@Qualifier("restDataMapper") BasicQuestDataMapper<ResponseData, UpdateData> mapper,
////                                                    QuestFeignClient questFeignClient,
////                                                    UpdateDataLogRepositoryService repositoryService,
////                                                    @Lazy ResponseDataService responseDataService) {
////
////        UpdateDataService.UpdateDataHandler handler = new UpdateDataService.UpdateDataHandler() {
////            @Override
////            @Transactional
////            public void processUpdateData(Update update) {
////                UpdateDataEntity updateDataEntity = mapper.toEntity(update);
////                UpdateData updateData = mapper.toTransportData(update);
////
////                try {
////                    updateDataEntity = repositoryService.save(updateDataEntity);
////                } catch (Exception e) {
////                    log.error("Ошибка сохранения в базу данных: []", e);
////
////                    try {
////                        responseDataService.sendMessage(sendErrorMessage(update));
////                    } catch (TelegramApiException ex) {
////                        throw new RuntimeException(ex);
////                    }
////                    return;
////                }
////
////                try {
////                    questFeignClient.sendUpdateData(updateDataEntity); // Отправляем данные сервису обработки
////                    repositoryService.delete(updateDataEntity); // Если всё прошло успешно, удаляем запись из базы
////                } catch (FeignException e) {
////                    log.error("Ошибка при вызове Feign-клиента: []", e);
////                }
////            }
////        };
////
////        return new UpdateDataService(handler, mapper, repositoryService, responseDataService);
////    }
////
////    @Bean("grpcService")
////    @ConditionalOnProperty(name = "grpc.enabled", havingValue = "true")
////    UpdateDataService getGrpsSyncUpdateDataService(@Qualifier("grpcDataMapper") BasicQuestDataMapper<GrpcResponseData, GrpcUpdateData> mapper,
////                                                   UpdateDataLogRepositoryService repositoryService,
////                                                   QuestGrpcClient client,
////                                                   @Lazy ResponseDataService responseDataService) {
////
////        UpdateDataService updateDataService = new UpdateDataService();
////        UpdateDataService.UpdateDataHandler handler = new UpdateDataService.UpdateDataHandler() {
////            @Override
////            @Transactional
////            public void processUpdateData(Update update) {
////                UpdateDataEntity updateDataEntity = mapper.toEntity(update);
////
////                try {
////                    updateDataEntity = repositoryService.save(updateDataEntity);
////                } catch (Exception e) {
////                    log.error("Ошибка сохранения в базу данных: []", e);
////
////                    try {
////                        responseDataService.sendMessage(sendErrorMessage(update));
////                    } catch (TelegramApiException ex) {
////                        log.error("Ошибка Telegram при отправке сообщения об ошибке: []", e);
////                    }
////
////                    return;
////                }
////
////                GrpcUpdateData grpcUpdateData = mapper.toTransportData(update);
////                GrpcResponseData responseData = null;
////
////                try {
////                    responseData = client.sendMessage(grpcUpdateData); // Отправляем данные сервису обработки
////                    repositoryService.delete(updateDataEntity); // Если всё прошло успешно, удаляем запись из базы
////                } catch (Exception e) {
////                    log.error("Ошибка gRPC запроса: []", e);
////                    return;
////                }
////
////                List<PartialBotApiMethod<?>> messageList = mapper.toTelegramMessage(responseData);
////                try {
////                    if (messageList != null) {
////                        responseDataService.sendMessage(messageList);
////                    }
////                } catch (TelegramApiException e) {
////                    log.error("Ошибка Telegram при отправке ответа: []", e);
////                }
////            }
////        };
////
////        updateDataService.setHandler(handler);
////        return updateDataService;
////    }
////
////    private List<SendMessage> sendErrorMessage(Update update) {
////        Long userChatId = update.hasMessage() ? update.getMessage().getChatId() : update.getCallbackQuery().getMessage().getChatId();
////        return List.of(SendMessage.builder()
////                        .chatId(userChatId)
////                        .text("К сожалению, сервис в данное время недоступен.")
////                        .build(),
////                SendMessage.builder()
////                        .chatId(adminID)
////                        .text("База данных недступна: " + LocalDateTime
////                                .now()
////                                .format(DateTimeFormatter.ofPattern("yy.MM.dd hh:mm:ss")))
////                        .build());
////
////    }


}
