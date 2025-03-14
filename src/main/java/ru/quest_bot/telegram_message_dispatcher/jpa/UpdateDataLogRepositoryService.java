package ru.quest_bot.telegram_message_dispatcher.jpa;

import ru.quest_bot.telegram_message_dispatcher.jpa.entity.UpdateDataEntity;

import java.util.List;

/**
 * Сервис для работы с репозиторием
 */
public interface UpdateDataLogRepositoryService {

    UpdateDataEntity save(UpdateDataEntity updateDataEntity);
    void delete(UpdateDataEntity updateDataEntity);

    List<UpdateDataEntity> getUserUpdates(Long userId);
}
