package ru.quest_bot.telegram_message_dispatcher.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class KeyboardMarkupData {

    private KeyboardType keyboardType;
    private List<KeyboardRowData> keyboardRows;
    private Integer rowWidth;
    private boolean resizeKeyboard;
    private boolean removeKeyboard;
}
