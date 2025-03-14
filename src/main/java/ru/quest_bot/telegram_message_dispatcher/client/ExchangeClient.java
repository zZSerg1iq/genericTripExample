package ru.quest_bot.telegram_message_dispatcher.client;

public interface ExchangeClient<Response, Request> {
    Response sendMessage(Request request);
}
