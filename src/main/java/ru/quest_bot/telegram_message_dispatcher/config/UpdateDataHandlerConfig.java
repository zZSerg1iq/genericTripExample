package ru.quest_bot.telegram_message_dispatcher.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.quest_bot.grpc.GrpcResponseData;
import ru.quest_bot.grpc.GrpcUpdateData;
import ru.quest_bot.telegram_message_dispatcher.dto.request.UpdateData;
import ru.quest_bot.telegram_message_dispatcher.dto.response.ResponseData;
import ru.quest_bot.telegram_message_dispatcher.service.ResponseDataService;
import ru.quest_bot.telegram_message_dispatcher.service.UpdateDataHandler;
import ru.quest_bot.telegram_message_dispatcher.service.UpdateDataService;

@Configuration
@Slf4j
public class UpdateDataHandlerConfig {


    @Bean
    @ConditionalOnProperty(name = "rest.enabled", havingValue = "true")
    UpdateDataHandler restAscyncUpdateDataHandler(
            @Qualifier("restAsyncUpdateDataService") UpdateDataService<ResponseData, UpdateData> updateService) {

        log.info("restAscyncUpdateDataHandler is enabled");
        System.out.println("restAscyncUpdateDataHandler");
        return updateService::processUpdateData;
    }

    @Bean
    @ConditionalOnProperty(name = "grpc.enabled", havingValue = "true")
    UpdateDataHandler grpcUpdateDataHandler(
            @Qualifier("grpcUpdateDataService") UpdateDataService<GrpcResponseData, GrpcUpdateData> updateService) {

        log.info("grpcUpdateDataService is enabled");
        System.out.println("grpcUpdateDataService");
        return new UpdateDataHandler() {
            @Override
            public void processUpdateData(Update update) {
                System.out.println("SENDING UPDATE");
                updateService.processUpdateData(update);
            }
        };
    }



}
