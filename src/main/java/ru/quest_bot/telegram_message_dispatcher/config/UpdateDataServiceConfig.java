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
     * @param repositoryService - репозиторий для взаимодействия с БД
     * @param questFeignClient  - клиент отправки сообщений сервису обработки
     * @param responseDataService - сервис отправки сообщеий в бота
     * @return бин скласса управления передачей соообщений
     */


    
    
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



}
