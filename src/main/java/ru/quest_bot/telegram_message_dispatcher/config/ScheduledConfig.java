package ru.quest_bot.telegram_message_dispatcher.config;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

//@EnableScheduling
public class ScheduledConfig {

   // @Scheduled(fixedRate = 5000)
    public void processOutbox() {
//        List<OutboxEvent> events = repository.findAll();
//        for (OutboxEvent event : events) {
//            kafkaTemplate.send("update-events", event.getPayload());
//            repository.delete(event);
//        }
    }
}
