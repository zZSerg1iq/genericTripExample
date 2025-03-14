package ru.quest_bot.telegram_message_dispatcher.mapper;

import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.quest_bot.telegram_message_dispatcher.dto.request.UpdateData;
import ru.quest_bot.telegram_message_dispatcher.dto.response.ResponseData;
import ru.quest_bot.telegram_message_dispatcher.mapper.basic.BasicDataMapper;

import java.util.List;

public class RestUpdateDataMapper extends BasicDataMapper<ResponseData, UpdateData> {

    @Override
    public UpdateData toTransportData(Update update) {
        return null;
    }

    @Override
    public List<PartialBotApiMethod<?>> toTelegramMessage(ResponseData responseData) {
        return null;
    }
}
