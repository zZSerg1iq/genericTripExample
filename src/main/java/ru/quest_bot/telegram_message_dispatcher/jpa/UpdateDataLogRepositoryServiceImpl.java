package ru.quest_bot.telegram_message_dispatcher.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.quest_bot.telegram_message_dispatcher.jpa.entity.UpdateDataEntity;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UpdateDataLogRepositoryServiceImpl implements UpdateDataLogRepositoryService {

    private final UpdateDataLogRepository repository;

    @Override
    public UpdateDataEntity save(UpdateDataEntity updateDataEntity) {
        return repository.save(updateDataEntity);
    }

    @Override
    public void delete(UpdateDataEntity updateDataEntity) {
        repository.delete(updateDataEntity);
    }

    @Override
    public List<UpdateDataEntity> getUserUpdates(Long userId) {
        return repository.findAllByUserId(userId);
    }
}
