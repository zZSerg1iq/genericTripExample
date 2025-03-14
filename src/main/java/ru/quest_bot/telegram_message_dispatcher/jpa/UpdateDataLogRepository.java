package ru.quest_bot.telegram_message_dispatcher.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.quest_bot.telegram_message_dispatcher.jpa.entity.UpdateDataEntity;

import java.util.List;

/**
 * репозиторий для хранения запроса пользователя
 */
public interface UpdateDataLogRepository extends JpaRepository<UpdateDataEntity, Long> {

    @Query(value = """
            select ude from UpdateDataEntity ude
            join fetch ude.from
            where ude.from.userId = ?1
            """)
    List<UpdateDataEntity> findAllByUserId(Long userId);
}
