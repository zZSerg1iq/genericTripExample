package ru.quest_bot.telegram_message_dispatcher.client;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.quest_bot.telegram_message_dispatcher.dto.request.UpdateData;
import ru.quest_bot.telegram_message_dispatcher.dto.response.ResponseData;

/**
 * Клиент для пересылки данных между сервисами
 */
@ConditionalOnProperty(name = "rest.enabled", havingValue = "true")
@org.springframework.cloud.openfeign.FeignClient(name = "updateHandlerService", url = "http://localhost:8080/api")
public interface QuestFeignClient extends ExchangeClient<ResponseData, UpdateData> {

    @PostMapping("/send")
    void sendUpdateData(@RequestBody UpdateData update);

}
