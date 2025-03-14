package ru.quest_bot.telegram_message_dispatcher.mapper.basic;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.polls.SendPoll;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;

@Component
public class TelegramMessageBuilder {

    public SendMessage getMessage() {
        return null;
    }

    public EditMessageText getEditMessage() {
        return null;
    }

    public DeleteMessage getDeleteMessage() {
        return null;
    }

    public SendPoll getPoll() {
        return null;
    }


}
