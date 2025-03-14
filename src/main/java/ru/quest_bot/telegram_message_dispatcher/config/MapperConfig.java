package ru.quest_bot.telegram_message_dispatcher.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.quest_bot.grpc.GrpcResponseData;
import ru.quest_bot.grpc.GrpcUpdateData;
import ru.quest_bot.telegram_message_dispatcher.dto.request.UpdateData;
import ru.quest_bot.telegram_message_dispatcher.dto.response.ResponseData;
import ru.quest_bot.telegram_message_dispatcher.mapper.GrpcUpdateDataMapper;
import ru.quest_bot.telegram_message_dispatcher.mapper.basic.BasicQuestDataMapper;
import ru.quest_bot.telegram_message_dispatcher.mapper.RestUpdateDataMapper;

@Slf4j
@Configuration
public class MapperConfig {

    @Bean("grpcDataMapper")
    @ConditionalOnProperty(name = "grpc.enabled", havingValue = "true")
    public BasicQuestDataMapper<GrpcResponseData, GrpcUpdateData> grpcUpdateMapper() {
        log.info("GrpcUpdateMapper");
        System.out.println("GrpcUpdateMapper");
        return new GrpcUpdateDataMapper();
    }

    @Bean("restDataMapper")
    @ConditionalOnProperty(name = "rest.enabled", havingValue = "true")
    public BasicQuestDataMapper<ResponseData, UpdateData> restUpdateMapper() {
        log.info("RestUpdateMapper");
        System.out.println("RestUpdateMapper");
        return new RestUpdateDataMapper();
    }
}
