package ru.quest_bot.telegram_message_dispatcher.client;

import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import ru.quest_bot.grpc.GrpcResponseData;
import ru.quest_bot.grpc.GrpcUpdateData;
import ru.quest_bot.grpc.QuestServiceGrpc;

@Service
@RequiredArgsConstructor
public class QuestGrpcClient implements ExchangeClient<GrpcResponseData, GrpcUpdateData> {

    @GrpcClient("questGrpcService")
    private QuestServiceGrpc.QuestServiceBlockingStub blockingStub;

    public GrpcResponseData sendMessage(GrpcUpdateData request) {
        return blockingStub.processUpdate(request);
    }

}
