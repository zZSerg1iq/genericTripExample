package ru.quest_bot.telegram_message_dispatcher;


import org.springframework.web.bind.annotation.*;
import org.telegram.telegrambots.meta.api.methods.polls.SendPoll;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.quest_bot.telegram_message_dispatcher.jpa.entity.UpdateDataEntity;

//@RestController
//@RequestMapping("/api")
//@RequiredArgsConstructor
//@ConditionalOnProperty(name = "test.enabled", havingValue = "true")
public class Controller {

  //  private final ResponseDataService responseDataService;

    @PostMapping("/send")
    void sendUpdateData(@RequestBody UpdateDataEntity update) throws TelegramApiException {
        System.out.println("3) "+update);
        SendPoll p = SendPoll.builder()
                .chatId(update.getMessage().getChatId())
                .question(update.getMessage().getText())
                .allowMultipleAnswers(true)
                .correctOptionId(2)
                .option("1")
                .option("2")
                .option("3")
                .option("4")
                .option("5")
                .build();
   //     responseDataService.sendMessage(p);
    }
}
